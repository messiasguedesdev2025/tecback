package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Favoritos;
import br.uniesp.si.techback.service.FavoritosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
public class FavoritosController {

    private final FavoritosService favoritoService;

    @GetMapping
    public List<Favoritos> listar() {
        return favoritoService.listar();
    }

    @GetMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<Favoritos> buscarPorId(@PathVariable Long usuarioId,
                                                 @PathVariable Long conteudoId) {
        try {
            return ResponseEntity.ok(favoritoService.buscarPorId(usuarioId, conteudoId));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Favoritos> criar(@Valid @RequestBody Favoritos favorito) {
        favorito.setCriadoEm(OffsetDateTime.now());
        Favoritos favoritoSalvo = favoritoService.salvar(favorito);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{usuarioId}/{conteudoId}")
                .buildAndExpand(favoritoSalvo.getUsuarioId(), favoritoSalvo.getConteudoId())
                .toUri();

        return ResponseEntity.created(location).body(favoritoSalvo);
    }

    @PutMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<Favoritos> atualizar(@PathVariable Long usuarioId,
                                               @PathVariable Long conteudoId,
                                               @Valid @RequestBody Favoritos favorito) {
        try {
            Favoritos favoritoAtualizado = favoritoService.atualizar(usuarioId, conteudoId, favorito);
            return ResponseEntity.ok(favoritoAtualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{usuarioId}/{conteudoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long usuarioId,
                                        @PathVariable Long conteudoId) {
        try {
            favoritoService.excluir(usuarioId, conteudoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
