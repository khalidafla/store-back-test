package ma.alten.store.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.alten.store.enums.InventoryStatus;
import org.hibernate.annotations.ColumnTransformer;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PRODUCT")
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "QUANTITY")
    private Long quantity;

    @Column(name = "INTERNAL_REFERENCE")
    private String internalReference;

    @Column(name = "SHELL_ID")
    private Long shellId;

    @Enumerated(EnumType.STRING)
    @Column(name = "INVENTORY_STATUS")
    private InventoryStatus inventoryStatus;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "CREATED_DATE", updatable = false)
    @ColumnTransformer(write = "COALESCE(?, CURRENT_TIMESTAMP)")
    @Setter(AccessLevel.NONE)
    private Date createdAt;

    @Column(name = "UPDATED_DATE")
    @ColumnTransformer(write = "COALESCE(CURRENT_TIMESTAMP,?)")
    @Setter(AccessLevel.NONE)
    private Date updatedAt;

    public Product(String code, String name, String description, String image, String category, BigDecimal price, Long quantity, String internalReference, Long shellId, InventoryStatus inventoryStatus, Integer rating) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.image = image;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.internalReference = internalReference;
        this.shellId = shellId;
        this.inventoryStatus = inventoryStatus;
        this.rating = rating;
    }
}
