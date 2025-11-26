package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    /**
     * Cria um novo gênero no banco de dados.
     */
    public Genero criar(Genero genero) {
        // A validação de 'TitulosBloqueados' deve ser tratada pelo @Valid na Controller
        // Se a entidade tiver validações de unicidade, podemos checar aqui:
        if (generoRepository.existsByNome(genero.getNome())) {
            throw new RuntimeException("Gênero com o nome '" + genero.getNome() + "' já existe.");
        }
        return generoRepository.save(genero);
    }

    /**
     * Retorna todos os gêneros.
     */
    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }

    /**
     * Busca um gênero pelo ID.
     */
    public Genero buscarPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com id: " + id));
    }

    /**
     * Atualiza um gênero existente.
     */
    public Genero atualizar(Long id, Genero generoAtualizado) {
        Genero generoExistente = buscarPorId(id);

        // Atualiza apenas os campos permitidos
        generoExistente.setNome(generoAtualizado.getNome());
        generoExistente.setDescricao(generoAtualizado.getDescricao());

        // A validação de unicidade (se o nome mudou) também deve ser considerada aqui.

        return generoRepository.save(generoExistente);
    }

    /**
     * Deleta um gênero pelo ID.
     */
    public void deletar(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new RuntimeException("Gênero não encontrado com id: " + id);
        }
        generoRepository.deleteById(id);
    }
}