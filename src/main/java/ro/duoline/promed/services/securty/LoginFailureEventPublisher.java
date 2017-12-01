package ro.duoline.promed.services.securty;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author I.T.W764
 */
@Component
public class LoginFailureEventPublisher implements ApplicationEventPublisherAware {
   
    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    public void publish(LoginFailureEvent event){
        this.publisher.publishEvent(event);
    }
}
