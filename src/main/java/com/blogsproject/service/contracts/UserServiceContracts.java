package com.blogsproject.service.contracts;

import java.util.List;

import com.blogsproject.dto.UserDTO;
import com.blogsproject.model.User;

public interface UserServiceContracts {

	public User saveUser(UserDTO newUser);

	public UserDTO updateUser(UserDTO userDTO, Long id);

	public UserDTO getUserById(Long id);

	public List<UserDTO> getAllUsers();

	public boolean deleteUserById(Long id);

}
