public class Obra {
    private String id;
    private String titulo;
   // private String autor;
    private String quantDisponivel;

    public Obra(String id, String titulo, String quantDisponivel) {
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

    public String getQuantDisponivel() {
        return quantDisponivel;
    }


}
