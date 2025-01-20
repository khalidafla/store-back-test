package ma.alten.store.dto;

import java.util.Set;

public record CartDto(Long id, String username, Set<CartProductDto> cartProducts) {
}
