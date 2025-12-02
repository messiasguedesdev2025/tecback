package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Importações necessárias para o Logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;

    // POST: Cria um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        LOGGER.info("Recebida requisição POST para criar um novo usuário.");
        LOGGER.debug("Dados de Usuário recebidos: {}", usuario);

        Usuario novoUsuario = usuarioService.criar(usuario);

        // Log INFO: Sucesso e ID gerado
        LOGGER.info("Usuário criado com sucesso. ID: {}", novoUsuario.getId());
        // Por segurança, você deve retornar um DTO que não exponha a senha!
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // GET: Lista todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        LOGGER.info("Recebida requisição GET para listar todos os usuários.");

        List<Usuario> usuarios = usuarioService.listarTodos();

        LOGGER.debug("Encontrados {} usuários.", usuarios.size());

        LOGGER.info("Listagem de usuários finalizada.");
        return ResponseEntity.ok(usuarios);
    }

    // GET: Busca um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar usuário pelo ID: {}", id);

        Usuario usuario = usuarioService.buscarPorId(id);

        LOGGER.info("Busca do usuário ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(usuario);
    }

    // PUT: Atualiza um usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                             @Valid @RequestBody Usuario usuarioAtualizado) {

        LOGGER.info("Recebida requisição PUT para atualizar usuário ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, usuarioAtualizado);

        Usuario usuario = usuarioService.atualizar(id, usuarioAtualizado);


        LOGGER.info("Usuário ID: {} atualizado com sucesso.", id);
        return ResponseEntity.ok(usuario);
    }

    // DELETE: Deleta um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        LOGGER.warn("Recebida requisição DELETE para deletar o usuário ID: {}", id);

        usuarioService.deletar(id);


        LOGGER.info("Usuário ID: {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build();
    }
}