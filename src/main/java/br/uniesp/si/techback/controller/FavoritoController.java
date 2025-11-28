package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.response.FavoritoResponseDTO;
import br.uniesp.si.techback.model.Favorito;
import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.service.FavoritoService;
import br.uniesp.si.techback.dto.request.FavoritoRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@RequiredArgsConstructor
@Slf4j
public class FavoritoController {

    private final FavoritoService favoritoService;

    // Em FavoritoController.java

    @PostMapping // Endpoint para testar o método sem DTO
    public ResponseEntity<Favorito> criar(@Valid @RequestBody Favorito favorito) {

        Favorito novoFavorito = favoritoService.criar(favorito);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoFavorito);
    }
    // POST: Adiciona um item (Filme OU Série) aos favoritos
    @PostMapping("/criar-com-dto")
    // MUDANÇA: Agora recebe o DTO de Requisição e não a Entity completa.
    public ResponseEntity<FavoritoResponseDTO> criarComDTO(@Valid @RequestBody FavoritoRequestDTO dto) {

        // A lógica de criação foi transferida para aceitar o DTO no Service,
        // garantindo que a validação de tipo de item seja feita.
        Favorito novoFavorito = favoritoService.criarComDTO(dto);
        // Agora que a service respondeu, vou transformar novoFavorito numa forma DTO

        FavoritoResponseDTO favoritoDTO = new FavoritoResponseDTO();
        favoritoDTO.setId(novoFavorito.getId());
        favoritoDTO.setItemId(novoFavorito.getItemId());
        favoritoDTO.setItemType(novoFavorito.getItemType());
        favoritoDTO.setUsuarioId(novoFavorito.getUsuario().getId());
        favoritoDTO.setUsuarioEmail(novoFavorito.getUsuario().getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoDTO);
    }

    // GET: Lista todos os favoritos (Pode ser refinado para listar por usuário)
    // Esses métodos continuam retornando a Entity, pois a serialização resolve a FK.
    @GetMapping
    public ResponseEntity<List<Favorito>> listarTodos() {
        return ResponseEntity.ok(favoritoService.listarTodos());
    }

    // GET: Busca um favorito pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Favorito> buscarPorId(@PathVariable Long id) {
        Favorito favorito = favoritoService.buscarPorId(id);
        return ResponseEntity.ok(favorito);
    }

    // GET: Lista favoritos de um usuário específico (Exemplo de endpoint mais útil)
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Favorito> favoritos = favoritoService.listarPorUsuario(usuarioId);

        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/usuarioDTO/{usuarioId}")
//  MUDANÇA: Retorna a lista de DTOs
    public ResponseEntity<List<FavoritoResponseDTO>> listarPorUsuarioDTO(@PathVariable Long usuarioId) {

        // 🚨 MUDANÇA: Chama o método que retorna a lista de DTOs
        List<FavoritoResponseDTO> favoritosDTO = favoritoService.listarPorUsuarioComDTO(usuarioId);

        return ResponseEntity.ok(favoritosDTO);
    }


    // DELETE: Remove um item dos favoritos
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        favoritoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Em br.uniesp.si.techback.controller.FavoritoController.java

// ... (Injete FavoritoService) ...

    @GetMapping("/usuario/{usuarioId}/filmes")
    public ResponseEntity<List<Filme>> listarFilmesFavoritosPorUsuario(@PathVariable Long usuarioId) {
        log.info("Recebida requisição GET para listar filmes favoritos do Usuário ID: {}", usuarioId);
        List<Filme> filmes = favoritoService.listarFilmesFavoritadosPorUsuario(usuarioId);
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/usuario/{usuarioId}/series")
    public ResponseEntity<List<Serie>> listarSeriesFavoritasPorUsuario(@PathVariable Long usuarioId) {
        log.info("Recebida requisição GET para listar séries favoritas do Usuário ID: {}", usuarioId);
        List<Serie> series = favoritoService.listarSeriesFavoritadasPorUsuario(usuarioId);
        return ResponseEntity.ok(series);
    }
}