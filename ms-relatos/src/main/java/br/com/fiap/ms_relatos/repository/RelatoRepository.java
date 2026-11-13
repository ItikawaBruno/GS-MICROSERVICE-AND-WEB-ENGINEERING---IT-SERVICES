package br.com.fiap.ms_relatos.repository;

import br.com.fiap.ms_relatos.entity.Relato;
import br.com.fiap.ms_relatos.entity.StatusRelato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RelatoRepository extends JpaRepository<Relato, Long> {

    List<Relato> findByQuadraId(Long quadraId);

    List<Relato> findByQuadraIdAndStatus(Long quadraId, StatusRelato status);

    long countByQuadraIdAndDataHoraAfter(Long quadraId, LocalDateTime dataHora);
}
