package pl.on.full.hack.auth.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.on.full.hack.auth.config.SecurityConstants;
import pl.on.full.hack.auth.dto.UserDTO;
import pl.on.full.hack.auth.exception.UserAlreadyExistsException;
import pl.on.full.hack.auth.service.UserService;
import pl.on.full.hack.base.dto.BaseApiContract;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = SecurityConstants.SIGN_UP_URL)
    public ResponseEntity<BaseApiContract<UserDTO>> signUp(@RequestBody UserDTO user) {
        final BaseApiContract<UserDTO> responseBody = new BaseApiContract<>();
        try {
            userService.signUp(user);
            //Na pewno chcemy zwracać usera z hashowanym hasłem?
            responseBody.setSpecificContract(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (UserAlreadyExistsException e) {
            responseBody.setError("User " + user.getUsername() + " already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        } catch (Exception e) {
            responseBody.setError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }
}
