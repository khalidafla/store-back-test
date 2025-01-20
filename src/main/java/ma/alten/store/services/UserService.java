package ma.alten.store.services;

import ma.alten.store.dto.UserDto;
import ma.alten.store.entities.User;
import ma.alten.store.exceptions.InvalidInputException;
import ma.alten.store.exceptions.UserException;
import ma.alten.store.repositories.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidInputException("User not found username : " + username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserException {
        var user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return User.builder()
                    .email(user.get().getEmail())
                    .password(user.get().getPassword())
                    .username(user.get().getUsername())
                    .firstname(user.get().getFirstname())
                    .build();
        } else {
            throw new UserException("User not found");
        }
    }

    @Transactional
    public User createUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.username())) {
            throw new UserException("Username is already taken");
        }

        if (userRepository.existsByEmail(userDto.email())) {
            throw new UserException("Email is already registered");
        }

        User user = new User();
        user.setUsername(userDto.username());
        user.setFirstname(userDto.firstname());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));

        return userRepository.save(user);
    }
}
