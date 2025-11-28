package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public Genero criar(Genero genero) {
        log.info("Iniciando cadastro de novo gênero: '{}'", genero.getNome());

        if (generoRepository.existsByNome(genero.getNome())) {
            log.warn("Tentativa de criar gênero duplicado: '{}'", genero.getNome());
            throw new RuntimeException("Gênero com o nome '" + genero.getNome() + "' já existe.");
        }

        Genero salvo = generoRepository.save(genero);

        log.info("Gênero cadastrado com sucesso. ID: {}", salvo.getId());

        return salvo;
    }

    public List<Genero> listarTodos() {
        log.debug("Listando todos os gêneros disponíveis.");
        return generoRepository.findAll();
    }

    public Genero buscarPorId(Long id) {
        log.debug("Buscando gênero ID: {}", id);

        return generoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Gênero ID {} não encontrado.", id);
                    return new RuntimeException("Gênero não encontrado com id: " + id);
                });
    }

    public Genero atualizar(Long id, Genero generoAtualizado) {
        log.info("Solicitação de atualização para o gênero ID: {}", id);

        Genero generoExistente = buscarPorId(id);

        log.debug("Atualizando nome de '{}' para '{}'", generoExistente.getNome(), generoAtualizado.getNome());

        generoExistente.setNome(generoAtualizado.getNome());
        generoExistente.setDescricao(generoAtualizado.getDescricao());

        Genero salvo = generoRepository.save(generoExistente);
        log.info("Gênero ID {} atualizado com sucesso.", id);

        return salvo;
    }

    public void deletar(Long id) {
        log.info("Solicitação para deletar gênero ID: {}", id);

        if (!generoRepository.existsById(id)) {
            log.warn("Tentativa de deletar gênero inexistente ID: {}", id);
            throw new RuntimeException("Gênero não encontrado com id: " + id);
        }

        generoRepository.deleteById(id);
        log.info("Gênero ID {} removido.", id);
    }
}