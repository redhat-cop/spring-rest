package hello;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.annotations.ApiOperation;

@RestController
public class GreetingController {

	@Autowired
	private GreetingRepository respository;

	private static final String template = "Hello, %s!";
	private final Counter counter;

	public GreetingController() {
		counter = new Counter("greeting_counter");
	}

	@GetMapping("/")
	public RedirectView index() {
		return new RedirectView("swagger-ui.html");
	}

	@RequestMapping(value = "/v1/greeting", method = RequestMethod.POST)
	@ApiOperation(value = "Saves a greeting for a given name")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		counter.increment();
		Greeting greeting = new Greeting((int) counter.count(), String.format(template, name));
		respository.save(greeting);
		return greeting;
	}

	@RequestMapping(value = "/v1/greeting", method = RequestMethod.GET)
	@ApiOperation(value = "Returns A List of Greetings")
	public List<Greeting> allGreetings() {
		return respository.findAll();
	}

	@RequestMapping(value = "/v1/hostinfo", method = RequestMethod.GET)
	@ApiOperation(value = "List host name running the sample application.")
	public HostInfo hostinfo() throws IOException {
		return new HostInfo();
	}

	@RequestMapping(value = "/v1/envinfo", method = RequestMethod.GET)
	@ApiOperation(value = "Display environment variables.")
	public EnvInfo envinfo(@RequestParam(value = "filter", defaultValue = "*") String filter) throws IOException {
		return new EnvInfo(filter);
	}

}
