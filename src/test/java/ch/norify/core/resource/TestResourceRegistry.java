package ch.norify.core.resource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class TestResourceRegistry {

  @Autowired
  private ResourceRegistry resourceRegistry;

  @Test
  public void testThatResourceRegistryLoads() {
    assertThat(resourceRegistry, is(notNullValue()));
    assertThat(resourceRegistry.get("resources"), is(notNullValue()));
  }

  @Test
  public void testResourceExtraction() throws IOException {
    resourceRegistry.get("resources").ifPresent(FileResource::writeToTempFile);
  }
}
