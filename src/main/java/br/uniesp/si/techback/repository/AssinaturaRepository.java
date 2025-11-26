package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    // Método útil para buscar assinaturas por usuário e status
    List<Assinatura> findByUsuarioIdAndStatus(Long usuarioId, Assinatura.StatusAssinatura status);
}