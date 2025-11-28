package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.AssinaturaRequestDTO;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario; // Assumindo que a model existe
import br.uniesp.si.techback.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final PlanoService planoService;
    private final UsuarioService usuarioService; // Assumindo que este serviço existe

    /**
     * Valida e persiste uma nova assinatura.
     */
    public Assinatura criar(Assinatura assinatura) {
        // 1. Validação de Dependência: Garante que o Plano e o Usuário existam
        Long planoId = assinatura.getPlano().getId();
        Plano planoExistente = planoService.buscarPorId(planoId);

        Long usuarioId = assinatura.getUsuario().getId();
        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioId);
        // Lembre-se: o UsuarioService deve ser implementado, ou este método falhará.

        // 2. Atribui os objetos gerenciados pelo JPA
        assinatura.setPlano(planoExistente);
        assinatura.setUsuario(usuarioExistente);

        // 3. (Opcional) Poderia haver uma validação para garantir que o usuário não
        // possua outra assinatura ATIVA antes de criar uma nova.

        return assinaturaRepository.save(assinatura);
    }

    // Recebe um DTO simplificado e faz a busca das dependências por ID
    public Assinatura criarComDTO(AssinaturaRequestDTO dto) {

        // 1. Busca as dependências (Plano e Usuário) usando os IDs do DTO
        Plano planoExistente = planoService.buscarPorId(dto.getPlanoId());
        Usuario usuarioExistente = usuarioService.buscarPorId(dto.getUsuarioId());

        // 2. Mapeamento dos campos do DTO para a Entity
        Assinatura novaAssinatura = new Assinatura();
        novaAssinatura.setUsuario(usuarioExistente);
        novaAssinatura.setPlano(planoExistente);
        novaAssinatura.setDataInicio(dto.getDataInicio());
        novaAssinatura.setDataFim(dto.getDataFim());
        novaAssinatura.setStatus(dto.getStatus());

        // Vantagem: Lógica de negócio e validação ficam isoladas e limpas
        return assinaturaRepository.save(novaAssinatura);
    }
    /**
     * Retorna todas as assinaturas.
     */
    public List<Assinatura> listarTodos() {
        return assinaturaRepository.findAll();
    }

    /**
     * Busca uma assinatura pelo ID.
     */
    public Assinatura buscarPorId(Long id) {
        return assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada com id: " + id));
    }

    /**
     * Atualiza uma assinatura existente (focado em datas e status).
     */
    public Assinatura atualizar(Long id, Assinatura assinaturaAtualizada) {
        Assinatura assinaturaExistente = buscarPorId(id);

        // O ID do Plano e do Usuário geralmente NÃO são alterados em uma atualização de assinatura.
        // Se a regra de negócio permitir a mudança de plano, a validação de dependência deve ser refeita.

        // Atualiza os campos de controle
        assinaturaExistente.setDataInicio(assinaturaAtualizada.getDataInicio());
        assinaturaExistente.setDataFim(assinaturaAtualizada.getDataFim());
        assinaturaExistente.setStatus(assinaturaAtualizada.getStatus());

        return assinaturaRepository.save(assinaturaExistente);
    }

    /**
     * Deleta uma assinatura pelo ID.
     */
    public void deletar(Long id) {
        if (!assinaturaRepository.existsById(id)) {
            throw new RuntimeException("Assinatura não encontrada com id: " + id);
        }
        assinaturaRepository.deleteById(id);
    }

    // Em AssinaturaService.java

    public List<Assinatura> listarAssinaturasParaRenovacao() {
        // Define a data alvo: Daqui a 30 dias
        LocalDateTime dataAlvo = LocalDateTime.now().plusDays(30);

        return assinaturaRepository.findActiveSubscriptionsExpiringSoon(dataAlvo);
    }
}