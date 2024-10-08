package dev.zbib.server.service;

import dev.zbib.server.model.entity.User;
import dev.zbib.server.model.enums.UserRole;
import dev.zbib.server.model.request.RegisterRequest;
import dev.zbib.server.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(RegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .role(UserRole.USER)
                .build();

        return userRepository.save(user);
    }
}
