package zhanj.testevent.listeners;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import zhanj.testevent.event.StartEvent;

import java.util.Date;

@Component
public class Job1Listener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == StartEvent.class;
    }

    @Override
    public boolean supportsSourceType(@Nullable Class<?> sourceType) {
        return sourceType == Date.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(event.getSource() + ": Job1 is starting ...");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
