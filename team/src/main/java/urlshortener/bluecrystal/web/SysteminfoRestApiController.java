package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.SystemInfoService;
import urlshortener.bluecrystal.web.dto.SystemInfoDTO;
import urlshortener.bluecrystal.web.interfaces.SysteminfoRestApi;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;

@RestController
public class SysteminfoRestApiController implements SysteminfoRestApi {

    @Autowired
    protected Messages messages;

    @Autowired
    protected SystemInfoService systemInfoService;

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/systeminfo/{interval}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<?> getSystemInfo(@PathVariable("interval") String interval) {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null && userDetails.getRoles().contains(new Role(Role.ROLE_ADMIN))) {
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

}
