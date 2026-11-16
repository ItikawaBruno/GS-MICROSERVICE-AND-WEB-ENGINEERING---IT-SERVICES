package br.com.fiap.ms_relatos.service;

import br.com.fiap.ms_relatos.client.QuadraClient;
import br.com.fiap.ms_relatos.client.QuadraClientFallback;
import br.com.fiap.ms_relatos.dto.QuadraResponse;
import br.com.fiap.ms_relatos.dto.RelatoRequest;
import br.com.fiap.ms_relatos.dto.RelatoResponse;
import br.com.fiap.ms_relatos.dto.StatusRequest;
import br.com.fiap.ms_relatos.entity.Relato;
import br.com.fiap.ms_relatos.entity.StatusRelato;
import br.com.fiap.ms_relatos.exceptions.DatabaseException;
import br.com.fiap.ms_relatos.exceptions.ResourceNotFoundException;
import br.com.fiap.ms_relatos.repository.RelatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelatoService {

    private static final int LIMITE_RELATOS = 3;
    private static final int JANELA_MINUTOS = 15;
    private static final String STATUS_ALAGADO = "ALAGADO";

    private static final Logger log = LoggerFactory.getLogger(RelatoService.class);

    private final RelatoRepository relatoRepository;
    private final QuadraClient quadraClient;

    public RelatoService(RelatoRepository relatoRepository, QuadraClient quadraClient) {
        this.relatoRepository = relatoRepository;
        this.quadraClient = quadraClient;
    }

    @Transactional(readOnly = true)
    public List<RelatoResponse> listar() {
        return relatoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public RelatoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<RelatoResponse> listarPorQuadra(Long quadraId) {
        return relatoRepository.findByQuadraId(quadraId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public RelatoResponse criar(RelatoRequest request) {
        QuadraResponse quadra = consultarQuadra(request.getQuadraId());

        Relato relato = new Relato();
        copiarDadosDoRequest(request, relato);
        relato.setStatus(StatusRelato.PENDENTE);
        relato.setDataHora(LocalDateTime.now());

        relato = relatoRepository.save(relato);

        avaliarAlagamento(relato.getQuadraId(), quadra);

        return toResponse(relato);
    }

    @Transactional
    public RelatoResponse atualizar(Long id, RelatoRequest request) {
        Relato relato = buscarEntidade(id);
        consultarQuadra(request.getQuadraId());

        copiarDadosDoRequest(request, relato);

        return toResponse(relatoRepository.save(relato));
    }

    @Transactional
    public void excluir(Long id) {
        Relato relato = buscarEntidade(id);
        try {
            relatoRepository.delete(relato);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir o relato id " + id + ": existem dados vinculados.");
        }
    }


    private Relato buscarEntidade(Long id) {
        return relatoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relato não encontrado para o id " + id));
    }

    private QuadraResponse consultarQuadra(Long quadraId) {
        QuadraResponse quadra = quadraClient.buscarPorId(quadraId);
        if (quadra == null) {
            throw new ResourceNotFoundException("Quadra não encontrada para o id " + quadraId);
        }
        return quadra;
    }

    private boolean quadrasIndisponivel(QuadraResponse quadra) {
        return quadra == null
                || QuadraClientFallback.STATUS_INDISPONIVEL.equals(quadra.getStatusAlagamento());
    }

    private void avaliarAlagamento(Long quadraId, QuadraResponse quadra) {
        long relatosRecentes = relatoRepository.countByQuadraIdAndDataHoraAfter(
                quadraId, LocalDateTime.now().minusMinutes(JANELA_MINUTOS));

        if (relatosRecentes < LIMITE_RELATOS) {
            return;
        }

        if (quadrasIndisponivel(quadra)) {
            log.warn("Quadra {} atingiu {} relatos, mas o ms-quadras está indisponível: relatos seguem PENDENTE",
                    quadraId, relatosRecentes);
            return;
        }

        QuadraResponse atualizada = quadraClient.atualizarStatus(quadraId, new StatusRequest(STATUS_ALAGADO));

        if (quadrasIndisponivel(atualizada)) {
            log.warn("Falha ao atualizar o status da quadra {}: relatos seguem PENDENTE", quadraId);
            return;
        }

        confirmarRelatosPendentes(quadraId);
    }

    private void confirmarRelatosPendentes(Long quadraId) {
        List<Relato> pendentes = relatoRepository.findByQuadraIdAndStatus(quadraId, StatusRelato.PENDENTE);
        pendentes.forEach(pendente -> pendente.setStatus(StatusRelato.CONFIRMADO));
        relatoRepository.saveAll(pendentes);
    }

    private void copiarDadosDoRequest(RelatoRequest request, Relato relato) {
        relato.setQuadraId(request.getQuadraId());
        relato.setNomeUsuario(request.getNomeUsuario());
        relato.setDescricao(request.getDescricao());
        relato.setFotoUrl(request.getFotoUrl());
    }

    private RelatoResponse toResponse(Relato relato) {
        return new RelatoResponse(
                relato.getId(),
                relato.getQuadraId(),
                relato.getNomeUsuario(),
                relato.getDescricao(),
                relato.getFotoUrl(),
                relato.getDataHora(),
                relato.getStatus()
        );
    }
}
