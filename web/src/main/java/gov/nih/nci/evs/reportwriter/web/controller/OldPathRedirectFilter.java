package gov.nih.nci.evs.reportwriter.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Redirect from old context path. */
@Component
public class OldPathRedirectFilter implements Filter {

  /* see superclass */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    // Check if the request URI starts with the old path
    if (httpRequest.getRequestURI().startsWith("/ncreportwriter")) {
      // Construct the new URL and redirect
      String newUri =
          httpRequest.getRequestURI().replaceFirst("/ncreportwriter", "/ncireportwriter2");
      httpResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
      httpResponse.setHeader("Location", newUri);
    } else {
      // If the request is not for the old path, continue the filter chain
      chain.doFilter(request, response);
    }
  }
}
