package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Útil para login e validação de unicidade
    boolean existsByEmail(String email);

    // Buscar por email (para autenticação)
    Usuario findByEmail(String email);
}