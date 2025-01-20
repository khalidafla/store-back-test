package ma.alten.store.controller;

import ma.alten.store.dto.CartDto;
import ma.alten.store.dto.CartProductDto;
import ma.alten.store.entities.Cart;
import ma.alten.store.services.CartService;
import ma.alten.store.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<CartDto> getCart(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(fromEntity(cartService.getCartForUser(user)));
    }

    @PostMapping
    public ResponseEntity<CartDto> addToCart(
            Principal principal,
            @RequestParam Long productId,
            @RequestParam Long quantity) {
        var user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(fromEntity(cartService.addToCart(user, productId, quantity)));
    }

    @PutMapping
    public ResponseEntity<CartDto> updateCartItem(
            Principal principal,
            @RequestParam Long productId,
            @RequestParam Long quantity) {
        var user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(fromEntity(cartService.updateCartItem(user, productId, quantity)));
    }

    @DeleteMapping()
    public ResponseEntity<Void> clearCart(Principal principal) {
        var user = userService.findByUsername(principal.getName());
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }

    private CartDto fromEntity(Cart cart) {
        Set<CartProductDto> cartProductDtos = cart.getCartProducts().stream()
                .map(cartProduct -> new CartProductDto(
                        cartProduct.getProduct().getId(),
                        cartProduct.getProduct().getName(),
                        cartProduct.getQuantity()
                ))
                .collect(Collectors.toSet());
        return new CartDto(cart.getId(), cart.getUser().getUsername(), cartProductDtos);
    }
}
