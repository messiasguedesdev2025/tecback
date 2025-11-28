package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.FavoritoRequestDTO;
import br.uniesp.si.techback.dto.response.FavoritoResponseDTO;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.FavoritoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioService usuarioService;
    private final FilmeService filmeService;
    private final SerieService serieService;

    private static final String TYPE_FILME = "FILME";
    private static final String TYPE_SERIE = "SERIE";

    public Favorito criar(Favorito favorito) {
        Long usuarioId = favorito.getUsuario().getId();
        Long itemId = favorito.getItemId();
        String itemType = favorito.getItemType();

        log.info("Usuário ID {} tentando favoritar {} ID {}", usuarioId, itemType, itemId);

        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioId);
        favorito.setUsuario(usuarioExistente);

        if (favoritoRepository.existsByUsuarioIdAndItemIdAndItemType(usuarioId, itemId, itemType)) {
            log.warn("Tentativa de favorito duplicado. Usuário {} já possui o item {} favoritado.", usuarioId, itemId);
            throw new RuntimeException("Este item (" + itemType + " ID: " + itemId + ") já está na lista de favoritos do usuário.");
        }

        if (TYPE_FILME.equalsIgnoreCase(itemType)) {
            filmeService.buscarPorId(itemId);
        } else if (TYPE_SERIE.equalsIgnoreCase(itemType)) {
            serieService.buscarPorId(itemId);
        } else {
            log.error("Tipo de item inválido recebido: {}", itemType);
            throw new RuntimeException("Tipo de item inválido. Deve ser 'FILME' ou 'SERIE'.");
        }

        Favorito salvo = favoritoRepository.save(favorito);
        log.info("Favorito criado com sucesso. ID: {}", salvo.getId());
        return salvo;
    }

    public Favorito criarComDTO(FavoritoRequestDTO dto) {
        log.info("Recebida solicitação de favorito via DTO. Usuário: {}, Item: {} ({})",
                dto.getUsuarioId(), dto.getItemId(), dto.getItemType());

        Usuario usuarioExistente = usuarioService.buscarPorId(dto.getUsuarioId());

        String itemTypeUpper = dto.getItemType().toUpperCase();

        if (favoritoRepository.existsByUsuarioIdAndItemIdAndItemType(
                dto.getUsuarioId(), dto.getItemId(), itemTypeUpper)) {
            log.warn("Usuário {} tentou favoritar item {} duplicado.", dto.getUsuarioId(), dto.getItemId());
            throw new RuntimeException("Este item já está na lista de favoritos do usuário.");
        }

        log.debug("Verificando existência do item tipo {} com ID {}", itemTypeUpper, dto.getItemId());

        if (TYPE_FILME.equals(itemTypeUpper)) {
            filmeService.buscarPorId(dto.getItemId());
        } else if (TYPE_SERIE.equals(itemTypeUpper)) {
            serieService.buscarPorId(dto.getItemId());
        } else {
            log.error("Tipo de item desconhecido: {}", itemTypeUpper);
            throw new RuntimeException("Tipo de item inválido. Deve ser 'FILME' ou 'SERIE'.");
        }

        Favorito novoFavorito = new Favorito();
        novoFavorito.setUsuario(usuarioExistente);
        novoFavorito.setItemId(dto.getItemId());
        novoFavorito.setItemType(itemTypeUpper);

        return favoritoRepository.save(novoFavorito);
    }

    public List<Favorito> listarTodos() {
        log.debug("Listando todos os favoritos do sistema.");
        return favoritoRepository.findAll();
    }

    public Favorito buscarPorId(Long id) {
        return favoritoRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Favorito ID {} não encontrado.", id);
                    return new RuntimeException("Favorito não encontrado com id: " + id);
                });
    }

    public void deletar(Long id) {
        log.info("Solicitação para remover favorito ID: {}", id);

        if (!favoritoRepository.existsById(id)) {
            log.warn("Tentativa de deletar favorito inexistente ID: {}", id);
            throw new RuntimeException("Favorito não encontrado com id: " + id);
        }
        favoritoRepository.deleteById(id);
        log.info("Favorito ID {} removido com sucesso.", id);
    }

    public List<Favorito> listarPorUsuario(Long usuarioId) {
        log.debug("Buscando favoritos brutos do usuário ID: {}", usuarioId);
        usuarioService.buscarPorId(usuarioId);
        return favoritoRepository.findByUsuarioId(usuarioId);
    }

    private FavoritoResponseDTO toResponseDTO(Favorito favorito) {
        FavoritoResponseDTO dto = new FavoritoResponseDTO();
        dto.setId(favorito.getId());
        dto.setUsuarioId(favorito.getUsuario().getId());
        dto.setUsuarioEmail(favorito.getUsuario().getEmail());
        dto.setItemId(favorito.getItemId());
        dto.setItemType(favorito.getItemType());
        return dto;
    }

    public List<FavoritoResponseDTO> listarPorUsuarioComDTO(Long usuarioId) {
        log.info("Listando favoritos (DTO) para usuário ID: {}", usuarioId);

        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        List<Favorito> entities = favoritoRepository.findByUsuarioId(usuarioId);

        log.debug("Encontrados {} favoritos para o usuário {}.", entities.size(), usuario.getEmail());

        return entities.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<Filme> listarFilmesFavoritadosPorUsuario(Long usuarioId) {
        log.info("Resolvendo lista de FILMES favoritos para usuário ID: {}", usuarioId);

        List<Favorito> favoritosFilme = favoritoRepository.findFilmeFavoritosByUsuario(usuarioId);

        log.debug("Usuário possui {} filmes favoritados. Iniciando busca de detalhes...", favoritosFilme.size());

        List<Long> filmeIds = favoritosFilme.stream()
                .map(Favorito::getItemId)
                .toList();

        return filmeIds.stream()
                .map(filmeService::buscarPorId)
                .toList();
    }

    public List<Serie> listarSeriesFavoritadasPorUsuario(Long usuarioId) {
        log.info("Resolvendo lista de SERIES favoritas para usuário ID: {}", usuarioId);

        List<Favorito> favoritosSerie = favoritoRepository.findSerieFavoritosByUsuario(usuarioId);

        log.debug("Usuário possui {} séries favoritadas. Iniciando busca de detalhes...", favoritosSerie.size());

        List<Long> serieIds = favoritosSerie.stream()
                .map(Favorito::getItemId)
                .toList();

        return serieIds.stream()
                .map(serieService::buscarPorId)
                .toList();
    }
}