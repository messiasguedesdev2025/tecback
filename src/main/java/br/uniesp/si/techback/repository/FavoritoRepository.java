package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    List<Favorito> findByUsuarioId(Long usuarioId);

    // Metodo para verificar a unicidade (se o usuário já favoritou este item)
    boolean existsByUsuarioIdAndItemIdAndItemType(
            Long usuarioId,
            Long itemId,
            String itemType
    );

    // QUERY PARA FILMES FAVORITADOS DE UM USUÁRIO ESPECÍFICO
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId AND f.itemType = 'FILME'")
    List<Favorito> findFilmeFavoritosByUsuario(@Param("usuarioId") Long usuarioId);

    // QUERY PARA SÉRIES FAVORITADAS DE UM USUÁRIO ESPECÍFICO
    @Query("SELECT f FROM Favorito f WHERE f.usuario.id = :usuarioId AND f.itemType = 'SERIE'")
    List<Favorito> findSerieFavoritosByUsuario(@Param("usuarioId") Long usuarioId);
}