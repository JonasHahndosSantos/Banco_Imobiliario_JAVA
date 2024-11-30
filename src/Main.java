import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
            Jogo jogo = new Jogo();
            Scanner scanner = new Scanner(System.in);


            boolean continuar = true;
            while (continuar) {
                System.out.println("\nMenu do Jogo:");
                System.out.println("1. Cadastrar imóvel");
                System.out.println("2. Listar imóveis");
                System.out.println("3. Atualizar imóvel");
                System.out.println("4. Remover um imóvel");
                System.out.println("5. Cadastrar jogador");
                System.out.println("6. Listar jogadores");
                System.out.println("7. Atualizar jogador");
                System.out.println("8. Remover um jogador");
                System.out.println("9. Configurar jogo");
                System.out.println("10. Iniciar jogo");
                System.out.println("0. Sair");

                System.out.print("Escolha uma opção: ");
                int opcao = scanner.nextInt();

                switch (opcao) {
                    case 1:
                        jogo.adicionarImoveisPadrao();
                        jogo.cadastrarImovel();
                        break;
                    case 2:
                        jogo.listarImoveis();
                        break;
                    case 3:
                        jogo.atualizarImovelPorNome();
                        break;
                    case 4:
                            jogo.removerImovelPorNome();
                        break;
                    case 5:
                        jogo.cadastrarJogador();
                        break;
                    case 6:
                        jogo.listarJogadores();
                        break;
                    case 7:
                        jogo.atualizarJogadorPorNome();
                        break;
                    case 8:
                        jogo.removerJogadorPorNome();
                        break;
                    case 9:
                        jogo.configurarJogo();
                        break;
                    case 10:
                        jogo.iniciarJogo();
                        if (jogo.validarInicioJogo()) { // Verifica novamente os critérios antes de iniciar
                            for (int i = 0; i < jogo.rodadasMaximas; i++) {
                                System.out.println("\nRodada " + (i + 1));
                                jogo.jogarRodada(); // Executa a lógica de uma rodada
                            }
                            continuar = false; // Encerra o jogo após todas as rodadas
                        }
                        break;
                    case 0:
                        continuar = false;
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            }

            System.out.println("Obrigado por jogar!");


    }
}