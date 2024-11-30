import java.util.ArrayList;
import java.util.List;

public class Jogador {
    String nome;
    int saldo;
    int posicao;
    int salario;
    List<Casa> propriedades;


    public Jogador(String nome, int saldoInicial, int salario) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.salario = salario;
        this.posicao = 0;
        this.propriedades = new ArrayList<>();

    }
    @Override
    public String toString() {
        return "Jogador: " + nome + " | Saldo: R$" + saldo + " | Posição: " + posicao +
                " | Propriedades: " + (propriedades.isEmpty() ? "Nenhuma" : propriedades.size());
    }


    public void comprarImovel(Jogador jogador, Casa casa) {
        if (casa.getProprietario() != null) {
            System.out.println("Imóvel já possui um proprietário: " + casa.getProprietario().getNome());
            return;
        }

        if (jogador.getSaldo() < casa.getValor()) {
            System.out.println("Saldo insuficiente! Saldo atual: R$" + jogador.getSaldo() + ", Preço do imóvel: R$" + casa.getValor());
            return;
        }

        jogador.deduzirSaldo(casa.getValor());
        casa.setProprietario(jogador);
        jogador.adicionarPropriedade(casa);

        System.out.println("Compra realizada com sucesso! " + jogador.getNome() + " agora é o proprietário do imóvel " + casa.getNome() + "\n Seu saldo fui atualizado" + getSaldo() + "!");
    }
    public double calcularPatrimonioTotal() {
        double patrimonio = saldo; // Inclui o saldo no cálculo
        for (Casa casa : propriedades) {
            patrimonio += casa.getValor();
        }
        return patrimonio;
    }

    public int getSalario() {
        return salario;
    }

    public String getNome() {
        return nome;
    }

    public int getSaldo() {
        return saldo;
    }

    public void adicionarPropriedade(Casa casa) {
        propriedades.add(casa);
    }

    public void deduzirSaldo(int valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor a ser deduzido não pode ser negativo.");
        }
        if (saldo < valor) {
            throw new IllegalArgumentException("Saldo insuficiente para deduzir o valor: R$" + valor);
        }
        this.saldo -= valor;
    }

    public void setSaldo(int novoSaldo) {
        this.saldo = novoSaldo;
    }

    public void alterarSaldo(int valor) {
        this.saldo += valor;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int novaPosicao) {
        this.posicao = novaPosicao;
    }

    public List<Casa> getPropriedades() {
        return propriedades;
    }



}
