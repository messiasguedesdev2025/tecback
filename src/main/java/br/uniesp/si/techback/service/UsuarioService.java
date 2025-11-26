package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.model.Endereco;
import br.uniesp.si.techback.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoService enderecoService; // Injetando o serviço de Endereço

    // O ideal seria injetar um PasswordEncoder aqui, mas usaremos a senha simples por enquanto.

    /**
     * Cria um novo usuário, validando o email e as dependências.
     */
    public Usuario criar(Usuario usuario) {
        // 1. Validação de Unicidade
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }

        // 2. Validação de Dependência: Garante que o Endereço exista
        if (usuario.getEndereco() != null && usuario.getEndereco().getId() != null) {
            Long enderecoId = usuario.getEndereco().getId();
            Endereco enderecoExistente = enderecoService.buscarPorId(enderecoId);
            usuario.setEndereco(enderecoExistente);
        } else {
            // Se o endereço for opcional, você pode permitir que seja null aqui.
            usuario.setEndereco(null);
        }

        // 3. (Sugestão de Segurança): A senha DEVE ser criptografada aqui na produção!
        // Ex: usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        return usuarioRepository.save(usuario);
    }

    /**
     * Retorna todos os usuários.
     */
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um usuário pelo ID.
     */
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com id: " + id));
    }

    /**
     * Atualiza um usuário existente.
     */
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);

        // 1. Validação de Endereço (se mudou)
        if (usuarioAtualizado.getEndereco() != null && usuarioAtualizado.getEndereco().getId() != null) {
            Long novoEnderecoId = usuarioAtualizado.getEndereco().getId();
            Endereco novoEndereco = enderecoService.buscarPorId(novoEnderecoId);
            usuarioExistente.setEndereco(novoEndereco);
        } else {
            usuarioExistente.setEndereco(null);
        }

        // 2. Atualiza campos (cuidado para não sobrescrever senhas vazias ou emails duplicados)
        if (!usuarioExistente.getEmail().equals(usuarioAtualizado.getEmail()) && usuarioRepository.existsByEmail(usuarioAtualizado.getEmail())) {
            throw new RuntimeException("O novo email já está em uso.");
        }

        usuarioExistente.setNome(usuarioAtualizado.getNome());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail());

        // Senha só deve ser atualizada se for fornecida no objeto de atualização.
        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
            // Criptografar antes de salvar!
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        }

        return usuarioRepository.save(usuarioExistente);
    }

    /**
     * Deleta um usuário pelo ID.
     */
    public void deletar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado com id: " + id);
        }
        // O CascadeType.ALL na Model garante que Assinaturas e Favoritos sejam excluídos.
        usuarioRepository.deleteById(id);
    }
}