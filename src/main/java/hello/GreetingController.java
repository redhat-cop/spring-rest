package hello;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.metrics.instrument.Counter;
import org.springframework.metrics.instrument.MeterRegistry;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static final String welcome = "This is my webservice!";
    private static final List<String> paths = Arrays.asList("/v1/envinfo", "/v1/greeting", "/v1/hostinfo", "/swagger.json");
    private final Counter counter;

    public GreetingController(MeterRegistry registry) {
       counter = registry.counter("greeting_counter");
    }

    @RequestMapping(value = "/v1/greeting" ,  method = RequestMethod.GET)
    @ApiOperation(value = "Generate a greeting for a given name")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        counter.increment();
        return new Greeting((int)counter.count(),
                            String.format(template, name));
    }

    @RequestMapping(value = "/" ,  method = RequestMethod.GET)
    @ApiOperation(value = "Sample REST default path, list operations", nickname = "getGreeting")
    public Welcome welcome() {
      return new Welcome(welcome, paths);
    }

    @RequestMapping(value = "/v1/hostinfo",  method = RequestMethod.GET)
    @ApiOperation(value = "List host name running the sample application.")
    public HostInfo hostinfo() throws IOException {
      return new HostInfo();
    }
    
    @RequestMapping(value = "/v1/envinfo" ,  method = RequestMethod.GET)
    @ApiOperation(value = "Display environment variables.")
    public EnvInfo envinfo(@RequestParam(value="filter", defaultValue="*") String filter) throws IOException {
      return new EnvInfo(filter);
    }
    
}
