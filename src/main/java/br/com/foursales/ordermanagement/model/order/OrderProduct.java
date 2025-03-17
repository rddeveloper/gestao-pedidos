package br.com.foursales.ordermanagement.model.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "pedido_produto")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Product product;

    @Column(name = "quantidade")
    private Integer quantity;

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime createDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

}