package br.com.fiap.ms_relatos.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class QuadraClientFallbackFactory implements FallbackFactory<QuadraClient> {

    @Override
    public QuadraClient create(Throwable cause) {
        return new QuadraClientFallback(cause);
    }
}
