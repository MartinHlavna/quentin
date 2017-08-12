package sk.mizik.quentin.config;

import com.google.common.base.Strings;
import sk.mizik.quentin.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Marian Mizik
 * @since 1..0
 */
@ApplicationScoped
public class ConfigValidator {

    @Inject
    private Config config;

    private final String[] configKeys = new String[]{
            Constants.XMPP_USER,
            Constants.XMPP_PASS,
            Constants.XMPP_HOST,
            Constants.XMPP_PORT,
    };

    //VALIDATE IF ALL COMPULSORY CONFIGURATIONS ARE IN PLACE AND VALUE IS NOT EMPTY
    public void validate() {
        for (String key : configKeys) {
            if (Strings.isNullOrEmpty(config.getString(key))) {
                throw new RuntimeException("Incorrect  or missing configuration value for key " + key + ". Value found: " + config.getString(key));
            }
        }
    }
}
