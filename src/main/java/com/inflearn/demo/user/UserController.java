package com.inflearn.demo.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public List<Users> allUser(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public Users selectUser(@PathVariable int id) {
        Users users = service.findOne(id);

        if (users == null){
            throw new UserNotFoundException(String.format("ID[%S] not found", id));
        }

        return users;
    }

    @PostMapping("/users")
    public ResponseEntity<Users> createUser(@Validated @RequestBody Users users){
        Users createUsers = service.save(users);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createUsers.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        Users deleteUsers = service.deleteById(id);

        if(deleteUsers == null){
            throw new UserNotFoundException(String.format("ID[%s] not found",id));
        }
    }
}
