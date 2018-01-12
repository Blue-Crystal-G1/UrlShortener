package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.SystemInfoService;
import urlshortener.bluecrystal.web.annotations.AnnotationHelper;
import urlshortener.bluecrystal.web.annotations.DynamicLayout;
import urlshortener.bluecrystal.web.annotations.Layout;
import urlshortener.bluecrystal.web.dto.SystemInfoDTO;
import urlshortener.bluecrystal.web.interfaces.SysteminfoApi;

@Layout(Layout.DEFAULT)
@Controller
public class SysteminfoApiController implements SysteminfoApi {

    @Autowired
    protected Messages messages;

    @Autowired
    protected SystemInfoService systemInfoService;

    @Autowired
    protected AuthenticationFacade authenticationFacade;


    @RequestMapping(value = "/systeminfo/{interval}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getSystemInfo(@PathVariable("interval") String interval) {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null && userDetails.getRoles().contains(new Role(Role.ROLE_ADMIN))) {
            SystemInfoDTO info = systemInfoService.getSystemGlobalInformation(interval.toUpperCase());
            if (info != null) {
                modifyLayout(Layout.DEFAULT);
                return new ModelAndView("stats", HttpStatus.OK)
                        .addObject("info", info);
            } else {
                modifyLayout(Layout.NONE);
                return new ModelAndView("400", HttpStatus.BAD_REQUEST);
            }
        } else {
            modifyLayout(Layout.NONE);
            return new ModelAndView("403", HttpStatus.FORBIDDEN);
        }
    }

    private void modifyLayout(String layout) {
        DynamicLayout altered = new DynamicLayout(layout);
        AnnotationHelper.alterAnnotationOn(SysteminfoApiController.class, Layout.class, altered);
    }

}
