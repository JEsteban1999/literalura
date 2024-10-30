package com.aluracursos.literalura.model;

public enum Idioma {
    ES("es", "Español"),
    EN("en", "Ingles"),
    PT("pt", "Portugues"),
    FR("fr", "Frances"),
    AL("al", "Aleman"),
    IT("it", "Italiano");

    private String categoriaGutendex;
    private String categoriaCastellano;

    Idioma(String categoriaGutendex, String categoriaCastellano) {
        this.categoriaGutendex = categoriaGutendex;
        this.categoriaCastellano = categoriaCastellano;
    }

    public static Idioma fromString(String text) {
        for (Idioma idioma: Idioma.values()) {
            if (idioma.categoriaGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }

    public static Idioma fromCastellano(String text) {
        for (Idioma idioma: Idioma.values()) {
            if (idioma.categoriaCastellano.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + text);
    }
}
