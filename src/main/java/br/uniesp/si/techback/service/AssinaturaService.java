package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.AssinaturaRequestDTO;
import br.uniesp.si.techback.model.Assinatura;
import br.uniesp.si.techback.model.Plano;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssinaturaService {

    private final AssinaturaRepository assinaturaRepository;
    private final PlanoService planoService;
    private final UsuarioService usuarioService;

    public Assinatura criar(Assinatura assinatura) {
        Long usuarioId = assinatura.getUsuario().getId();
        Long planoId = assinatura.getPlano().getId();

        log.info("Iniciando criação de assinatura. Usuário ID: {}, Plano ID: {}", usuarioId, planoId);

        Plano planoExistente = planoService.buscarPorId(planoId);
        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioId);

        assinatura.setPlano(planoExistente);
        assinatura.setUsuario(usuarioExistente);

        Assinatura salva = assinaturaRepository.save(assinatura);

        log.info("Assinatura criada com sucesso. ID Gerado: {}", salva.getId());

        return salva;
    }

    public Assinatura criarComDTO(AssinaturaRequestDTO dto) {

        log.info("Solicitação de assinatura via DTO. Usuário ID: {}, Plano ID: {}", dto.getUsuarioId(), dto.getPlanoId());

        log.debug("Buscando dependências (Usuario/Plano) no banco de dados...");
        Plano planoExistente = planoService.buscarPorId(dto.getPlanoId());
        Usuario usuarioExistente = usuarioService.buscarPorId(dto.getUsuarioId());

        Assinatura novaAssinatura = new Assinatura();
        novaAssinatura.setUsuario(usuarioExistente);
        novaAssinatura.setPlano(planoExistente);
        novaAssinatura.setDataInicio(dto.getDataInicio());
        novaAssinatura.setDataFim(dto.getDataFim());
        novaAssinatura.setStatus(dto.getStatus());

        Assinatura salva = assinaturaRepository.save(novaAssinatura);

        log.info("Assinatura (via DTO) persistida com sucesso. ID: {}", salva.getId());
        return salva;
    }

    public List<Assinatura> listarTodos() {

        log.debug("Listando todas as assinaturas...");
        return assinaturaRepository.findAll();
    }

    public Assinatura buscarPorId(Long id) {
        log.debug("Buscando assinatura ID: {}", id);

        return assinaturaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Assinatura não encontrada. ID: {}", id);
                    return new RuntimeException("Assinatura não encontrada com id: " + id);
                });
    }

    public Assinatura atualizar(Long id, Assinatura assinaturaAtualizada) {

        log.info("Atualizando dados da assinatura ID: {}", id);

        Assinatura assinaturaExistente = buscarPorId(id);


        log.debug("Alterando status de '{}' para '{}'", assinaturaExistente.getStatus(), assinaturaAtualizada.getStatus());

        assinaturaExistente.setDataInicio(assinaturaAtualizada.getDataInicio());
        assinaturaExistente.setDataFim(assinaturaAtualizada.getDataFim());
        assinaturaExistente.setStatus(assinaturaAtualizada.getStatus());

        return assinaturaRepository.save(assinaturaExistente);
    }

    public void deletar(Long id) {
        log.info("Solicitação de cancelamento/exclusão da assinatura ID: {}", id);

        if (!assinaturaRepository.existsById(id)) {
            log.warn("Tentativa de deletar assinatura inexistente ID: {}", id);
            throw new RuntimeException("Assinatura não encontrada com id: " + id);
        }
        assinaturaRepository.deleteById(id);
        log.info("Assinatura ID {} deletada com sucesso.", id);
    }

    public List<Assinatura> listarAssinaturasParaRenovacao() {
        LocalDateTime dataAlvo = LocalDateTime.now().plusDays(30);

        log.info("Verificando assinaturas que expiram até: {}", dataAlvo);

        List<Assinatura> assinaturas = assinaturaRepository.findActiveSubscriptionsExpiringSoon(dataAlvo);

        log.info("Encontradas {} assinaturas próximas do vencimento.", assinaturas.size());

        return assinaturas;
    }
}