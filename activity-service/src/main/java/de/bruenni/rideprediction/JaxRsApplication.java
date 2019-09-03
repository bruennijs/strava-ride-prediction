package de.bruenni.rideprediction;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationPath("/rideprediction")
@LoginConfig(authMethod = "MP-JWT", realmName = "rideprediction")
@DeclareRoles({"strava:athlete"})
public class JaxRsApplication extends Application {
}
