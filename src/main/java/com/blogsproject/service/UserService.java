package com.blogsproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogsproject.dto.UserDTO;
import com.blogsproject.exception.UsernameAlreadyExistsException;
import com.blogsproject.model.User;
import com.blogsproject.repository.UserRepository;
import com.blogsproject.service.contracts.UserServiceContracts;

@Service
public class UserService implements UserServiceContracts {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User saveUser(UserDTO newUser) {

		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

			// Username has to be unique (exception)
			newUser.setUsername(newUser.getUsername());

			User user = modelMapper.map(newUser, User.class);

			return userRepository.save(user);

		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}

	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Long id) {
		Optional<User> userById = userRepository.findById(id);
		if (userById.isPresent()) {
			User getUser = userById.get();

			getUser.setName(userDTO.getName());
			getUser.setUsername(userDTO.getUsername());
			getUser.setMobileNumber(userDTO.getMobileNumber());
			getUser.setPassword(userDTO.getPassword());

			User updatedUser = userRepository.save(getUser);
			return modelMapper.map(updatedUser, UserDTO.class);
		}
		return null;
	}

	@Override
	public UserDTO getUserById(Long id) {
		Optional<User> userById = userRepository.findById(id);
		if (userById.isPresent()) {
			User getUser = userById.get();
			return modelMapper.map(getUser, UserDTO.class);
		}
		return null;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> listOfUsers = new ArrayList<>();
		Iterable<User> allUsers = userRepository.findAll();
		allUsers.forEach(user -> {
			listOfUsers.add(modelMapper.map(user, UserDTO.class));
		});
		return listOfUsers;
	}

	@Override
	public boolean deleteUserById(Long id) {
		userRepository.deleteById(id);
		return true;
	}

}