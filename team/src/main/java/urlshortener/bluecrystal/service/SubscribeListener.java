package urlshortener.bluecrystal.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Websocket listener for incoming subscribed users
 */
@Service
public class SubscribeListener {

    @Resource
    private Map<String, String> systemInfoInterval;

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        systemInfoInterval.put(event.getUser().getName(),
                ClickInterval.ALL.toString());
    }

    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        systemInfoInterval.remove(event.getUser().getName());
    }
}