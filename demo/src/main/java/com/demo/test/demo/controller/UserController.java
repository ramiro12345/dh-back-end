package com.demo.test.demo.controller;

import com.demo.test.demo.entity.User;
import com.demo.test.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    //Login USER
    @GetMapping("login/{id}")
    public ResponseEntity<?> login(@PathVariable(value = "id") Long userId){
        Optional<User> oUser = userService.findById(userId);
        if (!oUser.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oUser);
    }
    // Create a new USER
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    // Read USER
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Long userId){
        Optional<User> oUser = userService.findById(userId);
        if (!oUser.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oUser);
    }

    // Update USER
    @CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable(value = "id") Long userId){
        Optional<User> user = userService.findById(userId);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        user.get().setAccountid(userDetails.getAccountid());
        user.get().setFirstName(userDetails.getFirstName());
        user.get().setLastName(userDetails.getLastName());
        user.get().setCreatedDate(userDetails.getCreatedDate());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
    }

    //delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId){
        if (!userService.findById(userId).isPresent()){
            return  ResponseEntity.notFound().build();
        }
        userService.deleteById(userId);
        return ResponseEntity.ok().build();
    }

    // Read All USER
    @GetMapping
    public List<User> redAll(){
        List<User> users = StreamSupport
                .stream(userService.findAll().spliterator(),false)
                .collect(Collectors.toList());
        return users;
    }
}
