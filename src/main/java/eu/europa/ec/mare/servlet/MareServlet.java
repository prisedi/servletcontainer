package eu.europa.ec.mare.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MareServlet extends GenericServlet {

  /**
   * method is invoked upon each request after its initialization.
   *
   * @param servletRequest
   * @param servletResponse
   * @throws ServletException
   * @throws IOException
   */
  @Override
  public void service(ServletRequest servletRequest, ServletResponse servletResponse)
      throws ServletException, IOException {
    servletResponse.setContentType("text/plain; charset=utf-8");
    try (PrintWriter writer = servletResponse.getWriter()) {
      writer.println("hello world");
    }
  }
}
