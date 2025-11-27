package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.response.ViaCepResponse;
import br.uniesp.si.techback.model.Endereco;
import br.uniesp.si.techback.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // <-- Lombok cria o construtor com o repository
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final ViaCepService viaCepService;

    // Método novo para buscar e retornar o Entity preenchido
    public Endereco buscarEnderecoParaPreenchimento(String cep) {
        // 1. Chama o ViaCepService para obter o DTO
        ViaCepResponse dto = viaCepService.buscarEnderecoPorCep(cep);

        // 2. Converte o DTO para Entity
        Endereco enderecoPreenchido = converterDtoParaEntity(dto);

        // 3. Garante que os campos de imóvel (que não vêm da API) fiquem nulos
        // O ID é nulo por padrão. O complemento é preenchido (pode ser vazio).
        enderecoPreenchido.setNumero(null);

        return enderecoPreenchido;
    }

    // Método auxiliar de conversão (mantido)
    private Endereco converterDtoParaEntity(ViaCepResponse response) {
        Endereco endereco = new Endereco();

        endereco.setCep(response.getCep());
        endereco.setLogradouro(response.getLogradouro());

        // O complemento é mapeado, mesmo que venha vazio ("")
        endereco.setComplemento(response.getComplemento());

        endereco.setBairro(response.getBairro());
        endereco.setCidade(response.getLocalidade()); // localidade -> cidade
        endereco.setEstado(response.getUf());        // uf -> estado

        // O campo 'numero' não é setado aqui, permanecendo nulo.

        return endereco;
    }

    public Endereco criar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarTodos() {
        return enderecoRepository.findAll();
    }

    public Endereco buscarPorId(Long id) {
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado com id: " + id));
    }

    public Endereco atualizar(Long id, Endereco enderecoAtualizado) {
        Endereco endereco = buscarPorId(id);

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
        Endereco endereco = buscarPorId(id);
        enderecoRepository.delete(endereco);
    }
}
