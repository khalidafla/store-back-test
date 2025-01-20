package ma.alten.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record UserDto(
        @NotEmpty String username,
        @NotEmpty String firstname,
        @NotEmpty @Email String email,
        @NotEmpty String password
) {
}
