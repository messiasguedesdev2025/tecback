package br.uniesp.si.techback.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class ServicoCacheExemplo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicoCacheExemplo.class);
    

    private final Map<String, String> bancoDeDados = new HashMap<>();
    

    @Cacheable(value = "usuarios", key = "#id")
    public String buscarUsuario(String id) {
        LOGGER.info("Buscando usuário no banco de dados: {}", id);
        // Simula uma consulta demorada
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return bancoDeDados.getOrDefault(id, "Usuário não encontrado");
    }
    

    @CachePut(value = "usuarios", key = "#id")
    public String atualizarUsuario(String id, String nome) {
        LOGGER.info("Atualizando usuário: {} com nome: {}", id, nome);
        bancoDeDados.put(id, nome);
        return nome;
    }
    

    @CacheEvict(value = "usuarios", key = "#id")
    public void removerDoCache(String id) {
        LOGGER.info("Removendo usuário do cache: {}", id);

    }
    

    @CacheEvict(value = "usuarios", allEntries = true)
    public void limparCache() {
        LOGGER.info("Limpando todo o cache de usuários");
    }
    

    public String criarUsuario(String nome) {
        String id = UUID.randomUUID().toString();
        bancoDeDados.put(id, nome);
        return id;
    }
}
