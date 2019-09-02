package de.bruenni.rideprediction.identity.impl.auth0;

import com.auth0.client.mgmt.ManagementAPI;

import javax.enterprise.inject.Alternative;

/**
 * To be proxyable by CDI and usable in @Producer methods.
 *
 * @author Oliver Brüntje
 */
@Alternative
public class ManagementAPICdi extends ManagementAPI {
    /**
     * Create an instance with the given tenant's domain and API token.
     * See the Management API section in the readme or visit https://auth0.com/docs/api/management/v2/tokens to learn how to obtain a token.
     *
     * @param domain   the tenant's domain.
     * @param apiToken the token to authenticate the calls with.
     */
    public ManagementAPICdi(String domain, String apiToken) {
        super(domain, apiToken);
    }

    /**
     * CDI
     */
    protected ManagementAPICdi() {
        super("invalid.auth0.com", "");
    }
}
