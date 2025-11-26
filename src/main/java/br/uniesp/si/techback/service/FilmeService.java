package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final GeneroService generoService; // Injetando o serviço de Gênero

    // Você pode criar uma exceção customizada para esses casos,
    // mas por enquanto, usaremos RuntimeException.

    /**
     * Cria um novo filme no banco de dados.
     */
    public Filme criar(Filme filme) {
        // 1. Validação de Dependência: Garante que o Gênero exista
        Long generoId = filme.getGenero().getId();
        Genero generoExistente = generoService.buscarPorId(generoId);

        // Atribui o objeto Genero gerenciado pelo JPA ao Filme
        filme.setGenero(generoExistente);

        // 2. Validação de Unicidade: Garante que o título não esteja duplicado
        if (filmeRepository.existsByTitulo(filme.getTitulo())) {
            throw new RuntimeException("Filme com o título '" + filme.getTitulo() + "' já existe.");
        }

        return filmeRepository.save(filme);
    }

    /**
     * Retorna todos os filmes.
     */
    public List<Filme> listarTodos() {
        return filmeRepository.findAll();
    }

    /**
     * Busca um filme pelo ID.
     */
    public Filme buscarPorId(Long id) {
        return filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com id: " + id));
    }

    /**
     * Atualiza um filme existente.
     */
    public Filme atualizar(Long id, Filme filmeAtualizado) {
        Filme filmeExistente = buscarPorId(id);

        // 1. Validação de Dependência: Garante que o novo Gênero exista
        Long novoGeneroId = filmeAtualizado.getGenero().getId();
        Genero novoGenero = generoService.buscarPorId(novoGeneroId);

        // 2. Atualiza os campos
        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setDescricao(filmeAtualizado.getDescricao());
        filmeExistente.setDuracaoMinutos(filmeAtualizado.getDuracaoMinutos());
        filmeExistente.setGenero(novoGenero); // Atribui o novo gênero

        // Idealmente, adicionar validação de unicidade de título aqui se o título mudou

        return filmeRepository.save(filmeExistente);
    }

    /**
     * Deleta um filme pelo ID.
     */
    public void deletar(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado com id: " + id);
        }
        filmeRepository.deleteById(id);
    }
}