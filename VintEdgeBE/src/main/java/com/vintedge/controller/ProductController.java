package com.vintedge.controller;

import com.vintedge.model.Product;
import com.vintedge.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 🔹 GET: Ottieni tutti i prodotti
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 🔹 GET: Ottieni un prodotto per ID
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // 🔹 POST: Aggiungi un nuovo prodotto
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // 🔹 POST: Aggiungi una lista di prodotti
    @PostMapping("/bulk")
    public List<Product> createProducts(@RequestBody List<Product> products) {
        return productRepository.saveAll(products);
    }

    // 🔹 PUT: Aggiorna un prodotto esistente
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
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
        }).orElse(null);
    }

    // 🔹 DELETE: Elimina un prodotto per ID
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}