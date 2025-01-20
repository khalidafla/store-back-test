package ma.alten.store.controller;

import ma.alten.store.dto.AuthRequest;
import ma.alten.store.dto.AuthResponse;
import ma.alten.store.dto.UserDto;
import ma.alten.store.entities.User;
import ma.alten.store.services.UserService;
import ma.alten.store.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public LoginController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/account")
    public ResponseEntity<?> register(@Validated @RequestBody UserDto userDto) {
        User user = userService.createUser(userDto);
        return ResponseEntity.ok(
                UserDto.builder()
                        .firstname(user.getFirstname())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .build()
        );
    }

    @PostMapping("/token")
    public ResponseEntity<?> login(@Validated @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );

        String token = jwtUtils.generateToken(authentication.getName());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
