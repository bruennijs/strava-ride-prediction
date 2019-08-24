package de.bruenni.rideprediction.application.auth.strava;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.URI;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class StravaAuthServiceTest {

    @Test
    public void createAuthorizationUrlTest() {
        StravaAuthService sut = new StravaAuthService("223", "sih", "https://www.strava.com/auth", "https://localhost:8085/hello");

        URI url = sut.createAuthorizationUrl();
        Assert.assertEquals("https://www.strava.com?client_id=223", url.toASCIIString());
    }
}
