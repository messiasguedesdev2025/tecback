package br.uniesp.si.techback.service;

import br.uniesp.si.techback.dto.response.ViaCepResponse;
import br.uniesp.si.techback.exception.CepInvalidoException;
import br.uniesp.si.techback.exception.CepNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 1. Import do SLF4J
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViaCepService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    private final RestTemplate restTemplate;

    public ViaCepResponse buscarEnderecoPorCep(String cep) {
        log.info("Iniciando consulta externa de endereço para o CEP: '{}'", cep);

        String cepLimpo = cep.replaceAll("[^0-9]", "");

        if (cepLimpo.length() != 8) {
            log.warn("CEP inválido detectado (tamanho incorreto): '{}'", cep);
            throw new CepInvalidoException(cep);
        }

        try {
            long inicio = System.currentTimeMillis();
            log.debug("Enviando requisição GET para ViaCEP (CEP: {})", cepLimpo);

            ViaCepResponse response = restTemplate.getForObject(
                    VIA_CEP_URL,
                    ViaCepResponse.class,
                    cepLimpo
            );

            long tempoTotal = System.currentTimeMillis() - inicio;
            log.debug("Resposta do ViaCEP recebida em {} ms.", tempoTotal);

            if (response == null || response.isErro()) {
                log.warn("ViaCEP informou que o CEP '{}' não existe na base de dados.", cepLimpo);
                throw new CepNaoEncontradoException(cep);
            }

            log.info("Endereço encontrado: {}, {} - {}", response.getLogradouro(), response.getLocalidade(), response.getUf());

            return response;

        } catch (HttpClientErrorException.BadRequest ex) {
            log.error("Erro de Bad Request ao consultar CEP: {}", cep, ex);
            throw new CepInvalidoException(cep);
        } catch (Exception ex) {
            log.error("Falha inesperada na integração com ViaCEP para o CEP {}", cep, ex);
            throw ex;
        }
    }
}