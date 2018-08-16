package zhanj.testevent.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

public class StopEvent extends ApplicationEvent {
    public StopEvent(Date stopTime) {
        super(stopTime);
    }
}
