package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.response.ViaCepResponse;
import br.uniesp.si.techback.model.Endereco;
import br.uniesp.si.techback.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ViaCepService viaCepService;

    // Método crucial: Integração externa
    public Endereco buscarEnderecoParaPreenchimento(String cep) {
        log.info("Iniciando busca externa de endereço pelo CEP: {}", cep);

        ViaCepResponse dto = viaCepService.buscarEnderecoPorCep(cep);

        log.debug("ViaCep retornou dados para o CEP {}: {}", cep, dto);

        Endereco enderecoPreenchido = converterDtoParaEntity(dto);

        enderecoPreenchido.setNumero(null);

        return enderecoPreenchido;
    }

    private Endereco converterDtoParaEntity(ViaCepResponse response) {
        Endereco endereco = new Endereco();
        endereco.setCep(response.getCep());
        endereco.setLogradouro(response.getLogradouro());
        endereco.setComplemento(response.getComplemento());
        endereco.setBairro(response.getBairro());
        endereco.setCidade(response.getLocalidade());
        endereco.setEstado(response.getUf());
        return endereco;
    }

    public Endereco criar(Endereco endereco) {
        log.info("Cadastrando novo endereço para o CEP: {}", endereco.getCep());

        Endereco salvo = enderecoRepository.save(endereco);

        log.info("Endereço salvo com sucesso. ID: {}", salvo.getId());
        return salvo;
    }

    public List<Endereco> listarTodos() {
        log.debug("Listando todos os endereços cadastrados...");
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        log.debug("Buscando endereço ID: {}", id);
        return enderecoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Endereço ID {} não encontrado.", id);
                    return new RuntimeException("Endereço não encontrado com id: " + id);
                });
    }

    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        log.info("Atualizando endereço ID: {}", id);

        Endereco endereco = buscarPorId(id);

        if (!endereco.getCep().equals(enderecoAtualizado.getCep())) {
            log.info("Alteração de CEP detectada no endereço ID {}: De '{}' para '{}'",
                    id, endereco.getCep(), enderecoAtualizado.getCep());
        }

        endereco.setLogradouro(enderecoAtualizado.getLogradouro());
        endereco.setNumero(enderecoAtualizado.getNumero());
        endereco.setComplemento(enderecoAtualizado.getComplemento());
        endereco.setBairro(enderecoAtualizado.getBairro());
        endereco.setCidade(enderecoAtualizado.getCidade());
        endereco.setEstado(enderecoAtualizado.getEstado());
        endereco.setCep(enderecoAtualizado.getCep());

        return enderecoRepository.save(endereco);
    }

    public void deletar(Long id) {
        log.info("Solicitação para deletar endereço ID: {}", id);

        Endereco endereco = buscarPorId(id);

        enderecoRepository.delete(endereco);
        log.info("Endereço ID {} deletado.", id);
    }
}