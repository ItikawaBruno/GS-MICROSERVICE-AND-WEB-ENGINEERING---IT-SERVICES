package br.com.fiap.msquadras.controller;

import br.com.fiap.msquadras.dto.QuadraRequest;
import br.com.fiap.msquadras.dto.QuadraResponse;
import br.com.fiap.msquadras.dto.StatusRequest;
import br.com.fiap.msquadras.service.QuadraService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/quadras")
public class QuadraController {

    private final QuadraService quadraService;

    public QuadraController(QuadraService quadraService) {
        this.quadraService = quadraService;
    }

    @GetMapping
    public ResponseEntity<List<QuadraResponse>> listar() {
        return ResponseEntity.ok(quadraService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuadraResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(quadraService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<QuadraResponse> criar(@Valid @RequestBody QuadraRequest request) {
        QuadraResponse response = quadraService.criar(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuadraResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody QuadraRequest request) {
        return ResponseEntity.ok(quadraService.atualizar(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<QuadraResponse> atualizarStatus(@PathVariable Long id,
                                                          @Valid @RequestBody StatusRequest request) {
        return ResponseEntity.ok(quadraService.atualizarStatus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        quadraService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
