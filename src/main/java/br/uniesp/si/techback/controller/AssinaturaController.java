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


    @PostMapping
    public ResponseEntity<Assinatura> criar(@Valid @RequestBody Assinatura assinatura) {

        log.info("Recebida requisição POST para criar nova assinatura.");

        log.debug("Dados recebidos (Assinatura Model): {}", assinatura);

        Assinatura novaAssinatura = assinaturaService.criar(assinatura);


        log.info("Nova assinatura criada com sucesso. ID: {}", novaAssinatura.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAssinatura);
    }


    @PostMapping("/dto")
    public ResponseEntity<Assinatura> criarComDto(@Valid @RequestBody AssinaturaRequestDTO dto) {
        log.info("Recebida requisição POST /dto para criar assinatura usando DTO.");
        log.debug("DTO recebido: {}", dto);

        Assinatura novaAssinatura = assinaturaService.criarComDTO(dto);

        log.info("Assinatura criada via DTO com sucesso. ID: {}", novaAssinatura.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAssinatura);
    }


    @GetMapping
    public ResponseEntity<List<Assinatura>> listarTodos() {
        log.info("Recebida requisição GET para listar todas as assinaturas.");
        List<Assinatura> assinaturas = assinaturaService.listarTodos();


        log.debug("Foram encontradas {} assinaturas.", assinaturas.size());
        return ResponseEntity.ok(assinaturas);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Assinatura> buscarPorId(@PathVariable Long id) {
        log.info("Recebida requisição GET para buscar assinatura pelo ID: {}", id);

        Assinatura assinatura = assinaturaService.buscarPorId(id);

        log.info("Assinatura encontrada. ID: {}", id);
        return ResponseEntity.ok(assinatura);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Assinatura> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody Assinatura assinaturaAtualizada) {
        log.info("Recebida requisição PUT para atualizar a assinatura ID: {}", id);
        log.debug("Dados de atualização recebidos: {}", assinaturaAtualizada);

        Assinatura assinatura = assinaturaService.atualizar(id, assinaturaAtualizada);

        log.info("Assinatura ID: {} atualizada com sucesso.", id);
        return ResponseEntity.ok(assinatura);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.warn("Recebida requisição DELETE para deletar a assinatura ID: {}", id); // Nível WARN para operação destrutiva

        assinaturaService.deletar(id);

        log.info("Assinatura ID: {} deletada com sucesso.", id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/expirando")
    public ResponseEntity<List<Assinatura>> listarAssinaturasParaRenovacao() {
        log.info("Recebida requisição GET /api/assinaturas/expirando para listar assinaturas próximas do vencimento.");

        List<Assinatura> assinaturas = assinaturaService.listarAssinaturasParaRenovacao();

        log.info("Consulta de renovação finalizada. Foram encontradas {} assinaturas para renovação.", assinaturas.size());
        return ResponseEntity.ok(assinaturas);
    }
}