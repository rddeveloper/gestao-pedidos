package br.com.foursales.ordermanagement.service.impl;


import br.com.foursales.ordermanagement.exception.BusinessException;
import br.com.foursales.ordermanagement.mapper.order.ProductMapper;
import br.com.foursales.ordermanagement.model.dto.order.request.ProductRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.ProductResponseDTO;
import br.com.foursales.ordermanagement.model.order.Product;
import br.com.foursales.ordermanagement.repository.elasticsearch.ProductElasticsearchRepository;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import br.com.foursales.ordermanagement.repository.jpa.ProductRepository;
import br.com.foursales.ordermanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT_NOT_FOUND = "Produto não encontrado";
    public static final String STOCK_INSUFICIENT = "Estoque insuficiente para o produto: ";

    private final ProductRepository productRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    private final ProductElasticsearchRepository productElasticsearchRepository;

    private final ProductMapper productMapper;

    @Override
    public List<ProductResponseDTO> listAllByFilter(String name,
                                                    String category,
                                                    BigDecimal minPrice,
                                                    BigDecimal maxPrice) {

        log.info("Consultando produtos com filtros: name={}, category={}, minPrice={}, maxPrice={}",
                name, category, minPrice, maxPrice);

        Criteria criteria = new Criteria();

        if (name != null && !name.isEmpty()) {
            criteria = criteria.and("name.keyword").is(name);
        }
        if (category != null && !category.isEmpty()) {
            criteria = criteria.and("category.keyword").is(category);
        }
        if (minPrice != null && maxPrice != null) {
            criteria = criteria.and("price").between(minPrice.doubleValue(), maxPrice.doubleValue());
        }

        criteria = criteria.and("stock").greaterThan(0);

        Query searchQuery = new CriteriaQuery(criteria);

        List<Product> products = elasticsearchOperations.search(searchQuery, Product.class)
                .stream()
                .map(SearchHit::getContent)
                .toList();

        // Converte os produtos para DTOs e retorna
        return products.stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        log.info("Consultando produto por id: {}", id);

        Product product = (getProduct(id));

        log.info("Produto consultado com sucesso");

        return productMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        log.info("Cadastrando produto payload: {}", productRequestDTO);

        Product product = productMapper.toEntity(productRequestDTO);

        Product savedProduct = productRepository.save(product);

        productElasticsearchRepository.save(savedProduct);

        log.info("Produto cadastrado com sucesso");

        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO productRequestDTO) {

        log.info("Atualizando produto id: {}, payload: {}", id, productRequestDTO);

        Product product = getProduct(id);

        productMapper.updateEntityFromDTO(productRequestDTO, product);

        Product updatedProduct = productRepository.save(product);

        productElasticsearchRepository.save(updatedProduct);

        log.info("Produto atualziado com sucesso");

        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public Product getProduct(Long id) {
        log.info("Consultando produto na base de dados");

        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException(PRODUCT_NOT_FOUND));

    }

    @Override
    public void delete(Long id) {
        log.info("Deletando produto  id: {}", id);

        Product product = getProduct(id);

        productRepository.deleteById(product.getId());

        log.info("Produto deletado com sucesso");
    }

    @Override
    @Transactional
    public void updateStock(Long productId, Integer quantity) {

        log.info("Atualizando estoque produto {}, quantidade: {}", productId, quantity);

        Product product = getProduct(productId);

        if (product.getStock() < quantity) {
            throw new BusinessException(STOCK_INSUFICIENT + product.getName());
        }

        Integer calculateStock = product.getStock() - quantity;

        log.info("Estoque antigo: {}", product.getStock());
        log.info("Novo estoque: {}", calculateStock);

        product.setStock(calculateStock);

        productRepository.save(product);
        productElasticsearchRepository.save(product);

        log.info("Dados de estoque atualizados com sucesso");

    }

}