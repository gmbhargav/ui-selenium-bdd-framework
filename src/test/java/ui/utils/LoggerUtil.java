package ui.utils;

import com.google.common.base.Strings;
import org.apache.log4j.xml.DOMConfigurator;

import java.net.MalformedURLException;
import java.net.URL;


public class LoggerUtil {

    public void configureLogging() {
        String logConfigLocation = getLogConfigLocation();
        try {
            DOMConfigurator.configure(new URL(logConfigLocation));
        } catch (MalformedURLException e) {
            System.out.println("FATAL: failed to configure Logging." + e.getMessage());
            System.exit(3);
        }
    }

    private String getLogConfigLocation() {
        String logConfigLocation = System.getProperty("log4j.config");
        if (Strings.isNullOrEmpty(logConfigLocation)) {
            logConfigLocation = "file:src/main/resources/log4j.xml";
        }
        return logConfigLocation;
    }
}
