package ma.alten.store.controller;

import ma.alten.store.dto.ProductSumDto;
import ma.alten.store.dto.WishListDto;
import ma.alten.store.entities.User;
import ma.alten.store.entities.WishList;
import ma.alten.store.services.UserService;
import ma.alten.store.services.WishListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/wish")
public class WishListController {

    private final WishListService wishListService;
    private final UserService userService;

    public WishListController(WishListService wishListService, UserService userService) {
        this.wishListService = wishListService;
        this.userService = userService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<WishListDto> addProductToWishList(
            Principal principal,
            @PathVariable Long productId) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(fromEntity(wishListService.addProductToWishList(user, productId)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<WishListDto> removeProductFromWishList(
            Principal principal,
            @PathVariable Long productId) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(fromEntity(wishListService.removeProductFromWishList(user, productId)));
    }

    @GetMapping
    public ResponseEntity<WishListDto> getWishList(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return ResponseEntity.ok(fromEntity(wishListService.getWishList(user)));
    }

    private WishListDto fromEntity(WishList wishList) {
        Set<ProductSumDto> productDtos = wishList.getProducts().stream()
                .map(product -> new ProductSumDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice()
                ))
                .collect(Collectors.toSet());
        return new WishListDto(wishList.getId(), wishList.getUser().getUsername(), productDtos);
    }
}
