import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Biblioteca {
    private ArrayList<Usuario> usuarios = new ArrayList();
    private ArrayList<Obra> obras = new ArrayList();
    private ArrayList<Emprestimo> emprestimos = new ArrayList();
    private ArrayList<Usuario> usuarioLogado;

    public void consultarObras() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Deseja buscar a obra por:");
        System.out.println("1 - ID");
        System.out.println("2 - Nome");
        System.out.print("Escolha uma opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1:
                System.out.print("Digite o ID da obra: ");
                int idBusca = sc.nextInt();

                for (Obra obra : obras) {
                    if (obra.getId() == idBusca) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                        return;
                    }
                }
                System.out.println("Obra com ID " + idBusca + " não encontrada.");
                break;

            case 2:
                System.out.print("Digite o nome (título) da obra: ");
                String tituloBusca = sc.nextLine();

                for (Obra obra : obras) {
                    if (obra.getTitulo().equalsIgnoreCase(tituloBusca)) {
                        System.out.println("Obra encontrada:");
                        System.out.println(obra);
                        return;
                    }
                }
                System.out.println("Obra com título \"" + tituloBusca + "\" não encontrada.");
                break;

            default:
                System.err.println("Opção inválida.");
        }
    }


    public ArrayList<Obra> getObras() {
        return obras;
    }

    public void printObras(int i) {
        if(obras.get(i) != null) {
            System.out.println(this.obras.get(i).getId() + " " + this.obras.get(i).getTitulo());
        }
    }

    public boolean realizarEmprestimo(Usuario usuario, Obra obra) {
        if (usuario.verificarLimiteEmprestimo() && obra.getQuantDisponivel() > 0) {
            usuario.decrementarLimiteEmprestimo();
            obra.decrementarQuantidadeDisponivel();
            emprestimos.add(new Emprestimo(usuario, obra, LocalDate.now().toString(), null));
            return true;
        }
        return false;
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
                    System.out.println("1. Login Aluno");
                    System.out.println("2. Login Professor");
                    System.out.println("Escolha uma opção: ");
                    int tipoLogin = sc.nextInt();

                    switch (tipoLogin) {
                        case 1:
                            System.out.println("Login como Aluno selecionado.");
                            //chama método de login para Aluno
                            loginAluno(sc);
                            break;
                        case 2:
                            System.out.println("Login como Professor selecionado.");
                            //chamaa método de login para Professor
                            loginProfessor(sc);
                            break;
                        default:
                            System.err.println("Opção de login inválida!");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Saindo do sistema...");
                    return; // Encerra o programa
                default:
                    System.err.println("Opção inválida!");
                    break;
            }
        }
    }

    private void loginAluno(Scanner sc) {
        System.out.println("Digite seu email: ");
        String email = sc.next();
        System.out.println("Digite sua senha: ");
        String senha = sc.next();

        System.out.println("Bem-vindo, Aluno!");
    }

    private void loginProfessor(Scanner sc) {
        System.out.println("Digite seu email: ");
        String email = sc.next();
        System.out.println("Digite sua senha: ");
        String senha = sc.next();
        //não sei, mas ainda tem uma lógica por trás disso, tem que fazer mais coisa
        System.out.println("Bem-vindo, Professor!");
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
        String arquivo = "src/acervo.csv";

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
    public void cadastraUsuario(ArrayList<Usuario> usuarios) {
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