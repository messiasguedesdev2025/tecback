package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // POST: Cria um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criar(@Valid @RequestBody Usuario usuario) {
        // Espera JSON com o ID do Endereco aninhado: {"nome": "...", "endereco": {"id": 5}}
        Usuario novoUsuario = usuarioService.criar(usuario);
        // Por segurança, você deve retornar um DTO que não exponha a senha!
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // GET: Lista todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        // Novamente, idealmente, retornar DTOs sem senha.
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET: Busca um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // PUT: Atualiza um usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id,
                                             @Valid @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioService.atualizar(id, usuarioAtualizado);
        return ResponseEntity.ok(usuario);
    }

    // DELETE: Deleta um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}