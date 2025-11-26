package br.uniesp.si.techback.service;

import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario; // Assumindo que a model existe
import br.uniesp.si.techback.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}