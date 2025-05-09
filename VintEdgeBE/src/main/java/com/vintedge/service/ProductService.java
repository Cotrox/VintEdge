package com.vintedge.service;

import com.vintedge.model.Product;
import com.vintedge.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 🔹 Ottieni tutti i prodotti
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 🔹 Ottieni un prodotto per ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 🔹 Aggiungi un nuovo prodotto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // 🔹 Aggiungi una lista di prodotti
    public List<Product> createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    // 🔹 Aggiorna un prodotto esistente
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setImage(productDetails.getImage());
            product.setCategory(productDetails.getCategory());
            product.setPrice(productDetails.getPrice());
            product.setDiscount(productDetails.getDiscount());
            product.setDiscounted(productDetails.getDiscounted());
            product.setQuantity(productDetails.getQuantity());
            product.setAvailable(productDetails.getAvailable());
            product.setRating(productDetails.getRating());
            return productRepository.save(product);
        });
    }

    // 🔹 Elimina un prodotto per ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}