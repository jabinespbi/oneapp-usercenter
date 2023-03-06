package com.github.jabinespbi.oneapp.usercenter.security.sso;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.*;
import org.opensaml.saml2.core.impl.*;

import java.util.Base64;
import java.util.UUID;

public class SsoUtils {

    private static final String SAML2_PROTOCOL = "urn:oasis:names:tc:SAML:2.0:protocol";
    private static final String SAML2_NAME_ID_POLICY = "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent";
    private static final String SAML2_POST_BINDING = "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";
    private static final String SAML2_PASSWORD_PROTECTED_TRANSPORT = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";
    private static final String SAML2_ASSERTION = "urn:oasis:names:tc:SAML:2.0:assertion";
    public String getBase64EncodedSamlRequest() {
        var samlRequest = "<saml2p:AuthnRequest xmlns:saml2p=\"urn:oasis:names:tc:SAML:2.0:protocol\"\n" +
                "                     AssertionConsumerServiceURL=\"http://localhost:4200/sso/acs\"\n" +
                "                     Destination=\"https://dev-42085807.okta.com/app/dev-42085807_myangular_1/exk8j8tsu1n9mTZL85d7/sso/saml\"\n" +
                "                     ForceAuthn=\"false\"\n" +
                "                     ID=\"ARQ1da5e2f-f44d-4845-b0ba-0840ea4364b8\"\n" +
                "                     IsPassive=\"false\"\n" +
                "                     IssueInstant=\"2023-03-02T04:50:16.048Z\"\n" +
                "                     ProtocolBinding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\"\n" +
                "                     Version=\"2.0\"\n" +
                ">\n" +
                "<saml2:Issuer xmlns:saml2=\"urn:oasis:names:tc:SAML:2.0:assertion\">http://localhost:4200/sso</saml2:Issuer>\n" +
                "</saml2p:AuthnRequest>\n";

        DateTime issueInstant = new DateTime();
        AuthnRequestBuilder authRequestBuilder = new AuthnRequestBuilder();
        AuthnRequest authRequest = authRequestBuilder.buildObject(SAML2_PROTOCOL, "AuthnRequest", "samlp");
        authRequest.setForceAuthn(Boolean.FALSE);
        authRequest.setIsPassive(Boolean.FALSE);
        authRequest.setIssueInstant(issueInstant);
        authRequest.setProtocolBinding(SAML2_POST_BINDING);
        authRequest.setDestination("https://dev-42085807.okta.com/app/dev-42085807_myangular_1/exk8j8tsu1n9mTZL85d7/sso/saml");
        authRequest.setAssertionConsumerServiceURL("http://localhost:4200/sso/acs");
        authRequest.setIssuer(buildIssuer("http://localhost:4200/sso"));
        authRequest.setNameIDPolicy(buildNameIDPolicy());
        authRequest.setRequestedAuthnContext(buildRequestedAuthnContext());
        authRequest.setID(UUID.randomUUID().toString());
        authRequest.setVersion(SAMLVersion.VERSION_20);

        return Base64.getEncoder()
                .encodeToString(authRequest.toString().getBytes());
    }

    /**
     * Build the issuer object
     *
     * @return Issuer object
     */
    private static Issuer buildIssuer(String issuerId) {
        IssuerBuilder issuerBuilder = new IssuerBuilder();
        Issuer issuer = issuerBuilder.buildObject();
        issuer.setValue(issuerId);
        return issuer;
    }

    /**
     * Build the NameIDPolicy object
     *
     * @return NameIDPolicy object
     */
    private static NameIDPolicy buildNameIDPolicy() {
        NameIDPolicy nameIDPolicy = new NameIDPolicyBuilder().buildObject();
        nameIDPolicy.setFormat(SAML2_NAME_ID_POLICY);
        nameIDPolicy.setAllowCreate(Boolean.TRUE);
        return nameIDPolicy;
    }

    /**
     * Build the RequestedAuthnContext object
     *
     * @return RequestedAuthnContext object
     */
    private static RequestedAuthnContext buildRequestedAuthnContext() {

        //Create AuthnContextClassRef
        AuthnContextClassRefBuilder authnContextClassRefBuilder = new AuthnContextClassRefBuilder();
        AuthnContextClassRef authnContextClassRef =
                authnContextClassRefBuilder.buildObject(SAML2_ASSERTION, "AuthnContextClassRef", "saml");
        authnContextClassRef.setAuthnContextClassRef(SAML2_PASSWORD_PROTECTED_TRANSPORT);

        //Create RequestedAuthnContext
        RequestedAuthnContextBuilder requestedAuthnContextBuilder = new RequestedAuthnContextBuilder();
        RequestedAuthnContext requestedAuthnContext =
                requestedAuthnContextBuilder.buildObject();
        requestedAuthnContext.setComparison(AuthnContextComparisonTypeEnumeration.EXACT);
        requestedAuthnContext.getAuthnContextClassRefs().add(authnContextClassRef);

        return requestedAuthnContext;
    }
}
