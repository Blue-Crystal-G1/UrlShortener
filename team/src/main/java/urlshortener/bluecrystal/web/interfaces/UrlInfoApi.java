package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@ApiIgnore
public interface UrlInfoApi {
    @ApiOperation(value = "", hidden = true)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody ModelAndView index();

    @ApiOperation(value = "", hidden = true)
    @RequestMapping(value = "/urlInfo/{id}/{interval}", method = RequestMethod.GET)
    @ResponseBody ModelAndView getUrlInfoById(@PathVariable("id") String id,
                                              @PathVariable("interval") String interval);

    @ApiOperation(value = "", hidden = true)
    @RequestMapping(value = "/urlInfo", method = RequestMethod.GET)
    @ResponseBody ModelAndView getUrlInfoList(HttpServletRequest request);

}
