package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.service.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/planos")
@RequiredArgsConstructor
public class PlanoController {


    private static final Logger LOGGER = LoggerFactory.getLogger(PlanoController.class);

    private final PlanoService planoService;


    @PostMapping
    public ResponseEntity<Plano> criar(@Valid @RequestBody Plano plano) {

        LOGGER.info("Recebida requisição POST para criar um novo plano.");

        LOGGER.debug("Dados de Plano recebidos: {}", plano);

        Plano novoPlano = planoService.criar(plano);


        LOGGER.info("Plano criado com sucesso. ID: {}", novoPlano.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPlano);
    }


    @GetMapping
    public ResponseEntity<List<Plano>> listarTodos() {

        LOGGER.info("Recebida requisição GET para listar todos os planos.");

        List<Plano> planos = planoService.listarTodos();


        LOGGER.debug("Encontrados {} planos.", planos.size());


        LOGGER.info("Listagem de planos finalizada.");
        return ResponseEntity.ok(planos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Plano> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar plano pelo ID: {}", id);

        Plano plano = planoService.buscarPorId(id);


        LOGGER.info("Busca do plano ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(plano);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Plano> atualizar(@PathVariable Long id,
                                           @Valid @RequestBody Plano planoAtualizado) {

        LOGGER.info("Recebida requisição PUT para atualizar plano ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, planoAtualizado);

        Plano plano = planoService.atualizar(id, planoAtualizado);


        LOGGER.info("Plano ID: {} atualizado com sucesso.", id);
        return ResponseEntity.ok(plano);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        LOGGER.warn("Recebida requisição DELETE para deletar o plano ID: {}", id);

        planoService.deletar(id);


        LOGGER.info("Plano ID: {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}