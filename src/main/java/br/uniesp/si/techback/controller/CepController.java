package br.uniesp.si.techback.controller;

import br.uniesp.si.techback.dto.response.ViaCepResponse;
import br.uniesp.si.techback.service.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Importação necessária para o LOGGER

@RestController
@RequestMapping("/api/cep")
@RequiredArgsConstructor
public class CepController {


    private static final Logger LOGGER = LoggerFactory.getLogger(CepController.class);

    private final ViaCepService viaCepService;

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepResponse> consultarCep(@PathVariable String cep) {


        LOGGER.info("Recebida requisição GET para consultar o CEP: {}", cep);

        ViaCepResponse response = viaCepService.buscarEnderecoPorCep(cep);


        LOGGER.debug("Resposta da consulta do CEP {} recebida: {}", cep, response);


        LOGGER.info("Consulta do CEP {} finalizada com sucesso.", cep);

        return ResponseEntity.ok(response);
    }
}