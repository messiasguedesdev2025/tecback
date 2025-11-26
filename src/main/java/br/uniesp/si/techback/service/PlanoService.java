package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.repository.PlanoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;

    /**
     * Cria um novo plano no banco de dados.
     */
    public Plano criar(Plano plano) {
        // Validação de Unicidade
        if (planoRepository.existsByNome(plano.getNome())) {
            throw new RuntimeException("Plano com o nome '" + plano.getNome() + "' já existe.");
        }

        return planoRepository.save(plano);
    }

    /**
     * Retorna todos os planos.
     */
    public List<Plano> listarTodos() {
        return planoRepository.findAll();
    }

    /**
     * Busca um plano pelo ID.
     */
    public Plano buscarPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado com id: " + id));
    }

    /**
     * Atualiza um plano existente.
     */
    public Plano atualizar(Long id, Plano planoAtualizado) {
        Plano planoExistente = buscarPorId(id);

        // 1. Validação de Unicidade: Verifica se o nome mudou e se o novo nome já existe
        if (!planoExistente.getNome().equals(planoAtualizado.getNome()) && planoRepository.existsByNome(planoAtualizado.getNome())) {
            throw new RuntimeException("Já existe um plano com o nome '" + planoAtualizado.getNome() + "'.");
        }

        // 2. Atualiza todos os campos da Entity
        planoExistente.setNome(planoAtualizado.getNome());
        planoExistente.setDescricao(planoAtualizado.getDescricao());
        planoExistente.setPreco(planoAtualizado.getPreco()); // Campo BigDecimal
        planoExistente.setDuracaoMeses(planoAtualizado.getDuracaoMeses());
        planoExistente.setLimiteDispositivos(planoAtualizado.getLimiteDispositivos());

        return planoRepository.save(planoExistente);
    }

    /**
     * Deleta um plano pelo ID.
     */
    public void deletar(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new RuntimeException("Plano não encontrado com id: " + id);
        }
        planoRepository.deleteById(id);
    }
}