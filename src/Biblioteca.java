import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Usuario> usuarios = new ArrayList();
    private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Emprestimo> emprestimos = new ArrayList();

    public ArrayList<Emprestimo> getEmprestimos() {
        return emprestimos;
    }
   public Biblioteca( ){
        this.usuarios.add(new Bibliotecario("Seu José", "seujose2000@gmail.com", "Zezinho", "31996659292",0));
   }

    public void consultarObras() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("Deseja buscar a obra por:");
            System.out.println("1 - ID");
            System.out.println("2 - Nome");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine();
            Obra obra=null;
            switch (opcao) {
                case 1:
                    System.out.print("Digite o ID da obra: ");
                    int idBusca = sc.nextInt();
                    obra = buscaId(idBusca);
                    if(obra!=null) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                    }else {
                        System.out.println("Obra com ID " + idBusca + " não encontrada.");
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome (título) da obra: ");
                    String tituloBusca = sc.nextLine();
                    obra= buscaTitulo(tituloBusca);
                    if(obra!=null) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                    }else {

                        System.out.println("Obra com título \"" + tituloBusca + "\" não encontrada.");
                    }
                    break;

                default:
                    System.err.println("Opção inválida.Tente novamente");
            }
        }
    }

    private Obra buscaTitulo(String tituloBusca) {
        for (Obra obra : obras) {
            if (obra.getTitulo().equalsIgnoreCase(tituloBusca)) {
                 return obra;
            }
        }
        return null;
    }

    private Obra buscaId(int idBusca) {
        for (Obra obra : obras) {
            if (obra.getId() == idBusca) {
                return obra;
            }
        }
        return null;
    }



    public ArrayList<Obra> getObras() {
        return obras;
    }

    private boolean podeEmprestarLivro(Usuario usuario){
        for (Emprestimo emprestimo : emprestimos) {
            if(emprestimo.getUsuario().equals(usuario)){
                if(emprestimo.isAtrasado()){
                    return false;
                }
            }

        }
        return true;
    }
    private Usuario buscaUsuario(String nome) {
        for (Usuario usuario : usuarios) {
            if(usuario.equals(nome)){
                return usuario;
            }
        }
        return null;
    }

    public boolean realizarDevolucao(String nome,String titulo){
        Obra obra = buscaTitulo(titulo);
        Usuario usuario = buscaUsuario(nome);
        if(obra!=null && usuario!=null){
            for (int i=0; i<emprestimos.size(); i++) {
                if(emprestimos.get(i).getUsuario().equals(usuario)){
                    if(emprestimos.get(i).getObra().equals(obra)){
                        obra.incrementarQuantidadeDisponivel();
                        emprestimos.remove(i);
                        return true;
                    }
                }
            }
        }
        return false;

    }

    public boolean realizarEmprestimo(Usuario usuario, String titulo) {
        if(podeEmprestarLivro(usuario)) {
            Obra obra = buscaTitulo(titulo);
            if (usuario.verificarLimiteEmprestimo() && obra != null && obra.getQuantDisponivel() > 0) {
                usuario.decrementarLimiteEmprestimo();
                obra.decrementarQuantidadeDisponivel();
                emprestimos.add(new Emprestimo(usuario, obra, LocalDate.now()));
                return true;
            }
            return false;
        }
        return false;
    }


    public void carregarDados(String arquivo,String obra) {
        // Carregar dados de obras a partir do arquivo CSV

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha = reader.readLine();
            linha=reader.readLine();
            while (linha != null) {
                String pedacosLinha[] = linha.split(",");
                if(pedacosLinha.length>=3) {
                    int id = Integer.parseInt(pedacosLinha[0]);
                    String nome = pedacosLinha[1];
                    int quantidade = Integer.parseInt(pedacosLinha[2]);
                    //String id, String titulo, String autor, String quantDisponivel
                    obras.add(new Obra(id, nome, quantidade));
                    System.out.println("Adicionando Obra: " + id + ", " + nome + ", " + quantidade); // Mensagem de depuração
                }
                linha = reader.readLine();
            }
        } catch (FileNotFoundException erro) {
            System.out.println("Caminho do arquivo incorreto");
        } catch (IOException erroLeitura) {
            System.out.println("Erro na leitura dos dados");
        }

    }
    //mas somente o bibliotecário pode cadastrar novos usuários, estou na classe certa? não sei, perguntar para a Estella!
    public void cadastraUsuario() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o nome do usuário: ");
        String nome = sc.nextLine();
        System.out.println("Digite o email do usuário: ");
        String email = sc.nextLine();

        // Verifica se o email já está cadastrado
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equalsIgnoreCase(email)) {
                System.err.println("Erro: Email já cadastrado.");
                return;
            }
        }

        System.out.println("Digite a senha do usuário: ");
        String senha = sc.nextLine();
        System.out.println("Selecione o tipo de usuário:");
        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        int tipoUsuario = sc.nextInt();

        Usuario novoUsuario = null;
        switch (tipoUsuario) {
            case 1:
                System.out.println("Digite o número de matrícula do aluno: ");
                int matricula = sc.nextInt();
                System.out.println("Digite o curso do aluno: ");
                sc.nextLine();
                String curso = sc.nextLine();

                novoUsuario = new Aluno(nome, email, senha, matricula, curso, 2); //limite de empréstimos para alunos é 2
                break;

            case 2:
                System.out.println("Digite o departamento do professor: ");
                String departamento = sc.nextLine();

                novoUsuario = new Professor(nome, email, senha, departamento);
                break;

            default:
                System.err.println("Opção inválida.");
                return;
        }

        // Adiciona o novo usuário à lista
        if (novoUsuario != null) {
            usuarios.add(novoUsuario);
            System.out.println("Usuário cadastrado com sucesso!");
        }else{
            System.err.println("Usuário já cadastrada!");
        }
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}