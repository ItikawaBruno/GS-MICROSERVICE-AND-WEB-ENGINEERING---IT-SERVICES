package br.com.fiap.msquadras.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "quadras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quadra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private String bairro;

    private Double latitude;

    private Double longitude;

    @Column(name = "nota_risco")
    private Integer notaRisco;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_risco")
    private NivelRisco nivelRisco;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_alagamento")
    private StatusAlagamento statusAlagamento;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

}
