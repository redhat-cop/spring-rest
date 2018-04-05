package com.hello;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.hello.Application;
import com.hello.Greeting;
import com.hello.GreetingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class GreetingRepositoryTest {

	@Autowired
	private GreetingRepository greetingRepository;

	@Test
	public void saveGreeting() {
		Greeting greeting = new Greeting(1, "Welcome");
		greetingRepository.save(greeting);

		Greeting g = greetingRepository.findOne(greeting.getId());
		assertEquals(1, g.getId());
	}
	
	@Test
	public void contextLoads() {
	}
}
