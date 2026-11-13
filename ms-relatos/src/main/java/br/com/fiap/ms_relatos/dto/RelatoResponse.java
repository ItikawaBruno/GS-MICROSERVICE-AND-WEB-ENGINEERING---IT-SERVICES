package br.com.fiap.ms_relatos.dto;

import br.com.fiap.ms_relatos.entity.StatusRelato;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoResponse {

    private Long id;
    private Long quadraId;
    private String nomeUsuario;
    private String descricao;
    private String fotoUrl;
    private LocalDateTime dataHora;
    private StatusRelato status;
}
