package ch.norify.core.configuration;

import ch.norify.core.resource.FileResource;
import ch.norify.core.resource.ResourceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class StartupApplicationListener implements
  ApplicationListener<ContextRefreshedEvent>, ApplicationRunner {

  @Autowired
  private ResourceRegistry resourceRegistry;

  @Autowired
  private ApplicationArguments args;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.debug("Application loaded: {}", event);
    resourceRegistry.register("JDBC_SQLSERVER_WIN_AUTH_JAR", FileResource.builder().path("/jdbc/sqlserver-win-auth.zip").build());
    resourceRegistry.register("resources", FileResource.builder().path("/resources.txt").build());
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("arguments: {}", Arrays.deepToString(args.getSourceArgs()));
    if (args.getSourceArgs().length == 0) {
      log.info("expected arguments: --extract=<resource> --pwd=<password>");
      return;
    }

    String[] parts = args.getSourceArgs()[0].split(" ");
    String extract = null;
    String pwd = null;

    for (String part : parts) {
      if (part.startsWith("--extract=")) {
        extract = part.substring("--extract=".length());
      } else if (part.startsWith("--pwd=")) {
        pwd = part.substring("--pwd=".length());
      }
    }
    log.info("extract: '{}' pwd: '{}'", extract, pwd);

    if (extract != null && pwd != null) {
      String finalPwd = pwd;
      resourceRegistry.get(extract).ifPresent(r -> r.extractResource(finalPwd));
    }
  }
}
