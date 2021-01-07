package fabio.zup.banco.controller;

import fabio.zup.banco.controller.dto.UserDto;
import fabio.zup.banco.controller.form.UserForm;
import fabio.zup.banco.model.User;
import fabio.zup.banco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping
    @Cacheable(value = "userList")
    public Page<UserDto> list(@PageableDefault(sort="id", direction= Sort.Direction.ASC) Pageable pagination){
        Page<User> users = userService.findAll(pagination);
        return UserDto.convertEntityToDto(users);
    }

    @PostMapping
    @Transactional
    @CacheEvict(value="userList", allEntries = true)
    public ResponseEntity<UserDto> create(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder){
        User user = form.convertFormToEntity();
        userService.save(user);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDto(user));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> detail(@PathVariable Long id) {
    	Optional<User> foundUser = userService.findById(id);
    	if (foundUser.isPresent())
    		return  ResponseEntity.ok(new UserDto(foundUser.get()));

    	return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value="userList", allEntries = true)
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserForm form){
    	Optional<User> foundUser = userService.findById(id);
    	if (foundUser.isPresent()) {
    		User user = form.update(id, userService);
        	return ResponseEntity.ok(new UserDto(user));
    	}
    	
    	return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value="userList", allEntries = true)
    public ResponseEntity<Object> remove(@PathVariable Long id){
    	Optional<User> foundUser = userService.findById(id);
    	if (foundUser.isPresent()) {
    		userService.deleteById(id);
        	return ResponseEntity.ok().build();
    	}
    	
    	return ResponseEntity.notFound().build();
    }
}
