package br.com.fiap.msquadras.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuadraRequest {

    @NotBlank(message = "O logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "O bairro é obrigatório")
    private String bairro;

    @NotNull(message = "A latitude é obrigatória")
    @Min(value = -90, message = "A latitude deve estar entre -90 e 90")
    @Max(value = 90, message = "A latitude deve estar entre -90 e 90")
    private Double latitude;

    @NotNull(message = "A longitude é obrigatória")
    @Min(value = -180, message = "A longitude deve estar entre -180 e 180")
    @Max(value = 180, message = "A longitude deve estar entre -180 e 180")
    private Double longitude;

    @NotNull(message = "A nota de risco é obrigatória")
    @Min(value = 0, message = "A nota de risco deve estar entre 0 e 100")
    @Max(value = 100, message = "A nota de risco deve estar entre 0 e 100")
    private Integer notaRisco;
}
