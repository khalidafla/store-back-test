package ma.alten.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartProduct> cartProducts = new HashSet<>();

    public void addProduct(Product product, Long quantity) {
        CartProduct cartProduct = new CartProduct(this, product, quantity);
        cartProducts.add(cartProduct);
    }

    public void removeProduct(Long productId) {
        cartProducts.removeIf(cartProduct -> cartProduct.getProduct().getId().equals(productId));
    }
}
