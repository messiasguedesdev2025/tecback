package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.service.SerieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
@RequiredArgsConstructor
public class SerieController {

    private final SerieService serieService;

    // POST: Cria uma nova série
    @PostMapping
    public ResponseEntity<Serie> criar(@Valid @RequestBody Serie serie) {
        // Espera um JSON com o ID do Gênero aninhado: {"titulo": "...", "genero": {"id": 1}}
        Serie novaSerie = serieService.criar(serie);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSerie);
    }

    // GET: Lista todas as séries
    @GetMapping
    public ResponseEntity<List<Serie>> listarTodos() {
        return ResponseEntity.ok(serieService.listarTodos());
    }

    // GET: Busca uma série pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Serie> buscarPorId(@PathVariable Long id) {
        Serie serie = serieService.buscarPorId(id);
        return ResponseEntity.ok(serie);
    }

    // PUT: Atualiza uma série
    @PutMapping("/{id}")
    public ResponseEntity<Serie> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Serie serieAtualizada) {
        Serie serie = serieService.atualizar(id, serieAtualizada);
        return ResponseEntity.ok(serie);
    }

    // DELETE: Deleta uma série
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        serieService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}