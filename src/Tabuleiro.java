class Tabuleiro {
    No inicio;
    private int tamanho;



    // Classe interna No
    static class No {
        Casa casa;
        No proximo;

        No(Casa casa) {
            this.casa = casa;
            this.proximo = null;
        }
    }
    // Construtor
    public Tabuleiro() {
        this.inicio = null;
        this.tamanho = 0;
    }

    // Método para adicionar uma casa ao tabuleiro
    public void adicionarCasa(Casa novaCasa) {
        No novoNo = new No(novaCasa);

        if (inicio == null) {
            // Primeira casa sendo adicionada
            inicio = novoNo;
            inicio.proximo = inicio; // Circularidade
        } else {
            No atual = inicio;
            while (atual.proximo != inicio) {
                atual = atual.proximo;
            }
            atual.proximo = novoNo;
            novoNo.proximo = inicio; // Fecha a circularidade
        }
        tamanho++;
    }

    // Método para obter uma casa pelo índice (posição no tabuleiro)

    public Casa obterCasa(int posicao) {
        if (tamanho == 0) {
            throw new IllegalStateException("O tabuleiro está vazio.");
        }
        if (posicao < 0) {
            throw new IndexOutOfBoundsException("Posição inválida: " + posicao);
        }

        int indice = posicao % tamanho; // Garante a circularidade
        No atual = inicio;
        for (int i = 0; i < indice; i++) {
            atual = atual.proximo;
        }
        return atual.casa;
    }

    // Método para obter o tamanho do tabuleiro
    public int getTamanho() {
        return tamanho;
    }
    public Casa getCasa(int i) {
        return obterCasa(i);
    }
    public void decrementarTamanho() {
        if (tamanho > 0) {
            tamanho--; // Atualiza o tamanho internamente
        }
    }



}

