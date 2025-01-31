package com.sportify.app.service.user;

import com.sportify.app.dto.request.CreateUserRequest;
import com.sportify.app.dto.request.UpdateUserRequest;
import com.sportify.app.entity.User;
import com.sportify.app.enums.UserRole;
import com.sportify.app.exception.AlreadyExistsException;
import com.sportify.app.exception.ResourceNotFoundException;
import com.sportify.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Author: Paras Dongre
 * Date Created:30-01-2025
 * Time Created:23:37
 */
@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not Found"));
    }

    @Override
    public User createUser(CreateUserRequest userRequest) {

        return Optional.of(userRequest)
                // Check if email is already in use, else create new user
                .filter(user -> !userRepository.existsByEmail(userRequest.getEmail()))
                .map(request ->{
                    User user = new User();
                    user.setUserName(userRequest.getUserName());
                    user.setEmail(userRequest.getEmail());
                    user.setPassword(userRequest.getPassword());
                    user.setAddress(userRequest.getAddress());
                    user.setMobileNo(userRequest.getMobileNo());
                    user.setUserRole(UserRole.ROLE_USER);

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new AlreadyExistsException("User Already Exists"));
    }

    @Override
    public User updateUser(long userId, UpdateUserRequest userRequest) {

        return userRepository.findById(userId)
                .map(existingUser ->{
                    existingUser.setUserName(userRequest.getUserName());
                    existingUser.setAddress(userRequest.getAddress());
                    existingUser.setMobileNo(userRequest.getMobileNo());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(user -> userRepository.delete(user),
                        () -> {
                    throw new ResourceNotFoundException("User not Found");
                        });
    }
}
