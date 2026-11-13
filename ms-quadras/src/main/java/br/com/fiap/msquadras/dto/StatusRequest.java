package br.com.fiap.msquadras.dto;

import br.com.fiap.msquadras.entity.StatusAlagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusRequest {

    @NotNull(message = "O status de alagamento é obrigatório (NORMAL, ALERTA ou ALAGADO)")
    private StatusAlagamento status;
}
