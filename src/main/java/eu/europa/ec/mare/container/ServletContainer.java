package eu.europa.ec.mare.container;

import eu.europa.ec.mare.container.initialization.ServletContainerInit;
import eu.europa.ec.mare.container.servlet.ServletHolder;
import eu.europa.ec.mare.exception.UsageException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ServletContainer {

  private final ExecutorService taskExecutor;
  private static final Servlet SERVLET_INSTANCE = new HelloWorldServlet();
  private final Map<String, ServletHolder> servletHoldersCache = new HashMap<>();


  public ServletContainer() {
    taskExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
  }

  // daemon lifecycle methods (init, start, stop, destroy)
  private void init(String[] args) throws Exception {
    ServletContainerInit servletContainerInit = new ServletContainerInit();
    servletHoldersCache.putAll(servletContainerInit.getServletHolders());
  }

  // ------------------------------------------------------------
  // implement daemon lifecycle methods (init, start, stop, destroy)
  public void start(String[] args) throws Exception {
    try {
      init(args);
    } catch (Exception ex) {
      log.error("Initialization failed .Exit...", ex);
      System.exit(-1);
    }

    try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
      if (serverSocketChannel.isOpen()) {
        serverSocketChannel.setOption(StandardSocketOptions.SO_RCVBUF, 4 * 1024);
        serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
        ByteBuffer buffer = ByteBuffer.wrap("this is a test".getBytes());
        serverSocketChannel.socket().bind(new InetSocketAddress(17777));
        serverSocketChannel.configureBlocking(false);
        log.info("Servlet Container Started.Waiting for connections ...");
        int counter = 0;
        while (true) {
          // accept the client connection requests
          SocketChannel socketChannel = serverSocketChannel.accept();
          if (Objects.isNull(socketChannel)) {
            Thread.sleep(100);
          } else {
            counter++;
            log.info(
                "Incoming connection from: " + socketChannel.socket().getRemoteSocketAddress());

            try {
              log.info(" >> " + "Client No:" + counter + " started!");
              //servletHoldersCache.get(SERVLET_INSTANCE.getServletConfig().getServletName()))
              taskExecutor.submit(new ServletWorker(socketChannel, counter, null));
            } catch (Exception ex) {
              log.error("Exception: ", ex);
              log.info("\n Servlet Container is shutting down ...");
              shutdownExecutor();
              break;
            }
          }
        }

      } else {
        log.info("The server-socket channel cannot be opened!");
      }
    } catch (IOException ex) {
      log.error("Exception: ", ex);
    }
  }

  // ------------------------------------------------------------
  // implement daemon lifecycle methods (init, start, stop, destroy)
  private void stop() throws Exception {
    shutdownExecutor();
  }

  // ------------------------------------------------------------
  // implement daemon lifecycle methods (init, start, stop, destroy)
  private void destroy() {

  }

  private void shutdownExecutor() {
    taskExecutor.shutdown();
    while (!taskExecutor.isTerminated()) {
    }
  }

  public static void main(String[] args) {
    try {
      ServletContainer container = new ServletContainer();
      container.start(args);
    } catch (UsageException e) {
      log.error(e.getMessage());
      usageExit(e.getCause(), e.getExitCode());
    } catch (Throwable e) {
      usageExit(e, UsageException.ERR_UNKNOWN);
    }
  }

  private static void usageExit(int exit) {
    usageExit(null, exit);
  }

  private static void usageExit(Throwable t, int exit) {
    if (t != null) {
      log.debug("Exception:", t);
    }
    System.err.println();
    System.err.println("Usage: java -jar servlet-container-jar.jar ");

    System.exit(exit);
  }

  public static class HelloWorldServlet extends HttpServlet implements Servlet {

    @Override
    protected void doGet(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      response.getWriter().append("hello world");
      response.getWriter().close();
    }
  }
}