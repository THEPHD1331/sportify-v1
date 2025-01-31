package com.sportify.app.service.user;

import com.sportify.app.dto.request.CreateUserRequest;
import com.sportify.app.dto.request.UpdateUserRequest;
import com.sportify.app.entity.User;

/**
 * Author: Paras Dongre
 * Date Created:29-01-2025
 * Time Created:15:41
 */
public interface UserService {

    User getUserById(long userId);
    User createUser(CreateUserRequest userRequest);
    User updateUser(long userId, UpdateUserRequest userRequest);
    void deleteUser(long userId);
}
