package com.github.jabinespbi.oneapp.usercenter.security.sso;

import com.github.jabinespbi.oneapp.usercenter.security.jwt.AuthEntryPointJwt;
import com.google.common.base.Preconditions;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.core.config.InitializationException;
import org.opensaml.core.config.InitializationService;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringReader;
import java.util.List;

/**
 * Provides means to extract a SAML assertion from a piece of XML.
 */
@Component
public class SamlAssertionExtractor {

    private static final Logger log = LoggerFactory.getLogger(SamlAssertionExtractor.class);

    private final ParserPool parserPool;
    private final UnmarshallerFactory unmarshallerFactory;

    /**
     * C'tor.
     */
    public SamlAssertionExtractor() {
        try {
            InitializationService.initialize();
        } catch (InitializationException e) {
            throw new IllegalStateException(e);
        }
        parserPool = XMLObjectProviderRegistrySupport.getParserPool();
        unmarshallerFactory = XMLObjectProviderRegistrySupport.getUnmarshallerFactory();
    }

    /**
     * Extracts a SAML assertion from XML that can either be a SAML auth response {@code <saml2p:Response>} or an
     * assertion {@code <saml2:Assertion>}. Method ensures that the returned XML object is detached from any parent i.e
     * . has none.
     *
     * @param xml string representing a SAML auth response or SAML assertion element
     * @return first SAML assertion found in the provided XML string
     */
    public Assertion extractAssertion(String xml) {
        log.trace("Extracting SAML assertion from '{}'.", xml);
        assertThatInputIsSamlResponseOrAssertion(xml);
        Assertion assertion;
        try {
            Document document = parserPool.parse(new StringReader(xml));
            Element documentElement = document.getDocumentElement();
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(documentElement);

            if (StringUtils.equals(documentElement.getLocalName(), "Response")) {
                Response response = (Response) unmarshaller.unmarshall(documentElement);
                assertThatResponseContainsAtLeastOneAssertion(response);
                assertion = response.getAssertions().get(0);
            } else {
                assertion = (Assertion) unmarshaller.unmarshall(documentElement);
            }
        } catch (XMLParserException | UnmarshallingException e) {
            throw new IllegalArgumentException(String.format("Failed to unmarshall '%s'.", xml), e);
        }
        // must be detached from any parent so it can be processed further
        assertion.setParent(null);
        log.debug("Found SAML assertion for '{}'.", extractSubjectNameFrom(assertion));
        return assertion;
    }

    private void assertThatInputIsSamlResponseOrAssertion(String xml) {
        Preconditions.checkArgument(StringUtils.contains(xml, ":Response") || StringUtils.contains(xml, ":Assertion"));
    }

    private void assertThatResponseContainsAtLeastOneAssertion(Response response) {
        List<Assertion> assertions = response.getAssertions();
        Preconditions.checkArgument(
                assertions != null && !assertions.isEmpty(), "SAML auth response contains no assertions.");
        if (assertions.size() > 1) {
            log.warn("SAML auth response contains {} assertions. Using the first ('{}').", assertions.size(),
                    extractSubjectNameFrom(assertions.get(0)));
        }
    }

    private String extractSubjectNameFrom(Assertion assertion) {
        return assertion.getSubject().getNameID().getValue();
    }
}