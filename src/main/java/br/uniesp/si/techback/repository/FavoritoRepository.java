package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // 1. Útil para listar todos os favoritos de um usuário específico
    List<Favorito> findByUsuarioId(Long usuarioId);

    // 2. Essencial para verificar a unicidade (se o usuário já favoritou este item)
    // Busca um favorito pelo ID do Usuário, ID do Item (Filme/Série) e Tipo do Item.
    boolean existsByUsuarioIdAndItemIdAndItemType(
            Long usuarioId,
            Long itemId,
            String itemType
    );
}