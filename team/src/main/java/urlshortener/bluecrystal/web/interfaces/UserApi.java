package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.UserDTO;
import urlshortener.bluecrystal.web.messages.ApiJsonResponse;

import java.util.List;

public interface UserApi {

    ResponseEntity<? extends ApiJsonResponse> createUser(@RequestBody UserDTO accountDto, BindingResult result);


    @ApiOperation(value = "Delete user", notes = "User deletion. This can only be done by the logged in user to its own profile. Admin can delete any user.", response = Void.class, tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 400, message = "Invalid id supplied", response = Void.class),
            @ApiResponse(code = 403, message = "Insufficient permissions", response = Void.class),
            @ApiResponse(code = 404, message = "User not found", response = Void.class) })
    default ResponseEntity<Void> deleteUser(@ApiParam(value = "The name that needs to be deleted", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get user data by ID", notes = "Get user data by ID. The user can only access its own data. Admin can access to any user data. The password is not returned", response = User.class, tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = User.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = User.class),
            @ApiResponse(code = 403, message = "Insufficient permission", response = User.class),
            @ApiResponse(code = 404, message = "User not found", response = User.class) })
    @RequestMapping(value = "/user/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    default ResponseEntity<User> getUserByID(@ApiParam(value = "The ID that needs to be fetched.", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get all users list", notes = "Get all users list. This can only be obtained by an admin", response = User.class, responseContainer = "List", tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = User.class),
            @ApiResponse(code = 403, message = "Insufficient permissions", response = User.class) })
    @RequestMapping(value = "/user",
            produces = { "application/json" },
            method = RequestMethod.GET)
    default ResponseEntity<List<User>> getUserList() {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Logs out current logged in user session", notes = "", response = Void.class, tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 401, message = "The user is not logged in", response = Void.class) })
    @RequestMapping(value = "/logout",
            produces = { "application/json" },
            method = RequestMethod.GET)
    default ResponseEntity<Void> logoutUser() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @ApiOperation(value = "Update user", notes = "This can only be done by the logged in user. Admin can update information of all users except email. User can only update its own data except roles.", response = Void.class, tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 403, message = "Insufficient permission", response = Void.class),
            @ApiResponse(code = 404, message = "User not found", response = Void.class) })
    @RequestMapping(value = "/user/{id}",
            produces = { "application/json" },
            method = RequestMethod.PUT)
    default ResponseEntity<Void> updateUser(@ApiParam(value = "user id that need to be updated", required = true) @PathVariable("id") Long id,
                                            @ApiParam(value = "Updated user object", required = true) @RequestBody User body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
