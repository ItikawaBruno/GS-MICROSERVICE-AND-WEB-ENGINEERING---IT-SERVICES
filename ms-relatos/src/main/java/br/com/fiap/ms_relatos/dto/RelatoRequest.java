package br.com.fiap.ms_relatos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatoRequest {

    @NotNull(message = "O id da quadra é obrigatório")
    @Min(value = 1, message = "O id da quadra deve ser maior que zero")
    private Long quadraId;

    @NotBlank(message = "O nome do usuário é obrigatório")
    private String nomeUsuario;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private String fotoUrl;
}
