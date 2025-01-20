package ma.alten.store.services;

import ma.alten.store.entities.Cart;
import ma.alten.store.entities.CartProduct;
import ma.alten.store.entities.Product;
import ma.alten.store.entities.User;
import ma.alten.store.exceptions.ResourceNotFoundException;
import ma.alten.store.repositories.CartRepository;
import ma.alten.store.repositories.ProductRepository;
import ma.alten.store.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Cart getCartForUser(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> createCartForUser(user));
    }

    @Transactional
    public Cart addToCart(User user, Long productId, Long quantity) {
        Cart cart = getCartForUser(user);
        Optional<CartProduct> existingItem = cart.getCartProducts().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartProduct item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productId));
            cart.addProduct(product, quantity);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartItem(User user, Long productId, Long quantity) {
        Cart cart = getCartForUser(user);
        CartProduct item = cart.getCartProducts().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart id " + productId));

        if (quantity <= 0) {
            cart.removeProduct(productId);
        } else {
            item.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = getCartForUser(user);
        cart.getCartProducts().clear();
        cartRepository.save(cart);
    }

    private Cart createCartForUser(User user) {
        return cartRepository.save(Cart.builder().user(user).build());
    }
}
