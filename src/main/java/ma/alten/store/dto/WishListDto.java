package ma.alten.store.dto;

import java.util.Set;

public record WishListDto(Long id, String username, Set<ProductSumDto> products) {
}
