package de.bruenni.rideprediction;

import org.eclipse.microprofile.auth.LoginConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author Yoshimasa Tanabe
 */
@ApplicationPath("/rideprediction")
@LoginConfig(authMethod = "MP-JWT", realmName = "rideprediction")
public class JaxRsApplication extends Application {
}
