package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.service.SerieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class SerieController {


    private static final Logger LOGGER = LoggerFactory.getLogger(SerieController.class);

    private final SerieService serieService;


    @PostMapping
    public ResponseEntity<Serie> criar(@Valid @RequestBody Serie serie) {

        LOGGER.info("Recebida requisição POST para criar uma nova série.");

        LOGGER.debug("Dados de Série recebidos: {}", serie);


        Serie novaSerie = serieService.criar(serie);

        LOGGER.info("Série criada com sucesso. ID: {}", novaSerie.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSerie);
    }


    @GetMapping
    public ResponseEntity<List<Serie>> listarTodos() {

        LOGGER.info("Recebida requisição GET para listar todas as séries.");

        List<Serie> series = serieService.listarTodos();


        LOGGER.debug("Encontradas {} séries.", series.size());


        LOGGER.info("Listagem de séries finalizada.");
        return ResponseEntity.ok(series);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Serie> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar série pelo ID: {}", id);

        Serie serie = serieService.buscarPorId(id);


        LOGGER.info("Busca da série ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(serie);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Serie> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Serie serieAtualizada) {

        LOGGER.info("Recebida requisição PUT para atualizar série ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, serieAtualizada);

        Serie serie = serieService.atualizar(id, serieAtualizada);


        LOGGER.info("Série ID: {} atualizada com sucesso.", id);
        return ResponseEntity.ok(serie);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        LOGGER.warn("Recebida requisição DELETE para deletar a série ID: {}", id);

        serieService.deletar(id);


        LOGGER.info("Série ID: {} deletada com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}