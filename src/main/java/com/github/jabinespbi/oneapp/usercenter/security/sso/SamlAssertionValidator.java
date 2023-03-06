package com.github.jabinespbi.oneapp.usercenter.security.sso;

import org.opensaml.saml2.core.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static com.google.common.base.Predicates.notNull;

@Component
public class SamlAssertionValidator {

    private static final Logger log = LoggerFactory.getLogger(SamlAssertionExtractor.class);

    public boolean validate(@NonNull Assertion assertion) {
        // Check ID
        // Check IssueInstant if its too old
        // Check Version
        // Check Issuer if equals to http://www.okta.com/exk8j8tsu1n9mTZL85d7
        // Check Subject's NameID == username in the SP
        // Check now against NotBefore and NotOnOrAfter
        // Check Audience if http://localhost:8080/sso
        // Check if AuthnContextClassRef is strong enough
        // Get all attributes

        return true;
    }

    /**
     * An example SAML assertion:
     * <?xml version="1.0" encoding="UTF-8"?>
     * <saml2:Assertion ID="id167978599824135181011603115" IssueInstant="2023-03-05T10:17:10.683Z" Version="2.0"
     *     xmlns:saml2="urn:oasis:names:tc:SAML:2.0:assertion">
     *     <saml2:Issuer Format="urn:oasis:names:tc:SAML:2.0:nameid-format:entity">http://www.okta.com/exk8j8tsu1n9mTZL85d7</saml2:Issuer>
     *     <saml2:Subject>
     *         <saml2:NameID Format="urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified">jabinespbi@gmail.com</saml2:NameID>
     *         <saml2:SubjectConfirmation Method="urn:oasis:names:tc:SAML:2.0:cm:bearer">
     *             <saml2:SubjectConfirmationData NotOnOrAfter="2023-03-05T10:22:10.683Z" Recipient="http://localhost:8080/sso/acs"/>
     *         </saml2:SubjectConfirmation>
     *     </saml2:Subject>
     *     <saml2:Conditions NotBefore="2023-03-05T10:12:10.683Z" NotOnOrAfter="2023-03-05T10:22:10.683Z">
     *         <saml2:AudienceRestriction>
     *             <saml2:Audience>http://localhost:8080/sso</saml2:Audience>
     *         </saml2:AudienceRestriction>
     *     </saml2:Conditions>
     *     <saml2:AuthnStatement AuthnInstant="2023-03-05T10:17:10.683Z" SessionIndex="id1678011430681.1407961225">
     *         <saml2:AuthnContext>
     *             <saml2:AuthnContextClassRef>urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml2:AuthnContextClassRef>
     *         </saml2:AuthnContext>
     *     </saml2:AuthnStatement>
     *     <saml2:AttributeStatement>
     *         <saml2:Attribute Name="email" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified">
     *             <saml2:AttributeValue
     *                 xmlns:xs="http://www.w3.org/2001/XMLSchema"
     *                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">jabinespbi@gmail.com
     *             </saml2:AttributeValue>
     *         </saml2:Attribute>
     *         <saml2:Attribute Name="firstName" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified">
     *             <saml2:AttributeValue
     *                 xmlns:xs="http://www.w3.org/2001/XMLSchema"
     *                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Paul Benedict
     *             </saml2:AttributeValue>
     *         </saml2:Attribute>
     *         <saml2:Attribute Name="lastName" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified">
     *             <saml2:AttributeValue
     *                 xmlns:xs="http://www.w3.org/2001/XMLSchema"
     *                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Jabines
     *             </saml2:AttributeValue>
     *         </saml2:Attribute>
     *         <saml2:Attribute Name="groups" NameFormat="urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified">
     *             <saml2:AttributeValue
     *                 xmlns:xs="http://www.w3.org/2001/XMLSchema"
     *                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="xs:string">Everyone
     *             </saml2:AttributeValue>
     *         </saml2:Attribute>
     *     </saml2:AttributeStatement>
     * </saml2:Assertion>
     */
}
