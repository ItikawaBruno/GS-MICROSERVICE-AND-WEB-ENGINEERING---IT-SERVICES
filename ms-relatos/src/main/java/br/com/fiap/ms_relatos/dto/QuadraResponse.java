package br.com.fiap.ms_relatos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuadraResponse {

    private Long id;
    private String logradouro;
    private String statusAlagamento;
}
