package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.FilmeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmeService {

    private final FilmeRepository filmeRepository;
    private final GeneroService generoService;

    public Filme criar(Filme filme) {
        log.info("Iniciando criação de novo filme com título: '{}'", filme.getTitulo());

        Long generoId = filme.getGenero().getId();

        log.debug("Buscando gênero com ID {} para vincular ao filme", generoId);
        Genero generoExistente = generoService.buscarPorId(generoId);

        filme.setGenero(generoExistente);

        if (filmeRepository.existsByTitulo(filme.getTitulo())) {
            log.warn("Tentativa de cadastro duplicado. Filme '{}' já existe.", filme.getTitulo());
            throw new RuntimeException("Filme com o título '" + filme.getTitulo() + "' já existe.");
        }

        Filme filmeSalvo = filmeRepository.save(filme);

        log.info("Filme criado com sucesso. ID: {}", filmeSalvo.getId());

        return filmeSalvo;
    }

    public List<Filme> listarTodos() {
        log.debug("Listando todos os filmes...");

        List<Filme> filmes = filmeRepository.findAll();

        log.debug("Total de filmes encontrados: {}", filmes.size());

        return filmes;
    }

    public Filme buscarPorId(Long id) {
        log.debug("Buscando filme pelo ID: {}", id);

        return filmeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Filme não encontrado no banco de dados. ID: {}", id);
                    return new RuntimeException("Filme não encontrado com id: " + id);
                });
    }

    public Filme atualizar(Long id, Filme filmeAtualizado) {
        log.info("Iniciando atualização do filme ID: {}", id);

        Filme filmeExistente = buscarPorId(id);

        Long novoGeneroId = filmeAtualizado.getGenero().getId();

        log.debug("Atualizando gênero do filme ID {} para o gênero ID {}", id, novoGeneroId);
        Genero novoGenero = generoService.buscarPorId(novoGeneroId);

        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setDescricao(filmeAtualizado.getDescricao());
        filmeExistente.setDuracaoMinutos(filmeAtualizado.getDuracaoMinutos());
        filmeExistente.setGenero(novoGenero);

        Filme salvo = filmeRepository.save(filmeExistente);
        log.info("Filme ID {} atualizado com sucesso.", salvo.getId());

        return salvo;
    }

    public void deletar(Long id) {
        log.info("Solicitação para deletar filme ID: {}", id);

        if (!filmeRepository.existsById(id)) {
            log.warn("Tentativa de deletar filme inexistente. ID: {}", id);
            throw new RuntimeException("Filme não encontrado com id: " + id);
        }

        filmeRepository.deleteById(id);
        log.info("Filme ID {} deletado com sucesso.", id);
    }
}