package gov.nih.nci.evs.reportwriter.web.configuration;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

/** The Class StaticResourcesConfiguration. */
@Configuration
@EnableConfigurationProperties({ResourceWebPropertiesConfig.class})
public class StaticResourcesConfiguration implements WebMvcConfigurer {

  /** The Constant STATIC_RESOURCES. */
  static final String[] STATIC_RESOURCES =
      new String[] {
        "/**/*.css",
        "/**/*.html",
        "/**/*.js",
        "/**/*.json",
        "/**/*.bmp",
        "/**/*.jpeg",
        "/**/*.jpg",
        "/**/*.png",
        "/**/*.ttf",
        "/**/*.eot",
        "/**/*.svg",
        "/**/*.woff",
        "/**/*.woff2"
      };

  /** The resource properties. */
  @Autowired private WebProperties.Resources resourceProperties = new WebProperties.Resources();

  /* see superclass */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Add all static files
    // Long cachePeriodLong = resourceProperties.getCache().getPeriod().getSeconds();
    Long cachePeriodLong = 30L;
    int cachePeriodInt = cachePeriodLong.intValue();
    Integer cachePeriod = Integer.valueOf(cachePeriodInt);

    registry
        .addResourceHandler(STATIC_RESOURCES)
        .addResourceLocations(resourceProperties.getStaticLocations())
        .setCachePeriod(cachePeriod)
        .resourceChain(true);


    // Create mapping to index.html for Angular HTML5 mode.
    String[] indexLocations = getIndexLocations();
    registry
        .addResourceHandler("/**")
        .addResourceLocations(indexLocations)
        .setCachePeriod(cachePeriod)
        .resourceChain(true)
        .addResolver(
            new PathResourceResolver() {
              @Override
              protected Resource getResource(String resourcePath, Resource location)
                  throws IOException {
                return location.exists() && location.isReadable() ? location : null;
              }
            });
  }

  /**
   * Gets the index locations.
   *
   * @return the index locations
   */
  private String[] getIndexLocations() {
    return Arrays.stream(resourceProperties.getStaticLocations())
        .map((location) -> location + "index.html")
        .toArray(String[]::new);
  }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // This view controller is the key to solving your problem.
        // It is a clean way to tell Spring to forward any path that doesn't
        // match a static resource or an API endpoint to the index.html file.
        registry.addViewController("/{path:[^\\.]*}").setViewName("forward:/index.html");

        // Add a second view controller to catch any multi-level paths that do not contain a period.
        registry
                .addViewController("/{path:[^\\.]*}/**/{path2:[^\\.]*}")
                .setViewName("forward:/index.html");
    }
}
