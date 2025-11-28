package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@RequiredArgsConstructor
public class GeneroController {


    private static final Logger LOGGER = LoggerFactory.getLogger(GeneroController.class);

    private final GeneroService generoService;


    @PostMapping
    public ResponseEntity<Genero> criar(@Valid @RequestBody Genero genero) {

        LOGGER.info("Recebida requisição POST para criar um novo gênero.");

        LOGGER.debug("Dados de Gênero recebidos: {}", genero);


        Genero novoGenero = generoService.criar(genero);

        LOGGER.info("Gênero criado com sucesso. ID: {}", novoGenero.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGenero);
    }


    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {

        LOGGER.info("Recebida requisição GET para listar todos os gêneros.");

        List<Genero> generos = generoService.listarTodos();


        LOGGER.debug("Encontrados {} gêneros.", generos.size());

        LOGGER.info("Listagem de gêneros finalizada.");
        return ResponseEntity.ok(generos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar gênero pelo ID: {}", id);

        Genero genero = generoService.buscarPorId(id);


        LOGGER.info("Busca do gênero ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(genero);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Genero> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody Genero generoAtualizado) {

        LOGGER.info("Recebida requisição PUT para atualizar gênero ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, generoAtualizado);

        Genero genero = generoService.atualizar(id, generoAtualizado);


        LOGGER.info("Gênero ID: {} atualizado com sucesso.", id);
        return ResponseEntity.ok(genero);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        LOGGER.warn("Recebida requisição DELETE para deletar o gênero ID: {}", id);

        generoService.deletar(id);


        LOGGER.info("Gênero ID: {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}