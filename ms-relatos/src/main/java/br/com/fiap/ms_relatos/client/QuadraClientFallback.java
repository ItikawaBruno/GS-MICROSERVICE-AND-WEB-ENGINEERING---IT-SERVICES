package br.com.fiap.ms_relatos.client;

import br.com.fiap.ms_relatos.dto.QuadraResponse;
import br.com.fiap.ms_relatos.dto.StatusRequest;
import br.com.fiap.ms_relatos.exceptions.ResourceNotFoundException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Fallback do Circuit Breaker (Resilience4J). Recebe a causa da falha para separar
// os dois casos: se o ms-quadras respondeu 404, a quadra realmente nao existe e o
// erro deve chegar ao cliente; se o servico esta fora do ar, devolve uma quadra
// marcada como INDISPONIVEL para que o relato seja salvo como PENDENTE sem quebrar.
public class QuadraClientFallback implements QuadraClient {

    public static final String STATUS_INDISPONIVEL = "INDISPONIVEL";

    private static final Logger log = LoggerFactory.getLogger(QuadraClientFallback.class);

    private final Throwable causa;

    public QuadraClientFallback(Throwable causa) {
        this.causa = causa;
    }

    @Override
    public QuadraResponse buscarPorId(Long id) {
        if (quadraNaoEncontrada()) {
            throw new ResourceNotFoundException("Quadra não encontrada para o id " + id);
        }

        log.warn("ms-quadras indisponível: não foi possível consultar a quadra id {}", id, causa);
        return new QuadraResponse(id, "Serviço de quadras indisponível", STATUS_INDISPONIVEL);
    }

    @Override
    public QuadraResponse atualizarStatus(Long id, StatusRequest request) {
        log.warn("ms-quadras indisponível: não foi possível atualizar o status da quadra id {} para {}",
                id, request.getStatus(), causa);
        return new QuadraResponse(id, "Serviço de quadras indisponível", STATUS_INDISPONIVEL);
    }

    private boolean quadraNaoEncontrada() {
        return causa instanceof FeignException feignException
                && feignException.status() == 404;
    }
}
