package br.com.fiap.msquadras.service;

import br.com.fiap.msquadras.dto.QuadraRequest;
import br.com.fiap.msquadras.dto.QuadraResponse;
import br.com.fiap.msquadras.dto.StatusRequest;
import br.com.fiap.msquadras.entity.NivelRisco;
import br.com.fiap.msquadras.entity.Quadra;
import br.com.fiap.msquadras.entity.StatusAlagamento;
import br.com.fiap.msquadras.exceptions.DatabaseException;
import br.com.fiap.msquadras.exceptions.ResourceNotFoundException;
import br.com.fiap.msquadras.repository.QuadraRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuadraService {

    private final QuadraRepository quadraRepository;

    public QuadraService(QuadraRepository quadraRepository) {
        this.quadraRepository = quadraRepository;
    }

    @Transactional(readOnly = true)
    public List<QuadraResponse> listar() {
        return quadraRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public QuadraResponse buscarPorId(Long id) {
        return toResponse(buscarEntidade(id));
    }

    @Transactional
    public QuadraResponse criar(QuadraRequest request) {
        Quadra quadra = new Quadra();
        copiarDadosDoRequest(request, quadra);
        quadra.setStatusAlagamento(StatusAlagamento.NORMAL);
        quadra.setDataAtualizacao(LocalDateTime.now());

        return toResponse(quadraRepository.save(quadra));
    }

    @Transactional
    public QuadraResponse atualizar(Long id, QuadraRequest request) {
        Quadra quadra = buscarEntidade(id);
        copiarDadosDoRequest(request, quadra);
        quadra.setDataAtualizacao(LocalDateTime.now());

        return toResponse(quadraRepository.save(quadra));
    }

    @Transactional
    public QuadraResponse atualizarStatus(Long id, StatusRequest request) {
        Quadra quadra = buscarEntidade(id);
        quadra.setStatusAlagamento(request.getStatus());
        quadra.setDataAtualizacao(LocalDateTime.now());

        return toResponse(quadraRepository.save(quadra));
    }

    @Transactional
    public void excluir(Long id) {
        Quadra quadra = buscarEntidade(id);
        try {
            quadraRepository.delete(quadra);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir a quadra id " + id + ": existem dados vinculados.");
        }
    }


    private Quadra buscarEntidade(Long id) {
        return quadraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quadra não encontrada para o id " + id));
    }

    private NivelRisco calcularNivelRisco(Integer notaRisco) {
        if (notaRisco < 40) {
            return NivelRisco.BAIXO;
        }
        if (notaRisco < 70) {
            return NivelRisco.MEDIO;
        }
        return NivelRisco.ALTO;
    }

    private void copiarDadosDoRequest(QuadraRequest request, Quadra quadra) {
        quadra.setLogradouro(request.getLogradouro());
        quadra.setBairro(request.getBairro());
        quadra.setLatitude(request.getLatitude());
        quadra.setLongitude(request.getLongitude());
        quadra.setNotaRisco(request.getNotaRisco());
        quadra.setNivelRisco(calcularNivelRisco(request.getNotaRisco()));
    }

    private QuadraResponse toResponse(Quadra quadra) {
        return new QuadraResponse(
                quadra.getId(),
                quadra.getLogradouro(),
                quadra.getBairro(),
                quadra.getLatitude(),
                quadra.getLongitude(),
                quadra.getNotaRisco(),
                quadra.getNivelRisco(),
                quadra.getStatusAlagamento(),
                quadra.getDataAtualizacao()
        );
    }
}
