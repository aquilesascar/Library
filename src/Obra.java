public class Obra {
    private String id;
    private String titulo;
    private int quantDisponivel;

    public Obra(String id, String titulo, int quantDisponivel) {
        this.id = id;
        this.titulo = titulo;
        this.quantDisponivel = quantDisponivel;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getQuantDisponivel() {
        return quantDisponivel;
    }

    @Override
    public String toString() {
        return "ID ='" + id + '\'' +
                ", Título ='" + titulo + '\'' +
                ", Quantidade disponível =" + quantDisponivel;
    }


    public void incrementarQuantidadeDisponivel() {
        quantDisponivel++;
    }
}
