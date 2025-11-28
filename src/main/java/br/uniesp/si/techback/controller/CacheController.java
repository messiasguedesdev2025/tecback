package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.cache.ServicoCacheExemplo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheController.class);

    private final ServicoCacheExemplo servicoCacheExemplo;

    public CacheController(ServicoCacheExemplo servicoCacheExemplo) {
        this.servicoCacheExemplo = servicoCacheExemplo;
    }



    @PostMapping("/usuarios")
    public ResponseEntity<String> criarUsuario(@RequestParam String nome) {


        LOGGER.info("Recebida requisição POST para criar usuário com nome: {}", nome);

        String id = servicoCacheExemplo.criarUsuario(nome);



        LOGGER.info("Usuário criado com sucesso. ID retornado: {}", id);
        return ResponseEntity.ok("Usuário criado com ID: " + id);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<String> buscarUsuario(@PathVariable String id) {

        LOGGER.info("Recebida requisição GET para buscar usuário com ID: {}", id);

        long inicio = System.currentTimeMillis();
        String usuario = servicoCacheExemplo.buscarUsuario(id);
        long tempo = System.currentTimeMillis() - inicio;



        LOGGER.info("Tempo de busca: {}ms", tempo);
        LOGGER.debug("Usuário encontrado: {}", usuario);
        return ResponseEntity.ok(usuario + " (Tempo: " + tempo + "ms)");
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<String> atualizarUsuario(
            @PathVariable String id,
            @RequestParam String nome) {


        LOGGER.info("Recebida requisição PUT para atualizar usuário ID: {} com novo nome: {}", id, nome);

        servicoCacheExemplo.atualizarUsuario(id, nome);


        LOGGER.info("Usuário ID: {} atualizado com sucesso. Esperada invalidação do cache.", id);
        return ResponseEntity.ok("Usuário atualizado com sucesso");
    }

    @DeleteMapping("/usuarios/{id}/cache")
    public ResponseEntity<String> removerDoCache(@PathVariable String id) {

        LOGGER.warn("Recebida requisição DELETE para remover o usuário ID: {} explicitamente do cache.", id);

        servicoCacheExemplo.removerDoCache(id);


        LOGGER.info("Entrada do usuário ID: {} removida do cache.", id);
        return ResponseEntity.ok("Usuário removido do cache");
    }

    @DeleteMapping("/usuarios/cache")
    public ResponseEntity<String> limparCache() {

        LOGGER.warn("Recebida requisição DELETE para limpar TODO o cache de usuários.");

        servicoCacheExemplo.limparCache();


        LOGGER.info("Todo o cache de usuários foi limpo com sucesso.");
        return ResponseEntity.ok("Cache limpo com sucesso");
    }
}