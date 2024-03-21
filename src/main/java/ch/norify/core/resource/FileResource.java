package ch.norify.core.resource;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Builder
@Slf4j
public class FileResource {
  private String path;

  public String writeToTempFile() {
    try {
      File tempFile = File.createTempFile("res-", "");
      FileOutputStream tempWriter = new FileOutputStream(tempFile);
      InputStream inputResourceStream = Objects.requireNonNull(getClass().getResourceAsStream(path));
      FileCopyUtils.copy(inputResourceStream, tempWriter);

      log.info("the resource was written to: {}", tempFile);

      return tempFile.getAbsolutePath();
    } catch (IOException ignored) {
      log.warn("Cannot extract the file corresponding to {}", path);
    }
    return null;
  }

  public void extractResource(String password) {
    extractZip(writeToTempFile(), password);
  }

  private void extractZip(String path, String password) {
    log.info("ZIP archive: '{}'", path);
    try(ZipFile zf = new ZipFile(path, password.toCharArray())) {
      log.info("ZIP is encrypted: {}", zf.isEncrypted());
      zf.extractAll(".");
    } catch (IOException e) {
      log.warn("could not extract ZIP resource", e);
    }
  }
}
