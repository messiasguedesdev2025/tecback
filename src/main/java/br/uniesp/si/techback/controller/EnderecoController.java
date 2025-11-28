package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Endereco;
import br.uniesp.si.techback.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/enderecos")
@RequiredArgsConstructor // <-- construtor automático
public class EnderecoController {


    private static final Logger LOGGER = LoggerFactory.getLogger(EnderecoController.class);

    private final EnderecoService enderecoService;

    @GetMapping("/buscar-cep/{cep}")
    public ResponseEntity<Endereco> buscarEndereco(@PathVariable String cep) {

        LOGGER.info("Recebida requisição GET para buscar endereço pelo CEP: {}", cep);


        Endereco endereco = enderecoService.buscarEnderecoParaPreenchimento(cep);


        LOGGER.debug("Endereço encontrado para o CEP {}: {}", cep, endereco);


        LOGGER.info("Busca de endereço via CEP {} finalizada com sucesso.", cep);
        return ResponseEntity.ok(endereco);
    }

    @PostMapping
    public ResponseEntity<Endereco> criar(@RequestBody Endereco endereco) {

        LOGGER.info("Recebida requisição POST para criar um novo endereço.");

        LOGGER.debug("Dados de endereço recebidos: {}", endereco);

        Endereco novoEndereco = enderecoService.criar(endereco);


        LOGGER.info("Endereço criado com sucesso. ID: {}", novoEndereco.getId());
        return ResponseEntity.ok(novoEndereco);
    }

    @GetMapping
    public ResponseEntity<List<Endereco>> listarTodos() {

        LOGGER.info("Recebida requisição GET para listar todos os endereços.");

        List<Endereco> enderecos = enderecoService.listarTodos();


        LOGGER.debug("Total de {} endereços encontrados.", enderecos.size());


        LOGGER.info("Listagem de endereços finalizada.");
        return ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar endereço pelo ID: {}", id);

        Endereco endereco = enderecoService.buscarPorId(id);


        LOGGER.info("Busca de endereço ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id,
                                              @RequestBody Endereco enderecoAtualizado) {

        LOGGER.info("Recebida requisição PUT para atualizar endereço ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, enderecoAtualizado);

        Endereco endereco = enderecoService.atualizar(id, enderecoAtualizado);


        LOGGER.info("Endereço ID: {} atualizado com sucesso.", id);
        return ResponseEntity.ok(endereco);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        LOGGER.warn("Recebida requisição DELETE para deletar o endereço ID: {}", id);

        enderecoService.deletar(id);


        LOGGER.info("Endereço ID: {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}