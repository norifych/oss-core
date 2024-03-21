package ch.norify.core.configuration;

import ch.norify.core.resource.ResourceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResourceConfiguration {

  @Bean
  public ResourceRegistry resourceRegistry() {
    return new ResourceRegistry();
  }
}
