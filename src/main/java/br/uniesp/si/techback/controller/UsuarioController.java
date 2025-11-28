package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {


    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {

        LOGGER.info("Recebida requisição POST para criar um novo usuário.");

        LOGGER.debug("Dados de Usuário recebidos: {}", usuario);


        Usuario novoUsuario = usuarioService.criar(usuario);


        LOGGER.info("Usuário criado com sucesso. ID: {}", novoUsuario.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }


    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {

        LOGGER.info("Recebida requisição GET para listar todos os usuários.");

        List<Usuario> usuarios = usuarioService.listarTodos();


        LOGGER.debug("Encontrados {} usuários.", usuarios.size());


        LOGGER.info("Listagem de usuários finalizada.");

        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {

        LOGGER.info("Recebida requisição GET para buscar usuário pelo ID: {}", id);

        Usuario usuario = usuarioService.buscarPorId(id);


        LOGGER.info("Busca do usuário ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(usuario);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                             @Valid @RequestBody Usuario usuarioAtualizado) {

        LOGGER.info("Recebida requisição PUT para atualizar usuário ID: {}", id);

        LOGGER.debug("Dados de atualização recebidos para o ID {}: {}", id, usuarioAtualizado);

        Usuario usuario = usuarioService.atualizar(id, usuarioAtualizado);


        LOGGER.info("Usuário ID: {} atualizado com sucesso.", id);
        return ResponseEntity.ok(usuario);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
