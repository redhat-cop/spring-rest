package hello;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static final String welcome = "This is my webservice!";
    private static final List<String> paths = Arrays.asList("/", "/greeting", "/hostinfo");
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }

    @RequestMapping("/")
    public Welcome welcome() {
      return new Welcome(welcome, paths);
    }

    @RequestMapping("/hostinfo")
    public HostInfo hostinfo() throws IOException {
      return new HostInfo();
    }
}
