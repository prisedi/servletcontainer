package eu.europa.ec.mare.servlet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.junit.BeforeClass;
import org.junit.Test;

public class MareServletUnitTest {

  private static ServletRequest request;
  private static ServletResponse response;


  @BeforeClass
  public static void setUpServletRequestMockInstances() {
    request = mock(ServletRequest.class);
    response = mock(ServletResponse.class);
  }

  @Test
  public void givenServletResponseMockInstance_whenCalledgetContentType_thenCalledAtLeastOnce() {
    response.getContentType();
    verify(response, atLeast(1)).getContentType();
  }

  @Test
  public void givenServletResponseMockInstance_whenCalledgetContentType_thenOneAssertion() {
    when(response.getContentType()).thenReturn("text/plain");
    assertEquals("text/plain", response.getContentType());
  }
}
