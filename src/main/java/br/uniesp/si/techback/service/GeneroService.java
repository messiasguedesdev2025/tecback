package br.uniesp.si.techback.service;

import br.uniesp.si.techback.exception.EntidadeNaoEncontradaException;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    // Lista todos os gêneros
    public List<Genero> listar() {
        return generoRepository.findAll();
    }

    // Busca gênero por ID
    public Genero buscarPorId(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Gênero não encontrado com o ID: " + id));
    }

    // Salva um novo gênero
    @Transactional
    public Genero salvar(Genero genero) {
        return generoRepository.save(genero);
    }

    // Atualiza um gênero existente
    @Transactional
    public Genero atualizar(Long id, Genero genero) {
        if (!generoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Gênero não encontrado com o ID: " + id);
        }
        genero.setId(id);
        return generoRepository.save(genero);
    }

    // Exclui um gênero
    @Transactional
    public void excluir(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Gênero não encontrado com o ID: " + id);
        }
        generoRepository.deleteById(id);
    }
}
