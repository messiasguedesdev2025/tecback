package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    // POST: Cria um novo gênero
    @PostMapping
    public ResponseEntity<Genero> criar(@Valid @RequestBody Genero genero) {
        // O @Valid garante que a validação @TitulosBloqueados seja executada
        Genero novoGenero = generoService.criar(genero);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoGenero);
    }

    // GET: Lista todos os gêneros
    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {
        return ResponseEntity.ok(generoService.listarTodos());
    }

    // GET: Busca um gênero pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
        Genero genero = generoService.buscarPorId(id);
        return ResponseEntity.ok(genero);
    }

    // PUT: Atualiza um gênero
    @PutMapping("/{id}")
    public ResponseEntity<Genero> atualizar(@PathVariable Long id,
                                            @Valid @RequestBody Genero generoAtualizado) {
        Genero genero = generoService.atualizar(id, generoAtualizado);
        return ResponseEntity.ok(genero);
    }

    // DELETE: Deleta um gênero
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        generoService.deletar(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}