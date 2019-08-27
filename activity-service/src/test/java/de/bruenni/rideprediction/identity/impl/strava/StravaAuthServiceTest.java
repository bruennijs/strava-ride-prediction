package de.bruenni.rideprediction.identity.impl.strava;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.URI;

@RunWith(JUnit4.class)
public class StravaAuthServiceTest {

    @Test
    public void createAuthorizationUrlTest() {
        // given
        URI expected = URI
                .create("https://www.strava.com/auth?client_id=223&redirect_uri=https://localhost:8085/hello&response_type=code&scope=readall");
        StravaAuthService sut = new StravaAuthService("223", "sih", "https://www.strava.com/auth", "https://localhost:8085/hello", "readall");

        // when
        URI url = sut.createAuthorizationUrl();

        // then
        Assert.assertEquals(expected, url);
    }
}
