public class Obra {
    private String id;
    private String titulo;
    private String autor;
    private String quantDisponivel;

    public Obra(String id, String titulo, String autor, String quantDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.quantDisponivel = quantDisponivel;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getQuantDisponivel() {
        return quantDisponivel;
    }

    @Override
    public String toString() {
        return "ID ='" + id + '\'' +
                ", Título ='" + titulo + '\'' +
                ", Autor ='" + autor + '\'' +
                ", Quantidade disponível =" + quantDisponivel;
    }
}
