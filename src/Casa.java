public class Casa {

    public String nome;
    String tipo;
    int valor;
    int aluguel;
    Jogador proprietario;

    public Casa(String tipo, String nome, int valor, int aluguel) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
        this.aluguel = aluguel;
        this.proprietario = null;
    }

    @Override
    public String toString() {
        return "Casa: " + nome + " | Tipo: " + tipo + " | Valor: R$" + valor +
                " | Aluguel: R$" + aluguel + (proprietario != null ? " | Proprietário: " + proprietario.nome : "");
    }
    public int getAluguel() {
        return aluguel;
    }

    public void setProprietario(Jogador proprietario) {
        this.proprietario = proprietario;
    }

    public Object getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public Jogador getProprietario() {
        return proprietario;
    }

    public int getValor() {
        return valor;
    }


    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setAluguel(int aluguel) {
        this.aluguel = aluguel;
    }

}

