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



    @PostMapping // Endpoint para testar o método sem DTO
    public ResponseEntity<Favorito> criar(@Valid @RequestBody Favorito favorito) {

        log.info("Recebida requisição POST para criar favorito (usando Entity).");

        log.debug("Dados de Favorito recebidos: {}", favorito);

        Favorito novoFavorito = favoritoService.criar(favorito);


        log.info("Novo Favorito criado com sucesso. ID: {}", novoFavorito.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novoFavorito);
    }

    @PostMapping("/criar-com-dto")

    public ResponseEntity<FavoritoResponseDTO> criarComDTO(@Valid @RequestBody FavoritoRequestDTO dto) {

        log.info("Recebida requisição POST para criar favorito usando DTO.");

        log.debug("DTO de Favorito recebido: {}", dto);


        Favorito novoFavorito = favoritoService.criarComDTO(dto);


        FavoritoResponseDTO favoritoDTO = new FavoritoResponseDTO();
        favoritoDTO.setId(novoFavorito.getId());
        favoritoDTO.setItemId(novoFavorito.getItemId());
        favoritoDTO.setItemType(novoFavorito.getItemType());
        favoritoDTO.setUsuarioId(novoFavorito.getUsuario().getId());
        favoritoDTO.setUsuarioEmail(novoFavorito.getUsuario().getEmail());


        log.info("Novo Favorito (via DTO) criado com sucesso. ID: {}", novoFavorito.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoDTO);
    }


    @GetMapping
    public ResponseEntity<List<Favorito>> listarTodos() {

        log.info("Recebida requisição GET para listar todos os favoritos.");

        List<Favorito> favoritos = favoritoService.listarTodos();


        log.debug("Encontrados {} favoritos no total.", favoritos.size());


        log.info("Listagem de todos os favoritos finalizada.");
        return ResponseEntity.ok(favoritoService.listarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Favorito> buscarPorId(@PathVariable Long id) {

        log.info("Recebida requisição GET para buscar favorito pelo ID: {}", id);

        Favorito favorito = favoritoService.buscarPorId(id);


        log.info("Busca de favorito ID {} finalizada com sucesso.", id);
        return ResponseEntity.ok(favorito);
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Favorito>> listarPorUsuario(@PathVariable Long usuarioId) {
        log.info("Recebida requisição GET para listar favoritos do Usuário ID: {}", usuarioId);

        List<Favorito> favoritos = favoritoService.listarPorUsuario(usuarioId);


        log.debug("Encontrados {} favoritos para o Usuário ID: {}", favoritos.size(), usuarioId);


        log.info("Listagem de favoritos para o Usuário ID {} finalizada.", usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    @GetMapping("/usuarioDTO/{usuarioId}")

    public ResponseEntity<List<FavoritoResponseDTO>> listarPorUsuarioDTO(@PathVariable Long usuarioId) {

        log.info("Recebida requisição GET para listar favoritos (retornando DTOs) do Usuário ID: {}", usuarioId);


        List<FavoritoResponseDTO> favoritosDTO = favoritoService.listarPorUsuarioComDTO(usuarioId);


        log.debug("Encontrados {} DTOs de favoritos para o Usuário ID: {}", favoritosDTO.size(), usuarioId);


        log.info("Listagem de DTOs de favoritos para o Usuário ID {} finalizada.", usuarioId);
        return ResponseEntity.ok(favoritosDTO);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {

        log.warn("Recebida requisição DELETE para remover o favorito ID: {}", id);

        favoritoService.deletar(id);


        log.info("Favorito ID: {} deletado com sucesso.", id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/usuario/{usuarioId}/filmes")
    public ResponseEntity<List<Filme>> listarFilmesFavoritosPorUsuario(@PathVariable Long usuarioId) {

        log.info("Recebida requisição GET para listar filmes favoritos do Usuário ID: {}", usuarioId);

        List<Filme> filmes = favoritoService.listarFilmesFavoritadosPorUsuario(usuarioId);


        log.debug("Encontrados {} filmes favoritos para o Usuário ID: {}", filmes.size(), usuarioId);


        log.info("Listagem de filmes favoritos para o Usuário ID {} finalizada.", usuarioId);
        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/usuario/{usuarioId}/series")
    public ResponseEntity<List<Serie>> listarSeriesFavoritasPorUsuario(@PathVariable Long usuarioId) {

        log.info("Recebida requisição GET para listar séries favoritas do Usuário ID: {}", usuarioId);

        List<Serie> series = favoritoService.listarSeriesFavoritadasPorUsuario(usuarioId);


        log.debug("Encontradas {} séries favoritas para o Usuário ID: {}", series.size(), usuarioId);


        log.info("Listagem de séries favoritas para o Usuário ID {} finalizada.", usuarioId);
        return ResponseEntity.ok(series);
    }
}