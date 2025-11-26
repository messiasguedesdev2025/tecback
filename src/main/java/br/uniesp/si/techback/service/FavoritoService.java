package br.uniesp.si.techback.service;

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

    /**
     * Cria um novo favorito, validando dependências e a regra: um Favorito deve ter APENAS um Filme OU uma Série.
     */
    public Favorito criar(Favorito favorito) {

        // 1. Validação do Usuário
        Long usuarioId = favorito.getUsuario().getId();
        Usuario usuarioExistente = usuarioService.buscarPorId(usuarioId);
        favorito.setUsuario(usuarioExistente);

        // 2. Validação da Regra Exclusiva (Filme OU Série)
        boolean hasFilme = favorito.getFilme() != null && favorito.getFilme().getId() != null;
        boolean hasSerie = favorito.getSerie() != null && favorito.getSerie().getId() != null;

        if (hasFilme && hasSerie) {
            throw new RuntimeException("Um favorito deve ser ligado a um Filme OU a uma Série, não a ambos.");
        }
        if (!hasFilme && !hasSerie) {
            throw new RuntimeException("Um favorito deve ser ligado a um Filme ou a uma Série.");
        }

        // 3. Validação e Atribuição do Item Favorito
        if (hasFilme) {
            Long filmeId = favorito.getFilme().getId();
            // Verifica se já existe
            if (favoritoRepository.existsByUsuarioIdAndFilmeId(usuarioId, filmeId)) {
                throw new RuntimeException("Este filme já está na lista de favoritos do usuário.");
            }
            Filme filmeExistente = filmeService.buscarPorId(filmeId);
            favorito.setFilme(filmeExistente);
        } else { // Deve ser Série
            Long serieId = favorito.getSerie().getId();
            // Verifica se já existe
            if (favoritoRepository.existsByUsuarioIdAndSerieId(usuarioId, serieId)) {
                throw new RuntimeException("Esta série já está na lista de favoritos do usuário.");
            }
            Serie serieExistente = serieService.buscarPorId(serieId);
            favorito.setSerie(serieExistente);
        }

        return favoritoRepository.save(favorito);
    }

    /**
     * Retorna todos os favoritos.
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

    // Método adicional para buscar favoritos de um usuário (opcional)
    public List<Favorito> listarPorUsuario(Long usuarioId) {
        return favoritoRepository.findByUsuarioId(usuarioId);
    }
}