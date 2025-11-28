package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 1. Import do SLF4J
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SerieService {

    private final SerieRepository serieRepository;
    private final GeneroService generoService;

    public Serie criar(Serie serie) {
        log.info("Iniciando cadastro de nova série: '{}'", serie.getTitulo());

        Long generoId = serie.getGenero().getId();

        log.debug("Buscando gênero ID {} para vincular à série.", generoId);
        Genero generoExistente = generoService.buscarPorId(generoId);

        serie.setGenero(generoExistente);

        if (serieRepository.existsByTitulo(serie.getTitulo())) {
            log.warn("Tentativa de cadastro duplicado. Série '{}' já existe.", serie.getTitulo());
            throw new RuntimeException("Série com o título '" + serie.getTitulo() + "' já existe.");
        }

        Serie serieSalva = serieRepository.save(serie);

        log.info("Série criada com sucesso. ID: {}", serieSalva.getId());

        return serieSalva;
    }

    public List<Serie> listarTodos() {
        log.debug("Listando todas as séries do catálogo.");
        return serieRepository.findAll();
    }

    public Serie buscarPorId(Long id) {
        log.debug("Buscando série ID: {}", id);

        return serieRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Série ID {} não encontrada.", id);
                    return new RuntimeException("Série não encontrada com id: " + id);
                });
    }

    public Serie atualizar(Long id, Serie serieAtualizada) {
        log.info("Iniciando atualização da série ID: {}", id);

        Serie serieExistente = buscarPorId(id);

        Long novoGeneroId = serieAtualizada.getGenero().getId();

        if (!serieExistente.getGenero().getId().equals(novoGeneroId)) {
            log.debug("Alteração de Gênero na série ID {}: de ID {} para ID {}",
                    id, serieExistente.getGenero().getId(), novoGeneroId);
        }

        Genero novoGenero = generoService.buscarPorId(novoGeneroId);

        serieExistente.setTitulo(serieAtualizada.getTitulo());
        serieExistente.setDescricao(serieAtualizada.getDescricao());
        serieExistente.setTemporadas(serieAtualizada.getTemporadas());
        serieExistente.setGenero(novoGenero);

        Serie salva = serieRepository.save(serieExistente);
        log.info("Série ID {} atualizada com sucesso.", id);

        return salva;
    }

    public void deletar(Long id) {
        log.info("Solicitação para deletar série ID: {}", id);

        if (!serieRepository.existsById(id)) {
            log.warn("Tentativa de deletar série inexistente ID: {}", id);
            throw new RuntimeException("Série não encontrada com id: " + id);
        }

        serieRepository.deleteById(id);
        log.info("Série ID {} deletada com sucesso.", id);
    }
}