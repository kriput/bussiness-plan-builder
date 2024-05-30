package com.taltech.app.finance.service;

import com.taltech.app.finance.domain.FinancialForecast;
import com.taltech.app.finance.domain.Product;
import com.taltech.app.finance.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final FinancialForecastService financialForecastService;

    @Transactional
    public Product saveProduct(Product product) {
        FinancialForecast forecast = financialForecastService.findForecastById(
            product.getFinancialForecastId());
        product.setFinancialForecast(forecast);
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByForecast(long id) {
        return productRepository.findAllByFinancialForecast_Id(id);
    }

    public void deleteProductById(Long productId) {
        productRepository.deleteById(productId);
    }
}
