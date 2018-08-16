package zhanj.testevent.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import zhanj.testevent.event.StartEvent;

@Component
public class HelloListener implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof StartEvent) {
            System.out.println("Hello, application started! current time is " + event.getSource());
        }
    }
}
