package br.com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GutendexBookDto {
    private Long id;
    private String title;

    @JsonProperty("authors")
    private List<GutendexAuthorDto> autores;

    @JsonProperty("languages")
    private List<String> idiomas;

    @JsonProperty("download_count")
    private Integer downloadCount;

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<GutendexAuthorDto> getAutores() { return autores; }
    public void setAutores(List<GutendexAuthorDto> autores) { this.autores = autores; }
    public List<String> getIdiomas() { return idiomas; }
    public void setIdiomas(List<String> idiomas) { this.idiomas = idiomas; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }
}
