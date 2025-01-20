package ma.alten.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    public CartProduct(Cart cart, Product product, Long quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }
}
