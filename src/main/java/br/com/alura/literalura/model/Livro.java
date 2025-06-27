package br.com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "livro_idiomas",
            joinColumns = @JoinColumn(name = "livro_id")
    )
    @Column(name = "idioma")
    private Set<String> idiomas;

    @Column(name = "numero_downloads")
    private Integer numeroDownloads;

    // Construtores
    public Livro() { }

    public Livro(String titulo, Autor autor, Set<String> idiomas, Integer numeroDownloads) {
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.numeroDownloads = numeroDownloads;
    }

    // Getters e setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Set<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Set<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }
}
