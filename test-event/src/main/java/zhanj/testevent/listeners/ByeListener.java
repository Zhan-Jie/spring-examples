package zhanj.testevent.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import zhanj.testevent.event.StopEvent;

@Component
public class ByeListener implements ApplicationListener<StopEvent> {

    @Override
    public void onApplicationEvent(StopEvent stopEvent) {
        System.out.println("Bye Bye, application stopped. current time is " + stopEvent.getSource());
    }
}
