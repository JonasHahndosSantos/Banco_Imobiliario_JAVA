import java.util.ArrayList;
import java.util.Scanner;

public class Jogo {
    public Tabuleiro tabuleiro;
    private final ArrayList<Jogador> jogadores;
    private int saldo;
    private int salario;
    int rodadasMaximas;

    public Jogo() {
        this.tabuleiro = new Tabuleiro();
        this.jogadores = new ArrayList<>();
    }
    private static class No {
        Jogo jogo;
        No proximo;

        No(Jogo jogo) {
            this.jogo = jogo;
            this.proximo = null;
        }
    }

    public void adicionarImoveisPadrao() {
        String[] tipo = {
                "Inicial", "Imovel", "Imovel", "Imovel", "CasaImposto", "Imovel","Imovel","Imovel","Imovel", "Retribuicao", "Imovel","Imovel","Imovel"
        };
        String[] nomes = {
                "Inicial", "Casa Verde", "Casa Azul", "Casa Vermelha", "CasaImposto", "Casa Amarela",
                "Loja Central", "Loja Norte", "Loja Sul", "Retribuição",  "Loja Leste",
                "Fazenda Feliz", "Chácara do Sol"
        };
        int[] valores = {0, 20000, 25000, 30000, 0,  35000, 40000, 45000, 50000, 0, 55000, 60000, 70000};
        int[] alugueis = {0, 200, 250, 300, 0, 350, 400, 450, 500, 0, 550, 600, 700};

        for (int i = 0; i < nomes.length; i++) {
            Casa casa = new Casa(tipo[i], nomes[i], valores[i], alugueis[i]);
            tabuleiro.adicionarCasa(casa);
            System.out.println(tipo[i] + " " + nomes[i] + " adicionado ao tabuleiro.");
        }
    }


    public void cadastrarImovel() {
        if (tabuleiro.getTamanho() >= 40) {
            System.out.println("Não é possível cadastrar mais imóveis. O limite máximo de 40 imóveis foi atingido.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do imóvel: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o preço do imóvel: ");
        int preco = scanner.nextInt();
        System.out.print("Digite o aluguel do imóvel: ");
        int aluguel = scanner.nextInt();

        Casa novaCasa = new Casa("Imóvel", nome, preco, aluguel);
        tabuleiro.adicionarCasa(novaCasa);

        System.out.println("Imóvel cadastrado com sucesso!");
    }

    public void listarImoveis() {
        System.out.println("Lista de imóveis no tabuleiro:");
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Casa casa = tabuleiro.getCasa(i);
            System.out.println("Imóvel: " + casa.getNome() + ", Preço: R$" + casa.getValor() + ", Aluguel: R$" + casa.getAluguel());

        }
    }

    public void cadastrarJogador() {
        if (jogadores.size() >= 6) {
            System.out.println("Não é possível cadastrar mais jogadores. O limite máximo de 6 participantes foi atingido.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do jogador: ");
        String nome = scanner.nextLine();

        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equalsIgnoreCase(nome)) {
                System.out.println("O nome '" + nome + "' já está em uso. Por favor, escolha outro nome.");
                return;
            }
        }

        Jogador novoJogador = new Jogador(nome, saldo, salario);
        jogadores.add(novoJogador);

        System.out.println("Jogador cadastrado com sucesso!");
    }

    public void listarJogadores() {
        System.out.println("Lista de jogadores:");
        for (Jogador jogador : jogadores) {
            System.out.println("Jogador: " + jogador.getNome() + ", Saldo: R$" + jogador.getSaldo());
        }
    }

    public void configurarJogo() {
        Scanner scanner = new Scanner(System.in);

        // Configura os valores iniciais
        System.out.print("Digite o saldo inicial dos jogadores: ");
        saldo = scanner.nextInt();
        for (Jogador jogador : jogadores) {
            jogador.setSaldo(this.saldo); // Atualiza o saldo de cada jogador
        }


        System.out.print("Digite o salário recebido ao passar pelo início: ");
        salario = scanner.nextInt();

        System.out.print("Digite o número máximo de rodadas: ");
        rodadasMaximas = scanner.nextInt();

        System.out.println("Configurações iniciais definidas com sucesso!");
    }

    public boolean validarInicioJogo() {
        boolean valido = true;

        if (jogadores.size() < 2) {
            System.out.println("Erro: O jogo deve ter pelo menos 2 jogadores.");
            valido = false;
        }
        if (tabuleiro.getTamanho() < 10) {
            System.out.println("Erro: O tabuleiro deve ter pelo menos 10 imóveis.");
            valido = false;
        }

        return valido;
    }

    public void iniciarJogo() {
        if (!validarInicioJogo()) {
            System.out.println("Não atingiu o mínimo de jogadores ou de imóveis, corrija os erros antes de iniciar o jogo.");
            return;
        }

        System.out.println("Jogo iniciado! Boa sorte a todos!");
    }

    public void mover(Jogador jogador) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("É a vez do jogador " + jogador.getNome() + ". Pressione Enter para jogar o dado.");
        scanner.nextLine();

        int dado = (int) (Math.random() * 6) + 1;
        System.out.println("O dado rolou: " + dado);

        int novaPosicao = (jogador.getPosicao() + dado) % tabuleiro.getTamanho();
        System.out.println(jogador.getNome() + " se moveu para a posição " + novaPosicao + ".");

        // Verifica se passou pelo início
        if (novaPosicao <= jogador.getPosicao()) {
            jogador.alterarSaldo(salario);
            System.out.println("Você passou pela casa inicial e recebeu seu salário de R$" + salario + "!");
        }

        jogador.setPosicao(novaPosicao);
        Casa casa = tabuleiro.getCasa(novaPosicao);
        System.out.println("Você caiu na casa: " + casa.getNome());

        // Verifica se a casa é do tipo "CasaImposto"
        if ("CasaImposto".equals(casa.getTipo())) {
            double patrimonio = jogador.calcularPatrimonioTotal();
            int imposto = (int) (patrimonio * 0.05); // Calcula 5% do patrimônio
            jogador.deduzirSaldo(imposto);
            System.out.println(jogador.getNome() + " caiu na casa " + casa.getNome() +
                    " e pagou R$" + imposto + " de imposto (5% do patrimônio total).");
        }
        else if ("Retribuicao".equals(casa.getTipo())) {
            double patrimonio = jogador.calcularPatrimonioTotal();
            int pagamento = (int) (patrimonio * 0.10); // Calcula 10% do patrimônio
            jogador.alterarSaldo(pagamento);  // Adiciona 10% ao saldo do jogador
            System.out.println(jogador.getNome() + " caiu na casa " + casa.getNome() +
                    " e recebeu R$" + pagamento + " (10% do patrimônio total).");
        }
        // Verifica se a casa é do tipo "Imovel"
        else if ("Imovel".equals(casa.getTipo())) {  // Corrigido para "Imóvel"
            if (casa.getProprietario() == null) {
                System.out.println("Este imóvel está disponível por R$" + casa.getValor() + ".");
                System.out.print("Deseja comprá-lo? (s/n): ");
                String resposta = scanner.nextLine();

                if (resposta.equalsIgnoreCase("s")) {
                    if (jogador.getSaldo() >= casa.getValor()) {
                        jogador.comprarImovel(jogador, casa);
                        System.out.println("Você comprou o imóvel " + casa.getNome() + "!");
                    } else {
                        System.out.println("Você não tem saldo suficiente para comprar este imóvel.");
                    }
                }
            } else if (casa.getProprietario() != jogador) {
                int aluguel = casa.getAluguel();
                System.out.println("Este imóvel pertence a " + casa.getProprietario().getNome() +
                        ". Você deve pagar R$" + aluguel + " de aluguel.");
                jogador.alterarSaldo(-aluguel);
                casa.getProprietario().alterarSaldo(aluguel);
            }
        } else {
            System.out.println("Esta casa não é um imóvel. Siga as regras específicas para este tipo de casa.");
        }
    }




    // Método para iniciar uma rodada
    public void jogarRodada() {
        System.out.println("Iniciando rodada...");
        for (int i = 0; i < jogadores.size(); i++) {
            Jogador jogador = jogadores.get(i);

            if (jogador.getSaldo() <= 0) {
                System.out.println("Jogador " + jogador.getNome() + " está falido e será removido do jogo.");
                jogadores.remove(i);
                i--;
                continue;
            }

            mover(jogador);
        }
        System.out.println("Rodada encerrada.");
            System.out.println("\nStatus Atual dos Jogadores:");
            for (Jogador jogador : jogadores) {
                System.out.println("Jogador: " + jogador.getNome() +
                        ", Posição: " + jogador.getPosicao() +
                        ", Saldo: R$" + jogador.getSaldo());

                // Exibe as propriedades do jogador (assumindo que existe um método para isso)
                System.out.print("Propriedades: ");
                if (jogador.getPropriedades().isEmpty()) {
                    System.out.println("Nenhuma propriedade.");
                } else {
                    for (Casa casa : jogador.getPropriedades()) {
                        System.out.print(casa.getNome() + " | ");
                    }
                    System.out.println();
                }
            }

    }

    public void removerJogadorPorNome() {
        for (Jogador jogador : jogadores) {
            System.out.println("Jogador: " + jogador.getNome());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do jogadorà ser removido: ");
        String nome = scanner.nextLine();
        Jogador jogadorRemover = null;

        // Percorrer a lista de jogadores para encontrar o jogador com o nome correspondente
        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equals(nome)) {
                jogadorRemover = jogador;
                break;  // Encontra o jogador, pode sair do loop
            }
        }

        if (jogadorRemover != null) {
            jogadores.remove(jogadorRemover);
            System.out.println("Jogador " + nome + " removido com sucesso!");
        } else {
            System.out.println("Jogador com o nome " + nome + " não encontrado.");
        }
    }

    public void atualizarJogadorPorNome() {
        for (Jogador jogador : jogadores) {
            System.out.println("Jogador: " + jogador.getNome());
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o nome do jogador à ser atualizar: ");
        String nome = scanner.nextLine();
        Jogador jogadorEncontrado = null;

        // Procurar o jogador na lista
        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equals(nome)) {
                jogadorEncontrado = jogador;
                break;  // Sai do loop quando o jogador for encontrado
            }
        }

        // Se o jogador foi encontrado, permite a atualização
        if (jogadorEncontrado != null) {

            System.out.println("Jogador " + nome + " encontrado.");

            // Escolha do que atualizar
            System.out.println("O que você deseja atualizar?");
            System.out.println("1 - Atualizar Saldo");
            System.out.println("2 - Atualizar Posição");
            System.out.println("0 - Sair");
            // Adicione outras opções conforme necessário
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 0:
                    break;
                case 1:
                    System.out.print("Digite o novo saldo para " + nome + ": ");
                    int novoSaldo = scanner.nextInt();
                    jogadorEncontrado.setSaldo(novoSaldo);
                    System.out.println("Saldo de " + nome + " atualizado para R$" + novoSaldo);
                    break;

                case 2:
                    System.out.print("Digite a nova posição para " + nome + ": ");
                    int novaPosicao = scanner.nextInt();
                    jogadorEncontrado.setPosicao(novaPosicao);
                    System.out.println("Posição de " + nome + " atualizada para " + novaPosicao);
                    break;

                // Caso você queira atualizar outras propriedades, adicione novos cases aqui

                default:
                    System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Jogador com o nome " + nome + " não encontrado.");
        }
    }
    public void atualizarImovelPorNome() {
        Scanner scanner = new Scanner(System.in);

        // Exibe a lista de imóveis para facilitar a escolha
        System.out.println("Imóveis disponíveis no tabuleiro:");
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Casa casa = tabuleiro.getCasa(i);
            if ("Imóvel".equals(casa.getTipo())) {
                System.out.println("Imóvel: " + casa.getNome() + ", Preço: R$" + casa.getValor() + ", Aluguel: R$" + casa.getAluguel());
            }
        }

        System.out.print("\nDigite o nome do imóvel que deseja atualizar: ");
        String nome = scanner.nextLine();

        Casa imovelEncontrado = null;
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Casa casa = tabuleiro.getCasa(i);
            if ("Imóvel".equals(casa.getTipo()) && casa.getNome().equalsIgnoreCase(nome)) {
                imovelEncontrado = casa;
                break;
            }
        }

        if (imovelEncontrado != null) {
            System.out.println("\nImóvel encontrado: " + imovelEncontrado.getNome());
            System.out.println("O que você deseja atualizar?");
            System.out.println("1 - Atualizar Preço");
            System.out.println("2 - Atualizar Aluguel");
            System.out.println("0 - Cancelar");
            int escolha = scanner.nextInt();

            switch (escolha) {
                case 0:
                    System.out.println("Atualização cancelada.");
                    break;
                case 1:
                    System.out.print("Digite o novo preço do imóvel: ");
                    int novoPreco = scanner.nextInt();
                    imovelEncontrado.setValor(novoPreco);
                    System.out.println("Preço atualizado para R$" + novoPreco);
                    break;
                case 2:
                    System.out.print("Digite o novo valor do aluguel: ");
                    int novoAluguel = scanner.nextInt();
                    imovelEncontrado.setAluguel(novoAluguel);
                    System.out.println("Aluguel atualizado para R$" + novoAluguel);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } else {
            System.out.println("Imóvel com o nome '" + nome + "' não encontrado no tabuleiro.");
        }
    }
    // Método auxiliar para remover a casa do tabuleiro
    private void removerCasaDoTabuleiro(int indiceRemover) {
        if (tabuleiro.getTamanho() == 0) {
            System.out.println("O tabuleiro está vazio. Nenhuma casa para remover.");
            return;
        }

        Tabuleiro.No atual = tabuleiro.inicio;
        Tabuleiro.No anterior = null;

        // Encontrar o nó correspondente ao índice
        for (int i = 0; i < indiceRemover; i++) {
            anterior = atual;
            atual = atual.proximo;
        }

        // Remover o nó
        if (atual == tabuleiro.inicio) {
            if (tabuleiro.getTamanho() == 1) {
                tabuleiro.inicio = null; // Último elemento sendo removido
            } else {
                Tabuleiro.No ultimo = tabuleiro.inicio;
                while (ultimo.proximo != tabuleiro.inicio) {
                    ultimo = ultimo.proximo;
                }
                tabuleiro.inicio = atual.proximo;
                ultimo.proximo = tabuleiro.inicio;
            }
        } else {
            anterior.proximo = atual.proximo;
        }

        tabuleiro.decrementarTamanho();
    }

    public void removerImovelPorNome() {
        Scanner scanner = new Scanner(System.in);

        // Exibe a lista de imóveis no tabuleiro
        System.out.println("Imóveis disponíveis no tabuleiro:");
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Casa casa = tabuleiro.getCasa(i);
            if ("Imóvel".equals(casa.getTipo())) {
                System.out.println("Imóvel: " + casa.getNome() + ", Preço: R$" + casa.getValor() + ", Aluguel: R$" + casa.getAluguel());
            }
        }

        System.out.print("\nDigite o nome do imóvel que deseja remover: ");
        String nome = scanner.nextLine();

        Casa imovelEncontrado = null;
        int indiceRemover = -1;

        // Busca pelo imóvel no tabuleiro
        for (int i = 0; i < tabuleiro.getTamanho(); i++) {
            Casa casa = tabuleiro.getCasa(i);
            if ("Imóvel".equals(casa.getTipo()) && casa.getNome().equalsIgnoreCase(nome)) {
                imovelEncontrado = casa;
                indiceRemover = i;
                break;
            }
        }

        if (imovelEncontrado != null && indiceRemover != -1) {
            // Confirmação da remoção
            System.out.println("\nImóvel encontrado: " + imovelEncontrado.getNome());
            System.out.print("Tem certeza que deseja remover este imóvel? (s/n): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("s")) {
                // Remove o imóvel atualizando o tabuleiro
                removerCasaDoTabuleiro(indiceRemover);
                System.out.println("Imóvel '" + imovelEncontrado.getNome() + "' removido com sucesso!");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } else {
            System.out.println("Imóvel com o nome '" + nome + "' não encontrado no tabuleiro.");
        }
    }







}
