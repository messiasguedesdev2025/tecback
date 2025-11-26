package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.service.FavoritoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
public class FavoritoController {

    private final FavoritoService favoritoService;

    // POST: Adiciona um item (Filme OU Série) aos favoritos
    @PostMapping
    public ResponseEntity<Favorito> criar(@Valid @RequestBody Favorito favorito) {
        // Espera um JSON com um dos seguintes formatos:
        // 1) {"usuario": {"id": 1}, "filme": {"id": 5}}
        // 2) {"usuario": {"id": 1}, "serie": {"id": 10}}
        Favorito novoFavorito = favoritoService.criar(favorito);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFavorito);
    }

    // GET: Lista todos os favoritos (Pode ser refinado para listar por usuário)
    @GetMapping
    public ResponseEntity<List<Favorito>> listarTodos() {
        return ResponseEntity.ok(favoritoService.listarTodos());
    }

    // GET: Busca um favorito pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Favorito> buscarPorId(@PathVariable Long id) {
        Favorito favorito = favoritoService.buscarPorId(id);
        return ResponseEntity.ok(favorito);
    }

    // GET: Lista favoritos de um usuário específico (Exemplo de endpoint mais útil)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Favorito> favoritos = favoritoService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(favoritos);
    }


    // DELETE: Remove um item dos favoritos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        favoritoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}