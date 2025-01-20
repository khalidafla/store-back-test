package ma.alten.store.dto;

import java.math.BigDecimal;

public record ProductSumDto(Long id, String name, BigDecimal price) {
}
