package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.request.FavoritoRequestDTO;
import br.uniesp.si.techback.dto.response.FavoritoResponseDTO;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.model.Usuario;
import br.uniesp.si.techback.repository.FavoritoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioService usuarioService;
    private final FilmeService filmeService;
    private final SerieService serieService;

    // Constantes para o campo itemType
    private static final String TYPE_FILME = "FILME";
    private static final String TYPE_SERIE = "SERIE";

    /**
     * Cria um novo favorito, validando o usuário, a unicidade e a existência do item (Filme ou Série) usando a modelagem Polimórfica.
     */
    public Favorito criar(Favorito favorito) {

        // 1. Validação do Usuário
        Long usuarioId = favorito.getUsuario().getId();
        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioId);
        // Garante que o objeto Usuario na entity está completo
        favorito.setUsuario(usuarioExistente);

        // 2. Validação da Unicidade
        Long itemId = favorito.getItemId();
        String itemType = favorito.getItemType();

        if (favoritoRepository.existsByUsuarioIdAndItemIdAndItemType(usuarioId, itemId, itemType)) {
            throw new RuntimeException("Este item (" + itemType + " ID: " + itemId + ") já está na lista de favoritos do usuário.");
        }

        // 3. Validação e Atribuição do Item Favorito (Polimorfismo)
        if (TYPE_FILME.equalsIgnoreCase(itemType)) {
            // Verifica se o filme existe (se não existir, o service lança uma exceção)
            filmeService.buscarPorId(itemId);
        } else if (TYPE_SERIE.equalsIgnoreCase(itemType)) {
            // Verifica se a série existe (se não existir, o service lança uma exceção)
            serieService.buscarPorId(itemId);
        } else {
            // O tipo de item é inválido
            throw new RuntimeException("Tipo de item inválido. Deve ser 'FILME' ou 'SERIE'.");
        }

        // 4. Salva o favorito
        return favoritoRepository.save(favorito);
    }

    public Favorito criarComDTO(FavoritoRequestDTO dto) {

        // 1. Validação do Usuário (Dependência 1)
        // Lança ResourceNotFoundException se o usuário não existir
        Usuario usuarioExistente = usuarioService.buscarPorId(dto.getUsuarioId());

        // 2. Validação da Unicidade (Baseada na chave composta)
        String itemTypeUpper = dto.getItemType().toUpperCase();

        if (favoritoRepository.existsByUsuarioIdAndItemIdAndItemType(
                dto.getUsuarioId(),
                dto.getItemId(),
                itemTypeUpper)
        ) {
            throw new RuntimeException("Este item já está na lista de favoritos do usuário.");
        }

        // 3. Validação Polimórfica (Checa se o item referenciado existe)
        if (TYPE_FILME.equals(itemTypeUpper)) {
            filmeService.buscarPorId(dto.getItemId());
        } else if (TYPE_SERIE.equals(itemTypeUpper)) {
            serieService.buscarPorId(dto.getItemId());
        } else {
            throw new RuntimeException("Tipo de item inválido. Deve ser 'FILME' ou 'SERIE'.");
        }

        // 4. Mapeamento DTO para Entity (Criação do objeto final)
        Favorito novoFavorito = new Favorito();

        // Atribui o objeto Usuário gerenciado pelo JPA
        novoFavorito.setUsuario(usuarioExistente);

        // Atribui os campos polimórficos
        novoFavorito.setItemId(dto.getItemId());
        novoFavorito.setItemType(itemTypeUpper);

        // 5. Salva a Entity
        return favoritoRepository.save(novoFavorito);
    }

// -------------------------------------------------------------
// Métodos de Listagem e Gerenciamento
// -------------------------------------------------------------

    /**
     * Retorna todos os favoritos.
     * OBS: Ao listar, o Service retorna o Favorito com o ID do Item.
     * O Controller deve ser responsável por buscar o objeto Filme/Série completo se necessário.
     */
    public List<Favorito> listarTodos() {
        return favoritoRepository.findAll();
    }

    /**
     * Busca um favorito pelo ID.
     */
    public Favorito buscarPorId(Long id) {
        return favoritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado com id: " + id));
    }

    /**
     * Deleta um favorito pelo ID.
     */
    public void deletar(Long id) {
        if (!favoritoRepository.existsById(id)) {
            throw new RuntimeException("Favorito não encontrado com id: " + id);
        }
        favoritoRepository.deleteById(id);
    }

    /**
     * Lista todos os favoritos de um usuário.
     */
    public List<Favorito> listarPorUsuario(Long usuarioId) {
        // Valida se o usuário existe antes de buscar a lista
        usuarioService.buscarPorId(usuarioId);
        return favoritoRepository.findByUsuarioId(usuarioId);
    }

    // Em br.uniesp.si.techback.service.FavoritoService.java

    // Adicione este método auxiliar (mapper manual)
    private FavoritoResponseDTO toResponseDTO(Favorito favorito) {
        // Nota: Para preencher 'itemTitulo', você teria que fazer um lookup adicional
        // usando o itemType e itemId, mas aqui simplificaremos o mapeamento dos IDs.

        FavoritoResponseDTO dto = new FavoritoResponseDTO();
        dto.setId(favorito.getId());
        dto.setUsuarioId(favorito.getUsuario().getId());
        dto.setUsuarioEmail(favorito.getUsuario().getEmail());
        dto.setItemId(favorito.getItemId());
        dto.setItemType(favorito.getItemType());

        // dto.setItemTitulo(... lógica para buscar o título ...)

        return dto;
    }


    // NOVO MÉTODO (O que a Controller irá chamar)
    public List<FavoritoResponseDTO> listarPorUsuarioComDTO(Long usuarioId) {
        // 1. Busca o usuário (para validar a existência e ter o objeto)
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        // 2. Busca a lista de Entities
        List<Favorito> entities = favoritoRepository.findByUsuarioId(usuarioId);

        // 3. Converte a lista de Entities para a lista de DTOs
        return entities.stream()
                .map(this::toResponseDTO) // Aplica o mapeamento a cada Entity
                .toList();
    }


    // Em br.uniesp.si.techback.service.FavoritoService.java

// ... (Injete FavoritoRepository, FilmeService e SerieService) ...

    public List<Filme> listarFilmesFavoritadosPorUsuario(Long usuarioId) {
        // 1. Usa a JPQL especializada para buscar APENAS os registros Favorito de FILMES para este usuário
        List<Favorito> favoritosFilme = favoritoRepository.findFilmeFavoritosByUsuario(usuarioId);

        // 2. Mapeia a lista de Favorito (que só tem o ID) para a lista de IDs de Filme
        List<Long> filmeIds = favoritosFilme.stream()
                .map(Favorito::getItemId)
                .toList();

        // 3. Busca os objetos Filme completos (resolve o polimorfismo)
        // OBS: Assume-se que 'filmeService' tem um método 'buscarPorId' que não retorna Optional.
        return filmeIds.stream()
                .map(filmeService::buscarPorId)
                .toList();
    }

    public List<Serie> listarSeriesFavoritadasPorUsuario(Long usuarioId) {
        // 1. Usa a JPQL especializada para buscar APENAS os registros Favorito de SÉRIES para este usuário
        List<Favorito> favoritosSerie = favoritoRepository.findSerieFavoritosByUsuario(usuarioId);

        // 2. Mapeia a lista de Favorito para a lista de IDs de Série
        List<Long> serieIds = favoritosSerie.stream()
                .map(Favorito::getItemId)
                .toList();

        // 3. Busca os objetos Série completos
        return serieIds.stream()
                .map(serieService::buscarPorId)
                .toList();
    }
}