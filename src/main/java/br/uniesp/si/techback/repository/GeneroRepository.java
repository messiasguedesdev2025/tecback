package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    // Método para verificar a unicidade do nome, útil para validação customizada
    boolean existsByNome(String nome);
}
