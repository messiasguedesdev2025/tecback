package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.service.FilmeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
@RequiredArgsConstructor
public class FilmeController {

    private final FilmeService filmeService;

    // POST: Cria um novo filme
    @PostMapping
    public ResponseEntity<Filme> criar(@Valid @RequestBody Filme filme) {
        // Espera um JSON com o corpo do Filme, incluindo o objeto Genero
        // com o ID preenchido (ex: {"titulo": "...", "genero": {"id": 1}})
        Filme novoFilme = filmeService.criar(filme);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFilme);
    }

    // GET: Lista todos os filmes
    @GetMapping
    public ResponseEntity<List<Filme>> listarTodos() {
        return ResponseEntity.ok(filmeService.listarTodos());
    }

    // GET: Busca um filme pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        Filme filme = filmeService.buscarPorId(id);
        return ResponseEntity.ok(filme);
    }

    // PUT: Atualiza um filme
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Filme filmeAtualizado) {
        Filme filme = filmeService.atualizar(id, filmeAtualizado);
        return ResponseEntity.ok(filme);
    }

    // DELETE: Deleta um filme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        filmeService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}