package de.bruenni.rideprediction.activityservice;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationPath("/rideprediction/activity-service")
@LoginConfig(authMethod = "MP-JWT", realmName = "rideprediction")
public class JaxRsApplication extends Application {
}
