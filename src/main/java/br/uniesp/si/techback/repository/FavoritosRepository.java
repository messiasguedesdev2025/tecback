package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Favoritos;
import br.uniesp.si.techback.model.FavoritosId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritosRepository extends JpaRepository<Favoritos, FavoritosId> {
}
