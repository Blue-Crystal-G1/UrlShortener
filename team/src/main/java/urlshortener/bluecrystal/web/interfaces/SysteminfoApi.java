package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Api(value = "systeminfo", description = "the systeminfo API")
public interface SysteminfoApi {

    @ApiOperation(value = "Get information about the system by an interval", tags={ "systeminfo", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid interval supplied"),
            @ApiResponse(code = 403, message = "Insufficient permissions (only for administrator)") })
    @RequestMapping(value = "/systeminfo/{interval}",
            method = RequestMethod.GET)
    @ResponseBody ModelAndView getSystemInfo(
            @ApiParam(value = "The interval that needs to be fetched") @PathVariable("interval") String interval);

}
