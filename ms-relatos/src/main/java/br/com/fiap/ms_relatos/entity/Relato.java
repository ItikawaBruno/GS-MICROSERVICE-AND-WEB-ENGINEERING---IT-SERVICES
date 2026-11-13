package br.com.fiap.ms_relatos.entity;

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

@Entity
@Table(name = "relatos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quadra_id", nullable = false)
    private Long quadraId;

    @Column(name = "nome_usuario")
    private String nomeUsuario;

    private String descricao;

    @Column(name = "foto_url")
    private String fotoUrl;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    private StatusRelato status;

}