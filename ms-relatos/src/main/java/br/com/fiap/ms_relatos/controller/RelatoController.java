package br.com.fiap.ms_relatos.controller;

import br.com.fiap.ms_relatos.dto.RelatoRequest;
import br.com.fiap.ms_relatos.dto.RelatoResponse;
import br.com.fiap.ms_relatos.service.RelatoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/relatos")
public class RelatoController {

    private final RelatoService relatoService;

    public RelatoController(RelatoService relatoService) {
        this.relatoService = relatoService;
    }

    @GetMapping
    public ResponseEntity<List<RelatoResponse>> listar() {
        return ResponseEntity.ok(relatoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelatoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(relatoService.buscarPorId(id));
    }

    @GetMapping("/quadra/{quadraId}")
    public ResponseEntity<List<RelatoResponse>> listarPorQuadra(@PathVariable Long quadraId) {
        return ResponseEntity.ok(relatoService.listarPorQuadra(quadraId));
    }

    @PostMapping
    public ResponseEntity<RelatoResponse> criar(@Valid @RequestBody RelatoRequest request) {
        RelatoResponse response = relatoService.criar(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelatoResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody RelatoRequest request) {
        return ResponseEntity.ok(relatoService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        relatoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
