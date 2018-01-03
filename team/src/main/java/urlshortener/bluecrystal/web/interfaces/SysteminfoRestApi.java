package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.web.dto.SystemInfoDTO;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;


@Api(value = "systeminfo", description = "the systeminfo API")
public interface SysteminfoRestApi {

    @ApiOperation(value = "Get information about the system by an interval", tags={ "systeminfo", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = SystemInfoDTO.class),
            @ApiResponse(code = 400, message = "Invalid interval supplied", response = ApiErrorResponse.class),
            @ApiResponse(code = 403, message = "Insufficient permissions (only for administrator)", response = ApiErrorResponse.class) })
    @RequestMapping(value = "/systeminfo/{interval}",
            method = RequestMethod.GET,
            produces = { "application/json" })
    ResponseEntity<?> getSystemInfo(
            @ApiParam(value = "The interval that needs to be fetched") @PathVariable("interval") String interval);

}
