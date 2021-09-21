package com.dealalert.webapp.controllers;

import com.dealalert.webapp.common.ApiResponse;
import com.dealalert.webapp.models.User;
import com.dealalert.webapp.security.token.JwtUtil;
import com.dealalert.webapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtutil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtutil) {
        this.userService = userService;
        this.jwtutil = jwtutil;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers () {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById (@PathVariable("id") String id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User tmpUser = userService.findUserById(user.getId());
        user.setPassword(tmpUser.getPassword());
        user.setRoles(tmpUser.getRoles());
        User updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") String id, @RequestHeader("Authorization") String jwt) {
        String username = jwtutil.getUserNameFromJwtToken(jwtutil.parseJwt(jwt));
        if(!Objects.equals(userService.findUserById(id).getUsername(), username)) {
            userService.deleteUser(id);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User deleted successfully"), HttpStatus.OK);
        }else {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Error while deleting user - can't delete yourself"), HttpStatus.BAD_REQUEST);
        }
    }




}