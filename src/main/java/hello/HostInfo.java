package hello;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Process;
import java.lang.Runtime;
import java.util.Scanner;

public class HostInfo {

  private final String hostname;

  public HostInfo() throws IOException {
    this.hostname = execReadToString("cat /etc/hostname").trim();
  }

  public String getHostname() {
    return hostname;
  }

  public static String execReadToString(String execCommand) throws IOException {
      Process proc = Runtime.getRuntime().exec(execCommand);
      try (InputStream stream = proc.getInputStream()) {
          try (Scanner s = new Scanner(stream).useDelimiter("\\A")) {
              return s.hasNext() ? s.next() : "";
          }
      }
  }
}
