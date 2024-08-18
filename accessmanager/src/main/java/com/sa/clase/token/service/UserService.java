package com.sa.clase.token.service;


import com.sa.clase.token.interfaces.RoleRepository;
import com.sa.clase.token.interfaces.UserRepository;
import com.sa.clase.token.model.User;
import com.sa.clase.token.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(User registrationRequest) throws Exception {
        try {
            // Verificar si el usuario ya existe
            if (userRepository.existsByEmail(registrationRequest.getEmail())) {
                throw new Exception("User already exists");
            }
            // Crear un nuevo usuario
            User newUser = new User();
            newUser.setName(registrationRequest.getName());
            newUser.setEmail(registrationRequest.getEmail());
            // Encriptar la contraseña
            newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            // Setear otros campos según sea necesario
            newUser.setEnabled(true);
            // Guardar los roles del usuario
            Set<Role> roles = new HashSet<>();
            for (Role roleDto : registrationRequest.getRoles()) {
                Role role = roleRepository.findByName(roleDto.getName());
                roles.add(role);
            }
            newUser.setRoles(roles);
            userRepository.save(newUser);
        } catch (Exception e) {
            throw new Exception("Error al guardar el usuario");
        }
    }

    public void delete(Long id) throws Exception {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception("Error al eliminar el usuario");
        }
    }
}
