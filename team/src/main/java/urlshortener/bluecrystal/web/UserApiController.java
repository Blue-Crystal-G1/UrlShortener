package urlshortener.bluecrystal.web;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.UserService;
import urlshortener.bluecrystal.web.dto.UserDTO;
import urlshortener.bluecrystal.web.interfaces.UserApi;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;
import urlshortener.bluecrystal.web.messages.ApiJsonResponse;
import urlshortener.bluecrystal.web.messages.ApiSuccessResponse;
import urlshortener.bluecrystal.web.validation.UserValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserApiController implements UserApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserValidator userValidator;

    @Autowired
    protected Messages messages;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<? extends ApiJsonResponse> createUser(
            @Valid @RequestBody UserDTO accountDto, BindingResult result) {

        LOGGER.info("Registering user account: {}", accountDto);

        // Check if proved data is correct
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            List<String> errorListMessages = errorList.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            ApiErrorResponse response = new ApiErrorResponse(Joiner.on(";").join(errorListMessages));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            final User registered = userService.registerNewUser(accountDto);
            if (registered != null) {
                LOGGER.info("User registration OK: {}", registered.toString());
                return new ResponseEntity<ApiJsonResponse>(new ApiSuccessResponse<Void>(), HttpStatus.CREATED);
            } else {
                ApiErrorResponse response = new ApiErrorResponse(messages.get("message.userAlreadyRegistered"), "UserAlreadyExists");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }
    }

}
