package br.com.foursales.ordermanagement.repository.elasticsearch;


import br.com.foursales.ordermanagement.model.order.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductElasticsearchRepository extends ElasticsearchRepository<Product, Long> {
    List<Product> findByNameContainingAndStockGreaterThan(String name, Integer stock);

    List<Product> findByCategoryAndStockGreaterThan(String category, Integer stock);

    List<Product> findByPriceBetweenAndStockGreaterThan(BigDecimal minPrice, BigDecimal maxPrice, Integer stock);

    List<Product> findByNameContainingAndCategoryAndPriceBetweenAndStockGreaterThan(
            String name, String category, BigDecimal minPrice, BigDecimal maxPrice, Integer stock);
}