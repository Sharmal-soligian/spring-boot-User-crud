package com.sharmal.springbootreactfullstack.controller;

import com.sharmal.springbootreactfullstack.exception.UserNotFoundException;
import com.sharmal.springbootreactfullstack.model.User;
import com.sharmal.springbootreactfullstack.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/user")
    public ResponseEntity<User> newUser(@RequestBody User user) {
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }

//    @GetMapping("/user/{id}")
//    public ResponseEntity<User> getUserById(@PathVariable Long id) {
//        Optional<User> user = userRepo.findById(id);
//        if (user.isPresent()) {
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @PutMapping("/user/{id}")
    User updateUser(@PathVariable Long id, @RequestBody User newUser) {
        return userRepo.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setName(newUser.getName());
                    user.setEmail(newUser.getEmail());
                    return userRepo.save(user);
                }).orElseThrow(() -> new  UserNotFoundException(id));
    }

    @DeleteMapping("/user/{id}")
    String deleteUser(@PathVariable Long id) {
        Optional<User> usertoDelete = userRepo.findById(id);
        if (!usertoDelete.isPresent()) {
            throw new UserNotFoundException(id);
        }

        String userMessage = usertoDelete.get().getName();
        userRepo.deleteById(id);
        return userMessage + " deleted successfully";
    }

}
