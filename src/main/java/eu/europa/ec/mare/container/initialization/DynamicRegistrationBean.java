package eu.europa.ec.mare.container.initialization;

import eu.europa.ec.mare.utils.TypeUtils;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Registration.Dynamic;
import javax.servlet.ServletContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DynamicRegistrationBean<D extends Dynamic> extends RegistrationBean {
  private String name;
  private boolean asyncSupported = true;
  private Map<String, String> initParameters = new LinkedHashMap();

  public DynamicRegistrationBean() {
  }

  protected final void register(String description, ServletContext servletContext) {
    D registration = this.addRegistration(description, servletContext);
    if (registration == null) {
      log.info(TypeUtils.capitalize(description) + " was not registered (possibly already registered?)");
    } else {
      this.configure(registration);
    }
  }

  protected abstract D addRegistration(String description, ServletContext servletContext);

  protected void configure(D registration) {
    registration.setAsyncSupported(this.asyncSupported);
    if (!this.initParameters.isEmpty()) {
      registration.setInitParameters(this.initParameters);
    }

  }
}