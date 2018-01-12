package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.SystemInfoService;
import urlshortener.bluecrystal.web.dto.SystemInfoDTO;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.interfaces.SysteminfoRestApi;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class SysteminfoRestApiController implements SysteminfoRestApi {

    @Autowired
    protected Messages messages;

    @Autowired
    protected SystemInfoService systemInfoService;

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    protected SimpUserRegistry simpUserRegistry;

    @Autowired
    protected SimpMessagingTemplate template;

    @Resource
    private Map<String, String> systemInfoInterval;

    @RequestMapping(value = "/systeminfo/{interval}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getSystemInfo(@PathVariable("interval") String interval) {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null && userDetails.getRoles().contains(new Role(Role.ROLE_ADMIN))) {
            // Only admin can set his interval associated to its session id
            if (simpUserRegistry.getUsers().size() == 1 && simpUserRegistry
                    .getUsers().stream().findFirst().isPresent()) {
                String adminWSName = simpUserRegistry.getUsers().stream()
                        .findFirst().get().getName();
                systemInfoInterval.put(adminWSName, interval.toUpperCase());
            }
            SystemInfoDTO info = systemInfoService.getSystemGlobalInformation(interval.toUpperCase());
            if (info != null) {
                return new ResponseEntity<>(info, HttpStatus.OK);
            } else {
                ApiErrorResponse response = new ApiErrorResponse(messages.get("message.badIntervalSpecified"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            ApiErrorResponse response = new ApiErrorResponse(messages.get("message.forbidden"));
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Sends system information to the administrator through a STOMP WS
     */
    @SendTo("/topic/statsChanged")
    public void sendSystemInfoStomp() {
        if (simpUserRegistry.getUsers().size() == 1 && simpUserRegistry
                .getUsers().stream().findFirst().isPresent()) {

            String adminWSName = simpUserRegistry.getUsers().stream()
                    .findFirst().get().getName();
            SystemInfoDTO info;
            if (systemInfoInterval != null && systemInfoInterval.containsKey(adminWSName))
                // Get admin specific interval
                info = systemInfoService.getSystemGlobalInformation(
                        systemInfoInterval.get(adminWSName));
            else
                info = systemInfoService.getSystemGlobalInformation(
                        ClickInterval.ALL.toString());

            if (info != null)
                template.convertAndSendToUser(adminWSName,
                        "/topic/statsChanged", info);
            else
                template.convertAndSendToUser(adminWSName,
                        "/topic/statsChanged", "unknown");
        }
    }

}
