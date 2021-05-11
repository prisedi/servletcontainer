package eu.europa.ec.mare.exception;

import lombok.Getter;

@Getter
public final class UsageException extends RuntimeException {
  public static final int ERR_UNKNOWN = 1;
  private int exitCode;

  public UsageException(int exitCode, String message) {
    super(message);
    this.exitCode = exitCode;
  }

  public UsageException(int exitCode, String format, Object... objs) {
    super(String.format(format, objs));
    this.exitCode = exitCode;
  }

  public UsageException(String format, Object... objs) {
    this(ERR_UNKNOWN, format, objs);
  }

  public UsageException(int exitCode, Throwable cause) {
    super(cause);
    this.exitCode = exitCode;
  }
}
