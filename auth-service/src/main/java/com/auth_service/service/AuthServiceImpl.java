package com.auth_service.service;

import com.auth_service.dto.request.ChangePasswordRequest;
import com.auth_service.dto.request.UserRequest;
import com.auth_service.dto.response.UserResponse;
import com.auth_service.model.User;
import com.auth_service.repository.UserRepository;
import com.auth_service.utils.JwtUtil;
import com.auth_service.utils.UserCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que implementa el manejo de autenticación:
 * - Login real con validación de credenciales desde la base de datos
 * - Registro con encriptación de contraseña
 * - Generación de JWT con claims personalizados
 * - Listado de usuarios
 * - Funciones adicionales: obtener perfil, cambiar contraseña, desactivar usuario, validar token
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserCodeGenerator userCodeGenerator;


    @Override
    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (user.isActive() == false) {
            throw new RuntimeException("Usuario inactivo");
        }

        Map<String, Object> claims = Map.of("role", user.getRol());
        return jwtUtil.generateToken(user.getUsername(), claims);
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        String uniqueCode = userCodeGenerator.generateUniqueUserCode();

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .rol(userRequest.getRol())
                .active(true)
                .userCode(uniqueCode)
                .build();

        userRepository.save(user);

        return new UserResponse(user.getUserCode(),user.getUsername(),user.getRol(), user.isActive());
    }


    @Override
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserResponse(user.getUserCode(), user.getUsername(), user.getRol(), user.isActive()))
                .collect(Collectors.toList());
    }

    /**
     * Obtener perfil de usuario por username
     */
    @Override
    public UserResponse getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UserResponse(user.getUserCode(), user.getUsername(), user.getRol(),user.isActive());
    }

    /**
     * Cambiar contraseña de usuario:
     * Se valida la contraseña actual antes de cambiar.
     */
    @Override
    public String changePassword(String username, ChangePasswordRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Contraseña actualizada exitosamente";
    }

    @Override
    public String toggleUserStatus(String userCode) {
        User user = userRepository.findByUserCode(userCode)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        boolean newStatus = !user.isActive();
        user.setActive(newStatus);

        userRepository.save(user);

        return "Usuario " + (newStatus ? "activado" : "desactivado") + " exitosamente";
    }


    /**
     * Validar si el token es válido y no expiró
     */
    @Override
    public boolean validateToken(String token) {
        try {
            String username = jwtUtil.extractUsername(token);
            return !jwtUtil.isTokenExpired(token) && userRepository.findByUsername(username).isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserResponse getUserByUserCode(String userCode) {
        User user = userRepository.findByUserCode(userCode)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return new UserResponse(user.getUserCode(), user.getUsername(), user.getRol(), user.isActive());
    }

}
