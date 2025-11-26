package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Útil para listar todos os favoritos de um usuário
    List<Favorito> findByUsuarioId(Long usuarioId);

    // Útil para evitar duplicidade: verificar se um usuário já favoritou um filme/série
    boolean existsByUsuarioIdAndFilmeId(Long usuarioId, Long filmeId);
    boolean existsByUsuarioIdAndSerieId(Long usuarioId, Long serieId);
}