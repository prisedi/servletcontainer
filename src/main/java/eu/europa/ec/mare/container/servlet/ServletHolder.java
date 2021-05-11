package eu.europa.ec.mare.container.servlet;

import java.lang.reflect.InvocationTargetException;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ServletHolder extends Holder<Servlet> implements Comparable<ServletHolder> {

  private int initOrder = -1;
  private boolean initOnStartup = false;
  private ServletRegistration.Dynamic registration;
  private Servlet servlet;

  protected ServletHolder(String source) {
    super(source);
  }

  /**
   * @return the newly created Servlet instance
   * @throws ServletException          if unable to create a new instance
   * @throws IllegalAccessException    if not allowed to create a new instance
   * @throws InstantiationException    if creating new instance resulted in error
   * @throws NoSuchMethodException     if creating new instance resulted in error
   * @throws InvocationTargetException If creating new instance throws an exception
   */
  protected Servlet newInstance()
      throws ServletException, IllegalAccessException, InstantiationException,
      NoSuchMethodException, InvocationTargetException {
    try {
      ServletContext ctx = getServletHandler().getServletContext();
      if (ctx != null) {
        return ctx.createServlet(getClazz());
      }
      return getClazz().getDeclaredConstructor().newInstance();
    } catch (ServletException ex) {
      Throwable cause = ex.getRootCause();
      if (cause instanceof InstantiationException) {
        throw (InstantiationException) cause;
      }
      if (cause instanceof IllegalAccessException) {
        throw (IllegalAccessException) cause;
      }
      if (cause instanceof NoSuchMethodException) {
        throw (NoSuchMethodException) cause;
      }
      if (cause instanceof InvocationTargetException) {
        throw (InvocationTargetException) cause;
      }
      throw ex;
    }
  }

  @Override
  /**
   * Comparator by init order.
   */
  public int compareTo(ServletHolder sh) {
    if (sh == this) {
      return 0;
    }

    if (sh.initOrder < initOrder) {
      return 1;
    }

    if (sh.initOrder > initOrder) {
      return -1;
    }

    // consider getClassName(), need to position properly when one is configured but not the other
    int c;
    if (getClassName() == null && sh.getClassName() == null) {
      c = 0;
    } else if (getClassName() == null) {
      c = -1;
    } else if (sh.getClassName() == null) {
      c = 1;
    } else {
      c = getClassName().compareTo(sh.getClassName());
    }

    // if _initOrder and getClassName() are the same, consider the getName()
    if (c == 0) {
      c = getName().compareTo(sh.getName());
    }

    return c;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ServletHolder && compareTo((ServletHolder) o) == 0;
  }

  @Override
  public int hashCode() {
    return getName() == null ? System.identityHashCode(this) : getName().hashCode();
  }
}
