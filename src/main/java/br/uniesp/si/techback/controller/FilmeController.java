package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/filmes")
@RequiredArgsConstructor
public class FilmeController {


    private static final Logger LOGGER = LoggerFactory.getLogger(FilmeController.class);

    private final FilmeService filmeService;


    @PostMapping
    public ResponseEntity<Filme> criar(@Valid @RequestBody Filme filme) {

        LOGGER.info("Recebida requisição POST para criar um novo filme.");

        LOGGER.debug("Dados de Filme recebidos: {}", filme);

        Filme novoFilme = filmeService.criar(filme);

        LOGGER.info("Filme criado com sucesso. ID: {}", novoFilme.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFilme);
    }


    @GetMapping
    public ResponseEntity<List<Filme>> listarTodos() {

        LOGGER.info("Recebida requisição GET para listar todos os filmes.");

        List<Filme> filmes = filmeService.listarTodos();


        LOGGER.debug("Encontrados {} filmes.", filmes.size());


        LOGGER.info("Listagem de filmes finalizada.");
        return ResponseEntity.ok(filmes);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar filme pelo ID: {}", id);

        Filme filme = filmeService.buscarPorId(id);

        LOGGER.info("Busca do filme ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(filme);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Filme filmeAtualizado) {

        LOGGER.info("Recebida requisição PUT para atualizar filme ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, filmeAtualizado);

        Filme filme = filmeService.atualizar(id, filmeAtualizado);


        LOGGER.info("Filme ID: {} atualizado com sucesso.", id);
        return ResponseEntity.ok(filme);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        LOGGER.warn("Recebida requisição DELETE para deletar o filme ID: {}", id);

        filmeService.deletar(id);


        LOGGER.info("Filme ID: {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}