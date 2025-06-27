package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.GutendexBookDto;
import br.com.alura.literalura.dto.GutendexResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class GutendexClient {

    private static final String API = "https://gutendex.com/books/?search=";

    @Autowired
    private RestTemplate restTemplate;

    public Optional<GutendexBookDto> buscarPrimeiroPorTitulo(String titulo) {
        String url = API + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
        GutendexResponseDto resp = restTemplate.getForObject(url, GutendexResponseDto.class);
        if (resp != null && !resp.getResults().isEmpty()) {
            return Optional.of(resp.getResults().get(0));
        }
        return Optional.empty();
    }
}
