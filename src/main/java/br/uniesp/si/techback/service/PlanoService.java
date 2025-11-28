package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;

    public Plano criar(Plano plano) {
        log.info("Iniciando criação de novo plano: '{}', Preço: R$ {}", plano.getNome(), plano.getPreco());

        if (planoRepository.existsByNome(plano.getNome())) {
            log.warn("Tentativa de criar plano com nome duplicado: '{}'", plano.getNome());
            throw new RuntimeException("Plano com o nome '" + plano.getNome() + "' já existe.");
        }

        Plano salvo = planoRepository.save(plano);
        log.info("Plano criado com sucesso. ID: {}", salvo.getId());
        return salvo;
    }

    public List<Plano> listarTodos() {
        log.debug("Listando todos os planos disponíveis.");
        return planoRepository.findAll();
    }

    public Plano buscarPorId(Long id) {
        log.debug("Buscando plano ID: {}", id);
        return planoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Plano ID {} não encontrado.", id);
                    return new RuntimeException("Plano não encontrado com id: " + id);
                });
    }

    public Plano atualizar(Long id, Plano planoAtualizado) {
        log.info("Solicitação de atualização para o Plano ID: {}", id);

        Plano planoExistente = buscarPorId(id);

        if (!planoExistente.getNome().equals(planoAtualizado.getNome()) && planoRepository.existsByNome(planoAtualizado.getNome())) {
            log.warn("Conflito de nome na atualização. Plano '{}' já existe.", planoAtualizado.getNome());
            throw new RuntimeException("Já existe um plano com o nome '" + planoAtualizado.getNome() + "'.");
        }

        if (planoExistente.getPreco().compareTo(planoAtualizado.getPreco()) != 0) {
            log.info("ALTERAÇÃO DE PREÇO no Plano ID {}: De R$ {} para R$ {}",
                    id, planoExistente.getPreco(), planoAtualizado.getPreco());
        }

        planoExistente.setNome(planoAtualizado.getNome());
        planoExistente.setDescricao(planoAtualizado.getDescricao());
        planoExistente.setPreco(planoAtualizado.getPreco());
        planoExistente.setDuracaoMeses(planoAtualizado.getDuracaoMeses());
        planoExistente.setLimiteDispositivos(planoAtualizado.getLimiteDispositivos());

        Plano salvo = planoRepository.save(planoExistente);
        log.info("Plano ID {} atualizado com sucesso.", id);

        return salvo;
    }

    public void deletar(Long id) {
        log.info("Solicitação para deletar Plano ID: {}", id);

        if (!planoRepository.existsById(id)) {
            log.warn("Tentativa de deletar plano inexistente ID: {}", id);
            throw new RuntimeException("Plano não encontrado com id: " + id);
        }

        planoRepository.deleteById(id);
        log.info("Plano ID {} deletado com sucesso.", id);
    }
}