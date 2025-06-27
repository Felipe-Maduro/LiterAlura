package br.com.alura.literalura.ui;

import br.com.alura.literalura.model.Autor;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class ConsoleMenu {

    @Autowired
    private LivroService livroService;

    private final Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("\n=== LiterAlura ===");
            System.out.println("1) Buscar livro por título (API + BD)");
            System.out.println("2) Listar livros registrados");
            System.out.println("3) Listar autores registrados");
            System.out.println("4) Listar autores vivos em determinado ano");
            System.out.println("5) Listar livros em determinado idioma");
            System.out.println("6) Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // consome o \n

            switch (opcao) {
                case 1 -> {
                    System.out.print("Título a buscar: ");
                    String titulo = scanner.nextLine();
                    Livro livro = livroService.salvarPorTitulo(titulo);
                    if (livro != null) {
                        imprimirDetalhes(livro, "API");
                    } else {
                        System.out.println("→ Nenhum resultado na API, buscando no banco...");
                        livro = livroService.buscarPorTituloLocal(titulo);
                        if (livro != null) {
                            imprimirDetalhes(livro, "BD");
                        } else {
                            System.out.println("✘ Nenhum livro encontrado para: " + titulo);
                        }
                    }
                }
                case 2 -> {
                    List<Livro> livros = livroService.listarLivros();
                    System.out.println("== Livros registrados ==");
                    livros.forEach(l ->
                            System.out.printf("• [%d] %s (Autor: %s)%n",
                                    l.getId(),
                                    l.getTitulo(),
                                    l.getAutor().getNome())
                    );
                }
                case 3 -> {
                    List<Autor> autores = livroService.listarAutores();
                    System.out.println("== Autores registrados ==");
                    autores.forEach(a ->
                            System.out.printf("• [%d] %s (%d–%s)%n",
                                    a.getId(),
                                    a.getNome(),
                                    a.getAnoNascimento(),
                                    a.getAnoFalecimento() == null ? "∅" : a.getAnoFalecimento())
                    );
                }
                case 4 -> {
                    System.out.print("Ano de referência: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();
                    List<Autor> vivos = livroService.listarAutoresVivos(ano);
                    System.out.println("== Autores vivos em " + ano + " ==");
                    vivos.forEach(a ->
                            System.out.printf("• %s (%d–%s)%n",
                                    a.getNome(),
                                    a.getAnoNascimento(),
                                    a.getAnoFalecimento() == null ? "∅" : a.getAnoFalecimento())
                    );
                }
                case 5 -> {
                    System.out.print("Idioma (ex: en, pt): ");
                    String idioma = scanner.nextLine();
                    List<Livro> porIdioma = livroService.listarLivrosPorIdioma(idioma);
                    if (porIdioma.isEmpty()) {
                        System.out.println("✘ Nenhum livro encontrado para o idioma: " + idioma);
                    } else {
                        System.out.println("== Livros no idioma '" + idioma + "' ==");
                        porIdioma.forEach(l ->
                                System.out.printf("• [%d] %s (Idiomas: %s)%n",
                                        l.getId(),
                                        l.getTitulo(),
                                        l.getIdiomas())
                        );
                    }
                }
                case 6 -> {
                    System.out.println("Saindo…");
                    return;
                }
                default -> System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private void imprimirDetalhes(Livro livro, String origem) {
        System.out.printf("%n====== Livro (via %s) ======%n", origem);
        System.out.println("Título:               " + livro.getTitulo());
        System.out.println("Autor:                " + livro.getAutor().getNome());
        System.out.println("Idiomas:              " + livro.getIdiomas());
        System.out.println("Número de downloads:  " + livro.getNumeroDownloads());
    }
}
