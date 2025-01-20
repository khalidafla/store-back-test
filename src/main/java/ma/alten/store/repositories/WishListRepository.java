package ma.alten.store.repositories;

import ma.alten.store.entities.User;
import ma.alten.store.entities.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUser(User user);
}
