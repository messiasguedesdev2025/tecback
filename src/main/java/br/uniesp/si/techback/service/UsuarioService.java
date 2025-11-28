package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.model.Endereco;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import jakarta.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService;
    private final PasswordEncoder passwordEncoder;

    public Usuario criar(@Valid Usuario usuario) {
        log.info("Iniciando cadastro de novo usuário. Email: {}", usuario.getEmail());

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            log.warn("Tentativa de cadastro com email duplicado: {}", usuario.getEmail());
            throw new RuntimeException("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }

        if (usuario.getEndereco() != null && usuario.getEndereco().getId() != null) {
            Long enderecoId = usuario.getEndereco().getId();
            log.debug("Vinculando usuário ao endereço existente ID: {}", enderecoId);
            Endereco enderecoExistente = enderecoService.buscarPorId(enderecoId);
            usuario.setEndereco(enderecoExistente);
        } else {
            log.debug("Usuário criado sem endereço vinculado (opcional).");
            usuario.setEndereco(null);
        }

        log.debug("Criptografando senha do usuário antes de salvar...");
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        Usuario salvo = usuarioRepository.save(usuario);

        log.info("Usuário cadastrado com sucesso. ID: {}", salvo.getId());

        return salvo;
    }

    public List<Usuario> listarTodos() {

        List<Usuario> usuarios = usuarioRepository.findAll();
        log.debug("Listando todos os usuários. Total encontrado: {}", usuarios.size());
        return usuarios;
    }

    public Usuario buscarPorId(Long id) {
        log.debug("Buscando usuário pelo ID: {}", id);
        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário ID {} não encontrado.", id);
                    return new RuntimeException("Usuário não encontrado com id: " + id);
                });
    }

    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        log.info("Solicitação de atualização de dados para usuário ID: {}", id);

        Usuario usuarioExistente = buscarPorId(id);

        if (usuarioAtualizado.getEndereco() != null && usuarioAtualizado.getEndereco().getId() != null) {
            Long novoEnderecoId = usuarioAtualizado.getEndereco().getId();
            log.debug("Atualizando endereço do usuário ID {} para endereço ID {}", id, novoEnderecoId);
            Endereco novoEndereco = enderecoService.buscarPorId(novoEnderecoId);
            usuarioExistente.setEndereco(novoEndereco);
        } else {
            if (usuarioExistente.getEndereco() != null) {
                log.info("Removendo vínculo de endereço do usuário ID {}", id);
            }
            usuarioExistente.setEndereco(null);
        }

        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail())) {
            log.info("Alteração de email solicitada. De '{}' para '{}'", usuarioExistente.getEmail(), usuarioAtualizado.getEmail());

            if (usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
                log.warn("Conflito de email na atualização: {}", usuarioAtualizado.getEmail());
                throw new RuntimeException("O novo email já está em uso.");
            }
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());


        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {

            log.info("SECURITY EVENT: Senha do usuário ID {} foi alterada.", id);

            String novaSenhaCriptografada = passwordEncoder.encode(usuarioAtualizado.getSenha());
            usuarioExistente.setSenha(novaSenhaCriptografada);
        }

        Usuario salvo = usuarioRepository.save(usuarioExistente);
        log.info("Dados do usuário ID {} atualizados com sucesso.", id);

        return salvo;
    }

    public void deletar(Long id) {
        log.info("Solicitação crítica: Exclusão do usuário ID: {}", id);

        if (!usuarioRepository.existsById(id)) {
            log.warn("Tentativa de deletar usuário inexistente ID: {}", id);
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }

        usuarioRepository.deleteById(id);
        log.info("Usuário ID {} e seus dados cascata (favoritos, assinaturas) foram removidos.", id);
    }
}