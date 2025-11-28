package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.request.AssinaturaRequestDTO;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.service.AssinaturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assinaturas")
@RequiredArgsConstructor
@Slf4j
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    // POST: Cria uma nova assinatura
    @PostMapping
    public ResponseEntity<Assinatura> criar(@Valid @RequestBody Assinatura assinatura) {
        // Espera JSON com os IDs aninhados: {"plano": {"id": 1}, "usuario": {"id": 1}, "dataInicio": "..."}
        Assinatura novaAssinatura = assinaturaService.criar(assinatura);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAssinatura);
    }

    // A) ENDPOINT USANDO DTO
    @PostMapping("/dto")
    public ResponseEntity<Assinatura> criarComDto(@Valid @RequestBody AssinaturaRequestDTO dto) {
        // Chama o método que usa o DTO
        Assinatura novaAssinatura = assinaturaService.criarComDTO(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAssinatura);
    }

    // GET: Lista todas as assinaturas
    @GetMapping
    public ResponseEntity<List<Assinatura>> listarTodos() {
        return ResponseEntity.ok(assinaturaService.listarTodos());
    }

    // GET: Busca uma assinatura pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Assinatura> buscarPorId(@PathVariable Long id) {
        Assinatura assinatura = assinaturaService.buscarPorId(id);
        return ResponseEntity.ok(assinatura);
    }

    // PUT: Atualiza uma assinatura
    @PutMapping("/{id}")
    public ResponseEntity<Assinatura> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody Assinatura assinaturaAtualizada) {
        Assinatura assinatura = assinaturaService.atualizar(id, assinaturaAtualizada);
        return ResponseEntity.ok(assinatura);
    }

    // DELETE: Deleta uma assinatura
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        assinaturaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // NOVO ENDPOINT DE CONSULTA ESPECIALIZADA: RENOVAÇÃO
    @GetMapping("/expirando")
    public ResponseEntity<List<Assinatura>> listarAssinaturasParaRenovacao() {
        log.info("Recebida requisição GET /api/assinaturas/expirando para listar assinaturas próximas do vencimento.");
        // Chama o metodo no Service que contém a lógica da query de 30 dias
        List<Assinatura> assinaturas = assinaturaService.listarAssinaturasParaRenovacao();
        return ResponseEntity.ok(assinaturas);
    }
}