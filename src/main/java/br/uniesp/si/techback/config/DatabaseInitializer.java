package br.uniesp.si.techback.config;

import br.uniesp.si.techback.model.Filme;
import br.uniesp.si.techback.model.Genero;
import br.uniesp.si.techback.model.Serie;
import br.uniesp.si.techback.repository.FilmeRepository;
import br.uniesp.si.techback.repository.GeneroRepository;
import br.uniesp.si.techback.repository.SerieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final GeneroRepository generoRepository;
    private final FilmeRepository filmeRepository;
    private final SerieRepository serieRepository;

    public DatabaseInitializer(GeneroRepository generoRepository, FilmeRepository filmeRepository, SerieRepository serieRepository) {
        this.generoRepository = generoRepository;
        this.filmeRepository = filmeRepository;
        this.serieRepository = serieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. CHECAGEM DE EXISTÊNCIA: Evita a duplicação de dados ao reiniciar a aplicação.
        if (generoRepository.count() > 0) {
            System.out.println("O banco de dados já está populado. Pulando a inicialização de dados.");
            return;
        }

        System.out.println("Iniciando a inicialização do banco de dados com dados de exemplo...");

        // 2. CRIAÇÃO DE GÊNEROS (Armazenados em variáveis para serem reutilizados)
        Genero acao = createAndSaveGenero("Ação");
        Genero comedia = createAndSaveGenero("Comédia");
        Genero drama = createAndSaveGenero("Drama");
        Genero ficcao = createAndSaveGenero("Ficção Científica"); // Adicionado mais um gênero para exemplo

        // 3. CRIAÇÃO DE FILMES
        // Código Refatorado: limpo e direto
        createAndSaveFilme("Filme de Ação 1", "Descrição do filme de ação 1", 2023, acao);
        createAndSaveFilme("Filme de Comédia 1", "Descrição do filme de comédia 1", 2023, comedia);
        createAndSaveFilme("Filme de Ação 2", "Descrição do filme de ação 2", 2022, acao);
        createAndSaveFilme("Filme de Comédia 2", "Descrição do filme de comédia 2", 2021, comedia);
        createAndSaveFilme("Filme de Drama 1", "Descrição do filme de drama 1", 2020, drama);
        createAndSaveFilme("Filme de Drama 2", "Descrição do filme de drama 2", 2019, drama);

        // 4. CRIAÇÃO DE SÉRIES
        createAndSaveSerie("Série de Drama 1", "Descrição da série de drama 1", 3, 30, drama);
        createAndSaveSerie("Série de Ação 1", "Descrição da série de ação 1", 2, 20, acao);
        createAndSaveSerie("Série de Comédia 1", "Descrição da série de comédia 1", 4, 40, comedia);
        createAndSaveSerie("Série de Drama 2", "Descrição da série de drama 2", 5, 50, drama);
        createAndSaveSerie("Série de Ficção", "Descrição da série de ficção 1", 1, 10, ficcao);

        System.out.println("Banco de dados inicializado com sucesso!");
    }

    private Genero createAndSaveGenero(String nome) {
        Genero genero = new Genero();
        genero.setNome(nome);
        return generoRepository.save(genero);
    }


    private void createAndSaveFilme(String titulo, String descricao, Integer anoLancamento, Genero genero) {
        // Código Antigo: repetitivo e verboso

        Filme filme = new Filme();
        filme.setTitulo(titulo);
        filme.setDescricao(descricao);
        filme.setAnoLancamento(anoLancamento);
        filme.setGenero(genero);
        filmeRepository.save(filme);
    }

    private void createAndSaveSerie(String titulo, String descricao, Integer temporadas, Integer totalEpisodios, Genero genero) {
        Serie serie = new Serie();
        serie.setTitulo(titulo);
        serie.setDescricao(descricao);
        serie.setTemporadas(temporadas);
        serie.setTotalEpisodios(totalEpisodios);
        serie.setGenero(genero);
        serieRepository.save(serie);
    }
}