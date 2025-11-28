package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    // Método útil para buscar assinaturas por usuário e status
    List<Assinatura> findByUsuarioIdAndStatus(Long usuarioId, Assinatura.StatusAssinatura status);
    @Query("SELECT a FROM Assinatura a " +
            "WHERE a.status = 'ATIVA' " +
            "AND a.dataFim BETWEEN CURRENT_TIMESTAMP() AND :dataAlvo")
    List<Assinatura> findActiveSubscriptionsExpiringSoon(@Param("dataAlvo") LocalDateTime dataAlvo);
}