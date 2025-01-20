package ma.alten.store.config;

import lombok.extern.slf4j.Slf4j;
import ma.alten.store.entities.Product;
import ma.alten.store.enums.InventoryStatus;
import ma.alten.store.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {

        return args -> {
            //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            //String password1 = encoder.encode("password123"); // For John
            //String password2 = encoder.encode("password456"); // For Jane
            //System.out.println("Encoded password for John: " + password1);
            //System.out.println("Encoded password for Jane: " + password2);
            //this.bootstrapData().forEach(product -> log.info("Preloading " + repository.save(product)));
            //repository.flush();
        };
    }

    private List<Product> bootstrapData() {
        var products = new ArrayList<Product>();

        // Add Bambou product
        var bamboo = new Product();
        bamboo.setCode("f230fh0g3");
        bamboo.setName("Bamboo Watch");
        bamboo.setDescription("Product Description");
        bamboo.setImage("bamboo-watch.jpg");
        bamboo.setPrice(new BigDecimal(65));
        bamboo.setCategory("Accessories");
        bamboo.setShellId(15L);
        bamboo.setInternalReference("REF-123-456");
        bamboo.setInventoryStatus(InventoryStatus.INSTOCK);
        bamboo.setRating(5);
        products.add(bamboo);

        // Add Watch product
        var watch = new Product();
        watch.setCode("nvklal433");
        watch.setName("Black Watch");
        watch.setDescription("Product Description");
        watch.setImage("black-watch.jpg");
        watch.setPrice(new BigDecimal(72));
        watch.setCategory("Accessories");
        watch.setShellId(15L);
        watch.setInternalReference("REF-123-456");
        watch.setInventoryStatus(InventoryStatus.INSTOCK);
        watch.setRating(4);
        products.add(watch);

        // Add Band product
        var band = new Product();
        band.setCode("zz21cz3c1");
        band.setName("Blue Band");
        band.setDescription("Product Description");
        band.setImage("blue-band.jpg");
        band.setPrice(new BigDecimal(79));
        band.setCategory("Fitness");
        band.setShellId(15L);
        band.setInternalReference("REF-123-456");
        band.setInventoryStatus(InventoryStatus.LOWSTOCK);
        band.setRating(3);
        products.add(band);

        return products;
    }
}
