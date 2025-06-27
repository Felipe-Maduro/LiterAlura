package br.com.alura.literalura.dto;

import java.util.List;

public class GutendexResponseDto {
    private List<GutendexBookDto> results;

    // getters e setters
    public List<GutendexBookDto> getResults() { return results; }
    public void setResults(List<GutendexBookDto> results) { this.results = results; }
}
