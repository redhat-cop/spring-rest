package hello;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.metrics.instrument.Counter;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static final String welcome = "This is my webservice!";
    private static final List<String> paths = Arrays.asList("/", "/greeting", "/hostinfo");
    private final Counter counter;

    public GreetingController(MeterRegistry registry) {
       counter = registry.counter("greeting_counter");
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        counter.increment();
        return new Greeting((int)counter.count(),
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
