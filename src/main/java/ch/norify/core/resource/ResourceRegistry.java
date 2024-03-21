package ch.norify.core.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class ResourceRegistry {

  private final ConcurrentMap<String, FileResource> fileResources = new ConcurrentHashMap<>();

  public void register(String name, FileResource resource) {
    log.info("Registering file resource '{}'", name);
    fileResources.put(name, resource);
  }

  public Optional<FileResource> get(String name) {
    if (!fileResources.containsKey(name)) {
      log.info("No resource with the name '{}'", name);
      return Optional.empty();
    }
    return Optional.ofNullable(fileResources.get(name));
  }
}
