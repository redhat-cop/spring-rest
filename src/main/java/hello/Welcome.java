package hello;

import java.util.List;
import java.util.Arrays;

public class Welcome {

  private final String message;
  private final List<String> paths;

  public Welcome(String message, List<String> paths) {
    this.message = message;
    this.paths = paths;
  }

  public String getMessage() {
    return message;
  }

  public String getPaths() {
    return Arrays.toString(paths.toArray());
  }
}
