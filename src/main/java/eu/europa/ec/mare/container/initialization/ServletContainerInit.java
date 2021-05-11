package eu.europa.ec.mare.container.initialization;

import eu.europa.ec.mare.container.servlet.ServletHolder;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServletContainerInit {

  private static final String LOCATION = "web.xml";

  public ServletContainerInit() {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    try {
      Enumeration<URL> resourceUrls =
          cl != null ? cl.getResources(LOCATION) : ClassLoader.getSystemResources(LOCATION);
      while (resourceUrls.hasMoreElements()) {
        URL url = resourceUrls.nextElement();
      }
    } catch (IOException e) {
      log.error("Could not find resource web.xml", e);
    }
  }

  public Map<String, ServletHolder> getServletHolders() {
    return new HashMap<>();
  }
}