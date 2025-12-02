package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {


    @Query("SELECT e FROM Endereco e WHERE e.cidade IN :cidades")
    List<Endereco> findByCidades(@Param("cidades") List<String> cidades);


    boolean existsByCep(String cep);
}
