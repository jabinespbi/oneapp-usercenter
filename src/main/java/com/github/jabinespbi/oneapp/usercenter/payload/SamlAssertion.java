package com.github.jabinespbi.oneapp.usercenter.payload;

public class SamlAssertion {
    private String RelayState;

    private String SAMLResponse;

    public String getRelayState() {
        return RelayState;
    }

    public void setRelayState(String relayState) {
        RelayState = relayState;
    }

    public String getSAMLResponse() {
        return SAMLResponse;
    }

    public void setSAMLResponse(String SAMLResponse) {
        this.SAMLResponse = SAMLResponse;
    }
}
