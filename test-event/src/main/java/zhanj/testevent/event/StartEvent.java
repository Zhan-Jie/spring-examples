package zhanj.testevent.event;

import org.springframework.context.ApplicationEvent;

import java.util.Date;

public class StartEvent extends ApplicationEvent {
    public StartEvent(Date startTime) {
        super(startTime);
    }
}
