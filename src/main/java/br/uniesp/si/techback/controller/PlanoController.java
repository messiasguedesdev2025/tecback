package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService planoService;

    // POST: Cria um novo plano
    @PostMapping
    public ResponseEntity<Plano> criar(@Valid @RequestBody Plano plano) {
        Plano novoPlano = planoService.criar(plano);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlano);
    }

    // GET: Lista todos os planos
    @GetMapping
    public ResponseEntity<List<Plano>> listarTodos() {
        return ResponseEntity.ok(planoService.listarTodos());
    }

    // GET: Busca um plano pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Long id) {
        Plano plano = planoService.buscarPorId(id);
        return ResponseEntity.ok(plano);
    }

    // PUT: Atualiza um plano
    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Plano planoAtualizado) {
        Plano plano = planoService.atualizar(id, planoAtualizado);
        return ResponseEntity.ok(plano);
    }

    // DELETE: Deleta um plano
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}