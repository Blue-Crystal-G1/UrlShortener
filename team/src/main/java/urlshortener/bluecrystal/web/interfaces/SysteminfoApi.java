package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import urlshortener.bluecrystal.domain.messages.SystemInfo;

import java.time.LocalDate;


@Api(value = "systeminfo", description = "the systeminfo API")
public interface SysteminfoApi {

    @ApiOperation(value = "Get information about the system by a type of information and, alternatively, of the given day", notes = "", response = SystemInfo.class, tags={ "systeminfo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = SystemInfo.class),
        @ApiResponse(code = 400, message = "Invalid information type supplied", response = SystemInfo.class),
        @ApiResponse(code = 403, message = "Insufficient permissions (only for administrator)", response = SystemInfo.class),
        @ApiResponse(code = 404, message = "Information type not found", response = SystemInfo.class) })
    @RequestMapping(value = "/systeminfo",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<SystemInfo> getSystemInfo(@ApiParam(value = "The information type that needs to be fetched", required = true) @RequestParam(value = "type", required = true) String type,
                                                     @ApiParam(value = "The day that needs to be fetched") @RequestParam(value = "day", required = false) LocalDate day) {
        // do some magic!
        return new ResponseEntity<SystemInfo>(HttpStatus.OK);
    }

}
