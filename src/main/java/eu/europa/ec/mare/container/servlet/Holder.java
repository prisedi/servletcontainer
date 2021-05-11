package eu.europa.ec.mare.container.servlet;

import java.util.Objects;
import lombok.Getter;

@Getter
public class Holder<T> {

  private final String source;
  private Class<? extends T> clazz;
  private String className;
  private String name;
  private T instance;
  private ServletHandler servletHandler;

  protected Holder(String source) {
    this.source = source;
  }

  public void setClassName(String className) {
    this.className = className;
    this.clazz = null;
  }

  /**
   * @param held The class to hold
   */
  public void setHeldClass(Class<? extends T> held) {
    this.clazz = held;
    if (held != null) {
      this.className = held.getName();
    }
  }

  protected synchronized void setInstance(T instance) {
    this.instance = instance;
    if (Objects.isNull(instance)) {
      setHeldClass(null);
    } else {
      setHeldClass((Class<T>) instance.getClass());
    }
    if (getName() == null) {
      setName(String.format("%s@%x", instance.getClass().getName(), instance.hashCode()));
    }
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param servletHandler The {@link ServletHandler} that will handle requests dispatched to this servlet.
   */
  public void setServletHandler(ServletHandler servletHandler) {
    this.servletHandler = servletHandler;
  }
}