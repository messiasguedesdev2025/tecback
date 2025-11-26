package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SerieService {

    private final SerieRepository serieRepository;
    private final GeneroService generoService; // Injetando o serviço de Gênero

    /**
     * Cria uma nova série no banco de dados.
     */
    public Serie criar(Serie serie) {
        // 1. Validação de Dependência: Garante que o Gênero exista
        Long generoId = serie.getGenero().getId();
        Genero generoExistente = generoService.buscarPorId(generoId);

        // Atribui o objeto Genero gerenciado pelo JPA à Série
        serie.setGenero(generoExistente);

        // 2. Validação de Unicidade: Garante que o título não esteja duplicado
        if (serieRepository.existsByTitulo(serie.getTitulo())) {
            throw new RuntimeException("Série com o título '" + serie.getTitulo() + "' já existe.");
        }

        return serieRepository.save(serie);
    }

    /**
     * Retorna todas as séries.
     */
    public List<Serie> listarTodos() {
        return serieRepository.findAll();
    }

    /**
     * Busca uma série pelo ID.
     */
    public Serie buscarPorId(Long id) {
        return serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Série não encontrada com id: " + id));
    }

    /**
     * Atualiza uma série existente.
     */
    public Serie atualizar(Long id, Serie serieAtualizada) {
        Serie serieExistente = buscarPorId(id);

        // 1. Validação de Dependência: Garante que o novo Gênero exista
        Long novoGeneroId = serieAtualizada.getGenero().getId();
        Genero novoGenero = generoService.buscarPorId(novoGeneroId);

        // 2. Atualiza os campos (Assumindo campos 'titulo', 'descricao', 'temporadas')
        serieExistente.setTitulo(serieAtualizada.getTitulo());
        serieExistente.setDescricao(serieAtualizada.getDescricao());
        serieExistente.setTemporadas(serieAtualizada.getTemporadas());
        serieExistente.setGenero(novoGenero);

        return serieRepository.save(serieExistente);
    }

    /**
     * Deleta uma série pelo ID.
     */
    public void deletar(Long id) {
        if (!serieRepository.existsById(id)) {
            throw new RuntimeException("Série não encontrada com id: " + id);
        }
        serieRepository.deleteById(id);
    }
}