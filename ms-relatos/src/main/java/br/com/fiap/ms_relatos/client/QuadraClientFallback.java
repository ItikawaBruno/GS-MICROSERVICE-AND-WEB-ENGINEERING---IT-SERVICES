package br.com.fiap.ms_relatos.client;

import br.com.fiap.ms_relatos.dto.QuadraResponse;
import br.com.fiap.ms_relatos.dto.StatusRequest;
import br.com.fiap.ms_relatos.exceptions.ResourceNotFoundException;
import feign.FeignException;

public class QuadraClientFallback implements QuadraClient {

    public static final String STATUS_INDISPONIVEL = "INDISPONIVEL";

    private final Throwable causa;

    public QuadraClientFallback(Throwable causa) {
        this.causa = causa;
    }

    @Override
    public QuadraResponse buscarPorId(Long id) {
        if (quadraNaoEncontrada()) {
            throw new ResourceNotFoundException("Quadra não encontrada para o id " + id);
        }

        return new QuadraResponse(id, "Serviço de quadras indisponível", STATUS_INDISPONIVEL);
    }

    @Override
    public QuadraResponse atualizarStatus(Long id, StatusRequest request) {
        return new QuadraResponse(id, "Serviço de quadras indisponível", STATUS_INDISPONIVEL);
    }

    private boolean quadraNaoEncontrada() {
        return causa instanceof FeignException feignException
                && feignException.status() == 404;
    }
}
