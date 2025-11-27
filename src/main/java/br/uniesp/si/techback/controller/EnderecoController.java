package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Endereco;
import br.uniesp.si.techback.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor // <-- construtor automático
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/buscar-cep/{cep}")
    public ResponseEntity<Endereco> buscarEndereco(@PathVariable String cep) {
        // Chama o Service para buscar e mapear o Endereço
        Endereco endereco = enderecoService.buscarEnderecoParaPreenchimento(cep);

        // Retorna o objeto Entity (com numero e ID nulos)
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public ResponseEntity<Endereco> criar(@RequestBody Endereco endereco) {
        return ResponseEntity.ok(enderecoService.criar(endereco));
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {
        return ResponseEntity.ok(enderecoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id,
                                              @RequestBody Endereco enderecoAtualizado) {
        return ResponseEntity.ok(enderecoService.atualizar(id, enderecoAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        enderecoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
