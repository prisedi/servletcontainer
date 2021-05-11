package eu.europa.ec.mare.container.servlet;

import javax.servlet.ServletContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServletHandler {
  private ServletContext servletContext;
}