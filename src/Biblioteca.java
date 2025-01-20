import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Usuario> usuarios = new ArrayList();
    private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Emprestimo> emprestimos = new ArrayList();
    private ArrayList<Usuario> usuarioLogado;

    public ArrayList<Obra> getObras(int i) {
        return obras;
    }

    public Biblioteca() {
        this.usuarios = new ArrayList();
        this.obras = new ArrayList();
        this.emprestimos = new ArrayList();
        this.usuarioLogado = new ArrayList();
    }
    public void printObras(int i) {
        System.out.println(this.obras.get(i).getId()+" "+ this.obras.get(i).getTitulo());
    }

    // Método para fazer empréstimo
    public void emprestimo(String nomeUsuario, ArrayList<Usuario> usuarios) {
        //percore o array de usuários para encontrar o usuário que deseja fazer o empréstimo
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario)) {
                if (usuario.verificarLimiteEmprestimo()) {
                    usuario.decrementarLimiteEmprestimo();
                    System.out.println("Empréstimo realizado com sucesso para " + nomeUsuario + "!");
                } else {
                    System.out.println("Você atingiu o limite de empréstimos, " + nomeUsuario + ".");
                }
                return;
            }
        }
        //caso o usuário não seja encontrado no array
        System.out.println("Usuário " + nomeUsuario + " não encontrado.");
    }


    // Método para devolução
    public void devolucao(String nomeUsuario, String tituloObra) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNome().equals(nomeUsuario)) {
                for (Obra obra : obras) {
                    if (obra.getTitulo().equals(tituloObra)) {
                        usuario.incrementarLimiteEmprestimo();
                        obra.incrementarQuantidadeDisponivel();

                        System.out.println("Devolução realizada com sucesso para " + nomeUsuario + "!");
                        return;
                    }
                }
                System.out.println("Obra não encontrada no sistema: " + tituloObra);
                return;
            }
        }
        System.out.println("Usuário " + nomeUsuario + " não encontrado.");
    }


    public void menuInicial() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("BIBLIOTECA MUNICIPAL DE OURO BRANCO");
            System.out.println("1. Login");
            System.out.println("2. Sair");
            System.out.println("Escolha uma opção: ");
            int opcao = sc.nextInt();
            switch (opcao) {
                case 1:

                    break;

                case 2:

                    break;

                default:
                    System.err.println("Opção inválida!");

            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Digite seu email: ");
        String email = scanner.next();
        System.out.println("Digite sua senha: ");
        String senha = scanner.next();

        for (int i =0; i < usuarios.size(); i++) {

            Usuario u = usuarios.get(i); //pegue o usuário do ArrayList
            if (u.getEmail().equals(email) && u.getSenha().equals(senha) ) { //verifique email e senha

                System.out.println("Bem-vindo, " + u.getNome() + "!");
                usuarios.get(i).menu(u);
                return;
            }
        }
        System.out.println("Email ou senha inválidos. Tente novamente.");

    }


    public void carregarDados() {
        // Carregar dados de obras a partir do arquivo CSV
        String arquivo = "acervo.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                int id=Integer.parseInt(pedacosLinha[0]);
                String nome=pedacosLinha[1];
                int quantidade = Integer.parseInt(pedacosLinha[2]);
                //String id, String titulo, String autor, String quantDisponivel
                obras.add(new Obra(id,nome,quantidade));
            }
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }
    }

    public void cadastraUsuario(){

    }

}


