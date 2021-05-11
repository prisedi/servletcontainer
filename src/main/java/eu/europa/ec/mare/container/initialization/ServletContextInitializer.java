package eu.europa.ec.mare.container.initialization;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@FunctionalInterface
public interface ServletContextInitializer {
  void onStartup(ServletContext servletContext) throws ServletException;
}