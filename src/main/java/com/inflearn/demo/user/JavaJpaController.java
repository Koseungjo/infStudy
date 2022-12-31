package com.inflearn.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.stream.Location;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class JavaJpaController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @GetMapping("/jpa/users")
    public List<Users> retrieveAllUser(){
        List<Users> list = userRepository.findAll();
        return list;
    }

    @GetMapping("/jpa/users/{id}")
    public Users retrieveUser(@PathVariable int id){
        Optional<Users> user = userRepository.findById(id);

        if (!user.isPresent())
            throw new UserNotFoundException("ID[%s] not found");

        return user.get();
    }

    @DeleteMapping("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id){
        userRepository.deleteById(id);
    }

    @PostMapping("/jpa/users")
    public ResponseEntity<Users> createUser(@Valid @RequestBody Users users){
        Users user = userRepository.save(users);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostByUser(@PathVariable int id){
        Optional<Users> user = userRepository.findById(id);

        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post){

        Optional<Users> user = userRepository.findById(id);

        if(!user.isPresent()){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        post.setUser(user.get());
        Post savePost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savePost.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
