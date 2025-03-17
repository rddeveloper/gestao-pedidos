package br.com.foursales.ordermanagement.repository.jpa;


import br.com.foursales.ordermanagement.model.order.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}