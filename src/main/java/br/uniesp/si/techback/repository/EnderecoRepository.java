package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    /**
     * Exemplo de Query Personalizada com JPQL.
     * Busca todos os endereços cuja cidade esteja contida na lista de cidades fornecida.
     *
     * @param cidades A lista de nomes de cidades a serem buscadas.
     * @return Uma lista de endereços que correspondem aos critérios.
     */
    @Query("SELECT e FROM Endereco e WHERE e.cidade IN :cidades")
    List<Endereco> findByCidades(@Param("cidades") List<String> cidades);

    /**
     * Exemplo de uma query para verificar se um CEP já existe (útil para validação).
     * @param cep O CEP a ser verificado.
     * @return true se o CEP já existir, false caso contrário.
     */
    boolean existsByCep(String cep);
}
