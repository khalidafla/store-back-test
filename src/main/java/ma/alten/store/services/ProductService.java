package ma.alten.store.services;

import jakarta.persistence.EntityManager;
import ma.alten.store.dto.ProductDTO;
import ma.alten.store.entities.Product;
import ma.alten.store.enums.InventoryStatus;
import ma.alten.store.exceptions.ResourceNotFoundException;
import ma.alten.store.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final EntityManager entityManager;

    @Autowired
    public ProductService(ProductRepository productRepository, EntityManager entityManager) {
        this.productRepository = productRepository;
        this.entityManager = entityManager;
    }

    private ProductDTO mapToDTO(Product product) {
        return new ProductDTO(product.getId(), product.getCode(), product.getName(), product.getDescription(), product.getImage(), product.getCategory(), product.getPrice(), product.getQuantity(), product.getInternalReference(), product.getShellId(), product.getInventoryStatus().toString(), product.getRating(), product.getCreatedAt(), product.getUpdatedAt());
    }

    private Product mapToEntity(ProductDTO productDTO) {
        return new Product(productDTO.code(), productDTO.name(), productDTO.description(), productDTO.image(), productDTO.category(), productDTO.price(), productDTO.quantity(), productDTO.internalReference(), productDTO.shellId(), InventoryStatus.valueOf(productDTO.inventoryStatus()), productDTO.rating());
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found id : " + id));
        return mapToDTO(product);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDTO createProduct(ProductDTO productDTO) {
        var product = mapToEntity(productDTO);
        var savedProduct = productRepository.saveAndFlush(product);
        this.entityManager.refresh(savedProduct);
        return mapToDTO(savedProduct);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found id : " + id));
        if(productDTO.code() != null) {existingProduct.setCode(productDTO.code());}
        if(productDTO.name() != null) {existingProduct.setName(productDTO.name());}
        if(productDTO.description() != null) {existingProduct.setDescription(productDTO.description());}
        if(productDTO.image() != null) {existingProduct.setImage(productDTO.image());}
        if(productDTO.category() != null) {existingProduct.setCategory(productDTO.category());}
        if(productDTO.price() != null) {existingProduct.setPrice(productDTO.price());}
        if(productDTO.quantity() != null) {existingProduct.setQuantity(productDTO.quantity());}
        if(productDTO.internalReference() != null) {existingProduct.setInternalReference(productDTO.internalReference());}
        if(productDTO.shellId() != null) {existingProduct.setShellId(productDTO.shellId());}
        if(productDTO.inventoryStatus() != null) {existingProduct.setInventoryStatus(InventoryStatus.valueOf(productDTO.inventoryStatus()));}
        if(productDTO.rating() != null) {existingProduct.setRating(productDTO.rating());}
        productRepository.saveAndFlush(existingProduct);
        this.entityManager.refresh(existingProduct);
        return mapToDTO(existingProduct);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found id : " + id));
        productRepository.delete(product);
    }
}
