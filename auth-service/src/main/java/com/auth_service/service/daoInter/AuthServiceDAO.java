package com.auth_service.service.daoInter;

import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.response.UserResponse;

import java.util.List;

public interface AuthServiceDAO {
    public String login(String username, String password);
    public String register(UserRequest user);
    public List<UserResponse> getUsers();
}
