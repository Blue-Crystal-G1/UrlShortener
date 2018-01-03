package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.UserDTO;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;

public interface UserApi {
    @ApiOperation(value = "Creates a new user",
            notes = "Creates a new user by the provided information", tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful operation", response = User.class),
            @ApiResponse(code = 400, message = "Invalid data supplied", response = ApiErrorResponse.class),
            @ApiResponse(code = 409, message = "Insufficient permissions", response = ApiErrorResponse.class) })
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    ResponseEntity<?> createUser(@RequestBody UserDTO accountDto, BindingResult result);
}
