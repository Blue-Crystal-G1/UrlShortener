package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
public interface SysteminfoApi {
    @ApiOperation(value = "", hidden = true)
    @RequestMapping(value = "/systeminfo/{interval}", method = RequestMethod.GET)
    @ResponseBody ModelAndView getSystemInfo(@PathVariable("interval") String interval);
}
