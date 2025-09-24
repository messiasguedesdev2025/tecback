package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Favoritos;
import br.uniesp.si.techback.model.FavoritosId;
import br.uniesp.si.techback.repository.FavoritosRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavoritosService {

    private final FavoritosRepository favoritosRepository;

    // Lista todos os favoritos
    public List<Favoritos> listar() {
        return favoritosRepository.findAll();
    }

    // Busca favorito por PK composta
    public Favoritos buscarPorId(Long usuarioId, Long conteudoId) {
        return favoritosRepository.findById(new FavoritosId(usuarioId, conteudoId))
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Favorito não encontrado para usuário ID: " + usuarioId + " e conteúdo ID: " + conteudoId));
    }

    // Salva um novo favorito
    @Transactional
    public Favoritos salvar(Favoritos favorito) {
        favorito.setCriadoEm(OffsetDateTime.now());
        return favoritosRepository.save(favorito);
    }

    // Atualiza um favorito existente (opcional, aqui normalmente só a data poderia ser alterada)
    @Transactional
    public Favoritos atualizar(Long usuarioId, Long conteudoId, Favoritos favorito) {
        Favoritos existente = buscarPorId(usuarioId, conteudoId);
        // Se houver campos para atualizar, insira aqui
        // exemplo: favorito.setCriadoEm(OffsetDateTime.now());
        return favoritosRepository.save(existente);
    }

    // Exclui um favorito
    @Transactional
    public void excluir(Long usuarioId, Long conteudoId) {
        Favoritos existente = buscarPorId(usuarioId, conteudoId);
        favoritosRepository.delete(existente);
    }
}
