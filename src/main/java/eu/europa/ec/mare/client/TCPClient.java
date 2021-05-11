package eu.europa.ec.mare.client;

import eu.europa.ec.mare.utils.NioUtils;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TCPClient {

  public static void main(String[] args) {
    try (SocketChannel client = SocketChannel.open()) {
      InetSocketAddress address = new InetSocketAddress("localhost", 17777);
      client.connect(address);
      String clientMessage = "";
      while (!clientMessage.equals("exit")) {
        log.info("Enter servlet name to invoke :");
        Scanner scanner = new Scanner(System.in);
        clientMessage = scanner.nextLine();
        log.info("Client: Sending ...");
        NioUtils.processWrite(client, clientMessage);
        log.info("Client: Message sent: " + clientMessage);
        String serverMessage = NioUtils.processRead(client);
        log.info(serverMessage);
      }
    } catch (IOException ex) {
      log.error("Exception: ", ex);
    }
  }
}