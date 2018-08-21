package zhanj.testevent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import zhanj.testevent.event.StartEvent;
import zhanj.testevent.event.StopEvent;

import java.util.Date;

@SpringBootApplication
public class TestEventApplication {

	public static void main(String[] args) throws InterruptedException {
		ApplicationContext ctx = SpringApplication.run(TestEventApplication.class, args);
		ctx.publishEvent(new StartEvent(new Date()));
		Thread.sleep(5000);
		ctx.publishEvent(new StopEvent(new Date()));
	}
}
