package com.auth_service.service.daoImpl;

import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.model.User;
import com.auth_service.repository.UserRepository;
import com.auth_service.service.daoInter.AuthServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.auth_service.utils.JwtUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthServiceDAO {

    //Generacion de token
    @Autowired
    private JwtUtil jwtUtil;

    //Crud de User
    @Autowired
    private UserRepository userRepository;

    @Override
    public String login(String username, String password) {

        // Aquí iría la validación real con la base de datos
        if (username.equals("admin") && password.equals("1234")) {
            Map<String, Object> claims = Map.of("role", "ADMIN");
            return jwtUtil.generateToken(username, claims);
        } else {
            throw new RuntimeException("Credenciales inválidas");
        }
    }

    @Override
    public String register(UserRequest userRequest) {

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setRol(userRequest.getRol());

        userRepository.save(user);

        return "Usuario registrado exitosamente";
    }

    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getRol()))
                .collect(Collectors.toList());
    }


}
