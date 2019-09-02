package de.bruenni.rideprediction.identity.impl.auth0;

import com.auth0.client.auth.AuthAPI;

import javax.enterprise.inject.Alternative;

/**
 * To be proxyable by CDI and usable in @Producer methods.
 *
 * @author Oliver Brüntje
 */
@Alternative
public class AuthAPICdi extends AuthAPI {
    /**
     * Create a new instance with the given tenant's domain, application's client id and client secret. These values can be obtained at https://manage.auth0.com/#/applications/{YOUR_CLIENT_ID}/settings.
     *
     * @param domain       tenant's domain.
     * @param clientId     the application's client id.
     * @param clientSecret the application's client secret.
     */
    public AuthAPICdi(String domain, String clientId, String clientSecret) {
        super(domain, clientId, clientSecret);
    }

    protected AuthAPICdi() {
        super("invalid.auth0.com", "", "");
    }
}
