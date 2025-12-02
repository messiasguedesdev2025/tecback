package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    // Metodo buscar filmes pelo título
    Filme findByTitulo(String titulo);

    // Metodo verificar se um filme com um certo título já existe
    boolean existsByTitulo(String titulo);
}
