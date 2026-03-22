package com.ecommerce.user.Controller;

import com.ecommerce.user.DTO.UserRequest;
import com.ecommerce.user.DTO.UserResponse;
import com.ecommerce.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor  //no need for creating constructor manually, lombok based annotation
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private Object user;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @GetMapping(value = "/users/all", headers = "content-type=text/text", produces = "text/plain")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllusers(), HttpStatus.OK);
    }

    @GetMapping("/public/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id)
    {
        logger.info("Request received for user: {}"+ id);
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/public/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest)
    {
        userService.addUser(userRequest);

        return new ResponseEntity<>("user is added", HttpStatus.OK);
    }

    @PostMapping("/public/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable(name = "id") Long id,
                                             @RequestBody UserRequest userRequest)
    {
        boolean updated = userService.updateUser(id, userRequest);

        if(updated)
            return new ResponseEntity<>("updated successfully!", HttpStatus.OK);

        return ResponseEntity.notFound().build();
    }

}
