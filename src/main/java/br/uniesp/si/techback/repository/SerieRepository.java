package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Long> {
    // Metodo verificar se uma série com um certo título já existe
    boolean existsByTitulo(String titulo);
}
