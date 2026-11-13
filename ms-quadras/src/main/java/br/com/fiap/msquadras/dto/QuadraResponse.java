package br.com.fiap.msquadras.dto;

import br.com.fiap.msquadras.entity.NivelRisco;
import br.com.fiap.msquadras.entity.StatusAlagamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuadraResponse {

    private Long id;
    private String logradouro;
    private String bairro;
    private Double latitude;
    private Double longitude;
    private Integer notaRisco;
    private NivelRisco nivelRisco;
    private StatusAlagamento statusAlagamento;
    private LocalDateTime dataAtualizacao;
}
