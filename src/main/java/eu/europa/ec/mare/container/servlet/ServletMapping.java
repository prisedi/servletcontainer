package eu.europa.ec.mare.container.servlet;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServletMapping {
  private String[] pathSpecs;
  private String servletName;
  //the location where the artifact was declared
  private String source;

  public ServletMapping(String source) {
    this.source = source;
  }
}