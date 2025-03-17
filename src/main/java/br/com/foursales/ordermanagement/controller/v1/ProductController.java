package br.com.foursales.ordermanagement.controller.v1;


import br.com.foursales.ordermanagement.model.dto.order.request.ProductRequestDTO;
import br.com.foursales.ordermanagement.model.dto.order.response.ProductResponseDTO;
import br.com.foursales.ordermanagement.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Listar os produtos por filtro", description = "Retorna uma lista produtos cadastrados por filtro.")
    @PreAuthorize("hasAuthority('LISTAR_PRODUTO')")
    @GetMapping("/filters")
    public List<ProductResponseDTO> listAllByFilter(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        return productService.listAllByFilter(name, category, minPrice, maxPrice);
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto específico.")
    @PreAuthorize("hasAuthority('LISTAR_PRODUTO')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Cadastrar novo produto", description = "Cria um novo produto no sistema.")
    @PreAuthorize("hasAuthority('CADASTRAR_PRODUTO')")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@Valid  @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(productRequestDTO));
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente.")
    @PreAuthorize("hasAuthority('EDITAR_PRODUTO')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@Valid @PathVariable Long id, @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.update(id, productRequestDTO));
    }

    @Operation(summary = "Excluir produto", description = "Remove um produto do sistema.")
    @PreAuthorize("hasAuthority('DELETAR_PRODUTO')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}