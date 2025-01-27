package com.example.retornosAPI.services;
import com.example.retornosAPI.models.Product;
import com.example.retornosAPI.models.ProductEntity;
import com.example.retornosAPI.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Map<String, Object>> createProduct(Product product) {
        validateProduct(product);
        ProductEntity entity = new ProductEntity(null, product.name(),product.description() ,product.price(),product.stockQuantity(), product.category());
        ProductEntity savedEntity = repository.save(entity);
        Map<String, Object> corpoResposta = Map.of("Menssagem", "Produto criado com sucesso", "Dados Produto",
                new Product(savedEntity.getId(), savedEntity.getName(),savedEntity.getDescription() ,savedEntity.getPrice(), savedEntity.getStockQuantity(), savedEntity.getCategory()));
        return ResponseEntity.status(HttpStatus.CREATED).body(corpoResposta);
    }

    public ProductEntity getProductById(Long id) {
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductEntity(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getStockQuantity(), entity.getCategory());
    }

    public List<ProductEntity> getAllProducts() {
        return repository.findAll().stream().map(entity -> new ProductEntity(entity.getId(), entity.getName(), entity.getDescription(),entity.getPrice(), entity.getStockQuantity(), entity.getCategory())).collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    // Atualizar um produto existente
    public Product updateProduct(Long id, Product updatedProduct) {
        // Verificar se o produto existe
        ProductEntity existingEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("Product with ID " + id + " not found"));
        //Chama o metodo de validacoes para seguir os requisitos.
        validateProduct(updatedProduct);
        // Atualizar os dados do produto
        existingEntity.setName(updatedProduct.name());
        existingEntity.setDescription(updatedProduct.description());
        existingEntity.setPrice(updatedProduct.price());
        existingEntity.setStockQuantity(updatedProduct.stockQuantity());
        existingEntity.setCategory(updatedProduct.category());
        // Salvar as alterações no banco de dados
        ProductEntity savedEntity = repository.save(existingEntity);
        // Retornar o produto atualizado
        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getDescription() ,savedEntity.getPrice(), savedEntity.getStockQuantity(), savedEntity.getCategory());
    }

    // Buscar produtos pelo nome
    public List<ProductEntity> getProductsByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The product name can't be empty.");
        }
        List<ProductEntity> entities = repository.findByNameContainingIgnoreCase(name);
        if (entities.isEmpty()) {
            System.out.println("No products found with the name: " + name);
        } else {
            System.out.println("Products found with the name '" + name + "': " + entities.size());
        }
        return entities.stream().map(entity -> new ProductEntity(entity.getId(), entity.getName(), entity.getDescription() ,entity.getPrice(), entity.getStockQuantity(), entity.getCategory())).collect(Collectors.toList());
    }

    private void validateProductName(String name){
        if (name == null || name.length() < 3 || name.length() > 100){
            throw new IllegalArgumentException("The product name must be between 3 and 100 characters.");
        }
    }

    private void validateProductDescription(String description){
        if (description != null && description.length() > 500){
            throw new IllegalArgumentException("The product description can have a maximum of 500 characters.");
        }
    }

    private void validateProductPrice(double price){
        if (price <= 0){
            throw new IllegalArgumentException("The product price must be more than 0.");
        }
    }

    private void validateProductStock(int stockQuantity){
        if (stockQuantity <= 0){
            throw new IllegalArgumentException("The quantity in stock must be greater than or equal to 0.");
        }
    }

    private void validateProduct(Product product){
        validateProductName(product.name());
        validateProductDescription(product.description());
        validateProductPrice(product.price());
        validateProductStock(product.stockQuantity());
    }
}