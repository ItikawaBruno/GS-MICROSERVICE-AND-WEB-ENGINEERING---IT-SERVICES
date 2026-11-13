package br.com.fiap.ms_relatos.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

// Entrega ao fallback a exceção que causou a falha na chamada ao ms-quadras.
@Component
public class QuadraClientFallbackFactory implements FallbackFactory<QuadraClient> {

    @Override
    public QuadraClient create(Throwable cause) {
        return new QuadraClientFallback(cause);
    }
}
