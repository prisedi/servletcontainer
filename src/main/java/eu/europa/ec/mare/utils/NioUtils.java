package eu.europa.ec.mare.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class NioUtils {

  public static String processRead(SocketChannel sChannel) throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    sChannel.read(buffer);
    buffer.flip();
    Charset charset = Charset.forName("UTF-8");
    CharsetDecoder decoder = charset.newDecoder();
    CharBuffer charBuffer = decoder.decode(buffer);
    return charBuffer.toString();
  }

  public static void processWrite(SocketChannel sChannel, String msg) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
    sChannel.write(buffer);
  }
}
