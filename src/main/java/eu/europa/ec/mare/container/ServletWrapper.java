package eu.europa.ec.mare.container;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.Data;

@Data
public class ServletWrapper {
  private final Servlet servlet;
  private final ServletRequest servletRequest;
  private final ServletResponse servletResponse;
}