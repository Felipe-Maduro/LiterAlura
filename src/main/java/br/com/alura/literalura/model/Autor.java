package br.com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "ano_nascimento")
    private Integer anoNascimento;

    @Column(name = "ano_falecimento")
    private Integer anoFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros;

    // Construtores
    public Autor() { }

    public Autor(String nome, Integer anoNascimento, Integer anoFalecimento) {
        this.nome = nome;
        this.anoNascimento = anoNascimento;
        this.anoFalecimento = anoFalecimento;
    }

    // Getters e setters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Integer getAnoNascimento() { return anoNascimento; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }
    public Integer getAnoFalecimento() { return anoFalecimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }
    public List<Livro> getLivros() { return livros; }
    public void setLivros(List<Livro> livros) { this.livros = livros; }
}
