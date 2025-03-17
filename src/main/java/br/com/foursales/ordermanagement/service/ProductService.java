package br.com.foursales.ordermanagement.service;


import br.com.foursales.ordermanagement.model.dto.order.request.ProductRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.request.ProductSearchRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.ProductResponseDTO;
import br.com.foursales.ordermanagement.model.order.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> listAllByFilter(String name,
                                             String category,
                                             BigDecimal minPrice,
                                             BigDecimal maxPrice);
    ProductResponseDTO findById(Long id);
    Product getProduct(Long id);
    ProductResponseDTO create(ProductRequestDTO productRequestDTO);
    ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO);
    void delete(Long id);

    void updateStock(Long productId, Integer quantity);
}