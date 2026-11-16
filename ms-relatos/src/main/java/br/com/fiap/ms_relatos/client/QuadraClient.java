package br.com.fiap.ms_relatos.client;

import br.com.fiap.ms_relatos.dto.QuadraResponse;
import br.com.fiap.ms_relatos.dto.StatusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "ms-quadras", fallbackFactory = QuadraClientFallbackFactory.class)
public interface QuadraClient {

    @GetMapping("/api/v1/quadras/{id}")
    QuadraResponse buscarPorId(@PathVariable("id") Long id);

    @PatchMapping("/api/v1/quadras/{id}/status")
    QuadraResponse atualizarStatus(@PathVariable("id") Long id, @RequestBody StatusRequest request);
}
