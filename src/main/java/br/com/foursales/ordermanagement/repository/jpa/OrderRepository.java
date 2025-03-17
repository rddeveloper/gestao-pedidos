package br.com.foursales.ordermanagement.repository.jpa;


import br.com.foursales.ordermanagement.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT u.id, u.username, SUM(o.valor_total) AS total_spent " +
            "FROM pedido o " +
            "JOIN usuario u ON o.usuario_id = u.id " +
            "WHERE o.status = 'PAGO' " +
            "GROUP BY u.id, u.username " +
            "ORDER BY total_spent DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5UsersByTotalSpent();

    @Query(value = "SELECT u.id, u.username, AVG(o.valor_total) AS average_ticket " +
            "FROM pedido o " +
            "JOIN usuario u ON o.usuario_id = u.id " +
            "WHERE o.status = 'PAGO' " +
            "GROUP BY u.id, u.username", nativeQuery = true)
    List<Object[]> findAverageTicketPerUser();

    @Query(value = "SELECT SUM(valor_total) FROM pedido " +
            "WHERE EXTRACT(MONTH FROM data_criacao) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM data_criacao) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND status = 'PAGO'", nativeQuery = true)
    BigDecimal findTotalRevenueForCurrentMonth();
}