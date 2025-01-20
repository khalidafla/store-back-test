package ma.alten.store.services;

import ma.alten.store.entities.Product;
import ma.alten.store.entities.User;
import ma.alten.store.entities.WishList;
import ma.alten.store.exceptions.InvalidInputException;
import ma.alten.store.exceptions.ResourceNotFoundException;
import ma.alten.store.repositories.ProductRepository;
import ma.alten.store.repositories.WishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public WishList addProductToWishList(User user, Long productId) {
        var wishList = wishListRepository.findByUser(user)
                .orElseGet(() -> WishList.builder().user(user).build());

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidInputException("Product not found id " + productId));
        wishList.getProducts().add(product);
        return wishListRepository.save(wishList);
    }

    @Transactional
    public WishList removeProductFromWishList(User user, Long productId) {
        WishList wishList = wishListRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Wish list not found for user " + user.getUsername()));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidInputException("Product not found id " + productId));
        wishList.getProducts().remove(product);
        return wishListRepository.save(wishList);
    }

    public WishList getWishList(User user) {
        return wishListRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Wish list not found for user " + user.getUsername()));
    }
}
