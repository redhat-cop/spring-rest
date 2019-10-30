package openshift.cop;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class GreetingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGreeting() throws Exception {
		this.mockMvc.perform(get("/v1/greeting").header("Origin", "*")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello, World!")));
	}

}
