package br.uniesp.si.techback.config;


import br.uniesp.si.techback.repository.GeneroRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final GeneroRepository generoRepository;

    public DatabaseInitializer(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Se este log for executado e o count for > 0, significa que o data.sql funcionou.
        if (generoRepository.count() > 0) {
            System.out.println("✅ Banco de dados populado com sucesso pelo data.sql!");
        } else {
            System.out.println("❌ Erro: O banco de dados continua vazio. Verifique o data.sql.");
        }
    }
}