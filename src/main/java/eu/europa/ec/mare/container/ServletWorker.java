package eu.europa.ec.mare.container;

import eu.europa.ec.mare.container.servlet.ServletHolder;
import eu.europa.ec.mare.utils.NioUtils;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;
import javax.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class ServletWorker implements Callable<String> {

  private final SocketChannel socketChannel;
  private final int clientNo;
  private final ServletHolder servletHolder;

  ServletWorker(SocketChannel asyncChannel, int counter, ServletHolder servletHolder) {
    this.socketChannel = asyncChannel;
    this.clientNo = counter;
    this.servletHolder = servletHolder;
  }

  @Override
  public String call() throws Exception {
    String host = socketChannel.getRemoteAddress().toString();
    log.info("Incoming connection from: " + host + " client :" + clientNo);
    log.info("********Servlet name to invoke:" + NioUtils.processRead(socketChannel));
    /////
    NioUtils.processWrite(socketChannel, "response for client no:" + clientNo);
    socketChannel.close();
    log.info("From Client-" + clientNo + " from host:" + host + " was successfully served!");
    return host;
  }
}