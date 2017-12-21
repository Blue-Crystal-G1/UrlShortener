package urlshortener.bluecrystal.web.interfaces;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.UserDTO;
import urlshortener.bluecrystal.web.messages.ApiJsonResponse;

import java.util.List;

public interface UserApi {

    ResponseEntity<? extends ApiJsonResponse> createUser(@RequestBody UserDTO accountDto, BindingResult result);

    default ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    default ResponseEntity<User> getUserByID(@PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    default ResponseEntity<List<User>> getUserList() {
        // do some magic!
        return new ResponseEntity<>(HttpStatus.OK);
    }

    default ResponseEntity<Void> logoutUser() {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    default ResponseEntity<Void> updateUser(@PathVariable("id") Long id, @RequestBody User body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
