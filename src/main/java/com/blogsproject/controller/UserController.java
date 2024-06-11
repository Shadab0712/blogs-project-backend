package com.blogsproject.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogsproject.configuration.JwtTokenProvider;
import com.blogsproject.configuration.SecurityConstants;
import com.blogsproject.dto.JWTLoginSucessReponse;
import com.blogsproject.dto.LoginRequest;
import com.blogsproject.dto.UserDTO;
import com.blogsproject.model.User;
import com.blogsproject.repository.UserRepository;
import com.blogsproject.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		User userDetails = userRepository.findByUsername(loginRequest.getUsername());

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);

		UserDTO userDTO = modelMapper.map(userDetails, UserDTO.class);
		JWTLoginSucessReponse response = new JWTLoginSucessReponse(jwt, userDTO);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<String> saveUser(@Valid @RequestBody UserDTO userDTO) {
		userService.saveUser(userDTO);
		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
		UserDTO userDTO = userService.getUserById(id);
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}

	@GetMapping("/allUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> allUsers = userService.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);

	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
		UserDTO updateUser = userService.updateUser(userDTO, id);
		return new ResponseEntity<>(updateUser, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		userService.deleteUserById(id);
		return new ResponseEntity<>("User deleted", HttpStatus.OK);
	}

}
