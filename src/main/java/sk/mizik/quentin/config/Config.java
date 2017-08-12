package sk.mizik.quentin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.mizik.quentin.Constants;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Marian Mizik
 * @since 1.1.0
 */
@ApplicationScoped
public class Config {

    @Inject
    private ConfigValidator configValidator;

    private static final Logger LOG = LoggerFactory.getLogger(Config.class);

    private Map<String, String> variables = new HashMap<>();

    public String getString(String key) {
        return variables.get(key);
    }

    public Boolean getBoolean(String key) {
        return Boolean.valueOf(variables.get(key));
    }

    public Integer getNumber(String key) {
        return Integer.valueOf(variables.get(key));
    }

    //LOAD CONFIGURATION FROM FILE. FILE SHOULD BE NAMED quentin.cfg AND LOCATED IN SERVER INSTALLATION ROOT DIRECTORY
    public void load() {
        try {
            String serverHome = System.getProperty("jboss.server.base.dir");
            if (serverHome == null) {
                serverHome = System.getProperty("jetty.home");
            }

            File file = new File(serverHome + File.separator + "quentin.cfg");
            Properties properties = new Properties();
            try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
                properties.load(reader);
            }

            for (String key : properties.stringPropertyNames()) {
                variables.put(key, properties.getProperty(key));
            }

            configValidator.validate();

            LOG.info(variables.size() + " configuration values has been loaded from: " + file.getAbsolutePath());
            LOG.info("Debug mode is set to: " + getBoolean(Constants.DEBUG_MODE));
        } catch (RuntimeException | IOException e) {
            LOG.error("Failed to load config file: " + e.getMessage());
        }
    }
}