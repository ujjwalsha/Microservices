package com.product.product.Service;



import com.product.product.DTO.ProductRequest;
import com.product.product.DTO.ProductResponse;
import com.product.product.Model.Product;
import com.product.product.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product = new Product();

        updateProductFromRequest(product, productRequest);

        Product savedProduct =  productRepository.save(product);

        return mapToProductResponse(savedProduct);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {

        ProductResponse response = new ProductResponse();

        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setActive(savedProduct.isActive());
        response.setStockQuantity(savedProduct.getStockQuantity());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setPrice(savedProduct.getPrice());

        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        product.setPrice(productRequest.getPrice());

    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

      return productRepository.findById(id)
                .map(existProduct -> {
                    updateProductFromRequest(existProduct, productRequest);
                    Product savedProduct = productRepository.save(existProduct);

                    return mapToProductResponse(savedProduct);
                });
    }

    public List<ProductResponse> getAllProduct() {

        return productRepository.findByActiveTrue()
                .stream().map(this::mapToProductResponse).collect(Collectors.toList());

    }

    public boolean deleteProduct(Long id) {

        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);

                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProduct(String keyword) {

        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
