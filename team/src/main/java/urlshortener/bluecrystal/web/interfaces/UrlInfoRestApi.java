package urlshortener.bluecrystal.web.interfaces;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Api(value = "urlInfo", description = "the urlInfo API")
public interface UrlInfoRestApi {


    @RequestMapping(value = "/urlInfo/{id}/{interval}",
        produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<?> getUrlInfoById(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id,
                                                       @ApiParam(value = "The interval to show information about clicks", required = true) @PathVariable("interval") String interval);


    @RequestMapping(value = "/urlInfo", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getUrlInfoList();
}
