package com.order_pay_service.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.order_pay_service.modelsAcopl.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "boletas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroSerie;

    private LocalDateTime fechaEmision;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Customer customer;

    @OneToMany(mappedBy = "boleta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleBoleta> detalles;
}
