package projet.spring.restControllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import projet.spring.entities.User;
import projet.spring.repos.UserRepository;
import projet.spring.service.UserService;
import projet.spring.service.register.RegistrationRequest;
@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "all",method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userRep.findAll();
	 }
	
	
	@PostMapping("/register")
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);
		
	}
	
	
	@GetMapping("/verifyEmail/{token}")
	 public User verifyEmail(@PathVariable("token") String token){
			return userService.validateToken(token);
	 }
	
	
}