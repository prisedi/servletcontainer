package eu.europa.ec.mare.container.initialization;

import eu.europa.ec.mare.utils.TypeUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public abstract class RegistrationBean  implements ServletContextInitializer{
  private boolean enabled = true;

  public RegistrationBean() {
  }
  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    String description = this.getDescription();
    if (!this.isEnabled()) {
      log.info(TypeUtils.capitalize(description) + " was not registered (disabled)");
    } else {
      this.register(description, servletContext);
    }
  }

  protected abstract String getDescription();

  protected abstract void register(String description, ServletContext servletContext);
}