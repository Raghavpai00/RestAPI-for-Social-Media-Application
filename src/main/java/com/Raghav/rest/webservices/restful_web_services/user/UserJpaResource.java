package com.Raghav.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.Raghav.rest.webservices.restful_web_services.exception.PostNotFoundException;
import com.Raghav.rest.webservices.restful_web_services.jpa.PostRepository;
import com.Raghav.rest.webservices.restful_web_services.jpa.UserRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository userRepository,PostRepository postRepository) {
		
		this.userRepository=userRepository;
		this.postRepository=postRepository;
	}
	@GetMapping("/jpa/users")//for getting all users
public List<User> retrieveAllUser() {
	return userRepository.findAll();
}
	
	@GetMapping("/jpa/users/{id}")//for getting perticular user
	public EntityModel< User> retrieveUser(@PathVariable int id) {
		Optional<User> user= userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		
		
		EntityModel<User> entityModel=EntityModel.of(user.get());
		WebMvcLinkBuilder link=linkTo(methodOn(this.getClass()).retrieveAllUser());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}
	
	@DeleteMapping("/jpa/users/{id}")//for deleting perticular user
	public void DeleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
			}
	
	@GetMapping("/jpa/users/{id}/posts")//for getting all the posts from perticular user
	public List<Post> retrivePostsForUser(@PathVariable int id) {
		Optional<User> user= userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		return user.get().getPosts();
			}
	
	@GetMapping("/jpa/users/{userId}/posts/{postId}")// for getting perticular post from perticular user
	public ResponseEntity<Post> getPostForUser(
	        @PathVariable int userId,
	        @PathVariable int postId) {

	    userRepository.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException("id:" + userId));

	    Post post = postRepository.findById(postId)
	            .orElseThrow(() -> new PostNotFoundException("postId:" + postId));

	    return ResponseEntity.ok(post);
	}
 
	@PostMapping("/jpa/users")// for creating a new user
	public ResponseEntity<User> createUsers(  @ Valid  @RequestBody User user) {
		
		User savedUser=userRepository.save(user);
		
		URI location= ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri();
		
	return	ResponseEntity.created(location).build();
	}
	@PostMapping("/jpa/users/{id}/posts") //for uploading a new post for perticular user
	public ResponseEntity<Object> createPostForUser(@PathVariable int id,@ Valid  @RequestBody Post post) {
		Optional<User> user= userRepository.findById(id);
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		post.setUser(user.get());
		
		Post savedPost=postRepository.save(post);
		
		URI location= ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId())
				.toUri();
		
	return	ResponseEntity.created(location).build();
		
			}
	
	@DeleteMapping("/jpa/users/{userId}/posts/{postId}") //for deleting a specific post of specific user
	public ResponseEntity<Void> deletePostForUser(
	        @PathVariable int userId,
	        @PathVariable int postId) {

	    // check user exists
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException("id:" + userId));

	    // check post exists
	    Post post = postRepository.findById(postId)
	            .orElseThrow(() -> new PostNotFoundException("postId:" + postId));

	    // optional safety check: post belongs to user
	    if (!post.getUser().getId().equals(user.getId())) {
	        throw new RuntimeException("Post does not belong to user");
	    }

	    postRepository.delete(post);

	    return ResponseEntity.noContent().build(); // 204 NO CONTENT
	}

}













