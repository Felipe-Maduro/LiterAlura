package br.com.alura.literalura.service;

import br.com.alura.literalura.dto.GutendexAuthorDto;
import br.com.alura.literalura.dto.GutendexBookDto;
import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.repository.AutorRepository;
import br.com.alura.literalura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private GutendexClient gutendexClient;

    @Autowired
    private AutorRepository autorRepo;

    @Autowired
    private LivroRepository livroRepo;

    @Transactional
    public Livro salvarPorTitulo(String tituloBuscado) {
        // 1.1) Busca local
        Optional<Livro> existente = livroRepo
                .findFirstByTituloContainingIgnoreCase(tituloBuscado);
        if (existente.isPresent()) {
            return existente.get();
        }
        // 1.2) Busca na API
        Optional<GutendexBookDto> dtoOpt = gutendexClient.buscarPrimeiroPorTitulo(tituloBuscado);
        if (dtoOpt.isEmpty()) {
            return null;
        }
        GutendexBookDto dto = dtoOpt.get();

        // 1.3) Mapeia e salva Autor
        GutendexAuthorDto aDto = dto.getAutores().get(0);
        Autor autor = new Autor(
                aDto.getName(),
                aDto.getBirthYear(),
                aDto.getDeathYear()
        );
        autor = autorRepo.save(autor);

        // 1.4) Mapeia e salva Livro
        Livro livro = new Livro(
                dto.getTitle(),
                autor,
                Set.copyOf(dto.getIdiomas()),
                dto.getDownloadCount()
        );
        return livroRepo.save(livro);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarLivros() {
        // Usa um map por título (ignorando case) para remover duplicatas
        return new ArrayList<>(
                livroRepo.findAll().stream()
                        .collect(Collectors.toMap(
                                l -> l.getTitulo().toLowerCase(),
                                Function.identity(),
                                (l1, l2) -> l1
                        )).values()
        );
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutores() {
        return new ArrayList<>(
                autorRepo.findAll().stream()
                        .collect(Collectors.toMap(
                                a -> a.getNome().toLowerCase(),
                                Function.identity(),
                                (a1, a2) -> a1
                        )).values()
        );
    }

    @Transactional(readOnly = true)
    public List<Autor> listarAutoresVivos(int ano) {
        return listarAutores().stream()
                .filter(a -> a.getAnoNascimento() != null && a.getAnoNascimento() <= ano)
                .filter(a -> a.getAnoFalecimento() == null || a.getAnoFalecimento() >= ano)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return new ArrayList<>(
                livroRepo.findAll().stream()
                        .filter(l -> l.getIdiomas().contains(idioma))
                        .collect(Collectors.toMap(
                                l -> l.getTitulo().toLowerCase(),
                                Function.identity(),
                                (l1, l2) -> l1
                        )).values()
        );
    }

    @Transactional(readOnly = true)
    public Livro buscarPorTituloLocal(String tituloParcial) {
        return livroRepo
                .findFirstByTituloContainingIgnoreCase(tituloParcial)
                .orElse(null);
    }
}
