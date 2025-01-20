package ma.alten.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ma.alten.store.validator.InventoryStatusConstraint;

import java.math.BigDecimal;
import java.util.Date;

public record ProductDTO(
    Long id,
    @NotBlank String code,
    @NotBlank String name,
    String description,
    String image,
    @NotBlank String category,
    @NotNull @Positive BigDecimal price,
    @Positive Long quantity,
    String internalReference,
    Long shellId,
    @InventoryStatusConstraint String inventoryStatus,
    @Positive Integer rating,
    Date createdAt,
    Date updatedAt
) {
}
