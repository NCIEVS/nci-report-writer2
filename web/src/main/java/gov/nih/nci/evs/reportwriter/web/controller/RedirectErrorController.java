package gov.nih.nci.evs.reportwriter.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;

/** Redirect from old context path. */
@Controller
public class RedirectErrorController implements ErrorController {

  /** The Constant ERROR_PATH. */
  private static final String ERROR_PATH = "/error";

  /** The Constant NEW_CONTEXT_PATH. */
  @Value("${server.servlet.context-path:ncireportwriter2}")
  private String newContextPath;

  /** The Constant OLD_CONTEXT_PATH. */
  private static final String OLD_CONTEXT_PATH = "/ncreportwriter";

  /**
   * Handle error.
   *
   * @param request the request
   * @return the redirect view
   */
  @GetMapping(ERROR_PATH)
  public RedirectView handleError(HttpServletRequest request) {
    String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");

    // Check if the 404 error came from a request to the old context path
    if (requestUri != null && requestUri.startsWith(OLD_CONTEXT_PATH)) {

      // Construct the new URI
      String newUri = requestUri.replaceFirst(OLD_CONTEXT_PATH, newContextPath);

      RedirectView redirectView = new RedirectView();
      redirectView.setUrl(newUri);
      redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

      return redirectView;
    }

    // For all other errors, return a default error view
    // You can replace this with a more sophisticated error page
    return new RedirectView(newContextPath);
  }
}
