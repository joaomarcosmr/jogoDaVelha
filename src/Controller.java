import java.util.Scanner;
import java.util.Random;

public class Controller {
    private Model modelo;
    private View visao;
    private Scanner scanner;

    public Controller() {
        modelo = new Model();
        visao = new View();
        scanner = new Scanner(System.in);
    }

    public void iniciarJogo() {
        int jogadas = 0;
        while (true) {
            usuarioJoga();
            jogadas++;
            visao.mostrarTabuleiro(modelo.getMatriz());
            int resultado = verificaSeTemVitoria();
            if (resultado == 1 || resultado == 3) {
                mostrarVencedor(resultado);
                break;
            }

            if (jogadas == 9) {
                visao.mostrarMensagem("Empate!");
                break;
            }

            computadorJoga();
            jogadas++;
            visao.mostrarTabuleiro(modelo.getMatriz());
            resultado = verificaSeTemVitoria();
            if (resultado == 1 || resultado == 3) {
                mostrarVencedor(resultado);
                break;
            }

            if (jogadas == 9) {
                visao.mostrarMensagem("Empate!");
                break;
            }
        }
    }

    private void usuarioJoga() {
        int linha, coluna;
        while (true) {
            visao.mostrarMensagem("Sua vez! Informe linha e coluna (0-2): ");
            linha = scanner.nextInt();
            coluna = scanner.nextInt();
            if (isMovimentoValido(linha, coluna)) {
                if (modelo.verificaSeAPosicaoEstaLivre(linha, coluna)) {
                    modelo.setCelula(linha, coluna, 1);
                    break;
                } else {
                    visao.mostrarMensagem("Essa posição já está ocupada. Tente outra.");
                }
            } else {
                visao.mostrarMensagem("Entrada inválida! Use números entre 0 e 2.");
            }
        }
    }

    private boolean isMovimentoValido(int linha, int coluna) {
        return linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3;
    }

    private void computadorJoga() {
        visao.mostrarMensagem("Computador jogando...");
        modelo.calculaOsPesos();
        if (!verSeEstouEmAlerta()) {
            int[] jogada = pegaACelulaDeMaiorPeso();
            modelo.setCelula(jogada[0], jogada[1], 3);
        } else {
            atrapalharAdversario();
        }
    }

    private boolean verSeEstouEmAlerta() {
        int[] pesos = modelo.getPesos();
        for (int i = 0; i < pesos.length; i++) {
            if (pesos[i] == 18 || pesos[i] == 2) {
                return true;
            }
        }
        return false;
    }

    private void atrapalharAdversario() {
        int[] pesos = modelo.getPesos();
        for (int i = 0; i < pesos.length; i++) {
            if (pesos[i] == 2) {
                bloquearJogada(i);
                return;
            }
        }
        Random rand = new Random();
        int linha, coluna;
        while (true) {
            linha = rand.nextInt(3);
            coluna = rand.nextInt(3);
            if (modelo.verificaSeAPosicaoEstaLivre(linha, coluna)) {
                modelo.setCelula(linha, coluna, 3);
                return;
            }
        }
    }

    private void bloquearJogada(int indiceLinha) {
        int[][] posicoes = getPosicoesLinha(indiceLinha);
        for (int[] pos : posicoes) {
            if (modelo.verificaSeAPosicaoEstaLivre(pos[0], pos[1])) {
                modelo.setCelula(pos[0], pos[1], 3);
                return;
            }
        }
    }

    private int[][] getPosicoesLinha(int indice) {
        int[][] posicoes = new int[3][2];
        if (indice >= 0 && indice <= 2) {
            for (int j = 0; j < 3; j++) {
                posicoes[j][0] = indice;
                posicoes[j][1] = j;
            }
        } else if (indice >= 3 && indice <= 5) {
            for (int i = 0; i < 3; i++) {
                posicoes[i][0] = i;
                posicoes[i][1] = indice - 3;
            }
        } else if (indice == 6) {
            for (int i = 0; i < 3; i++) {
                posicoes[i][0] = i;
                posicoes[i][1] = i;
            }
        } else if (indice == 7) {
            for (int i = 0; i < 3; i++) {
                posicoes[i][0] = i;
                posicoes[i][1] = 2 - i;
            }
        }
        return posicoes;
    }

    private int[] pegaACelulaDeMaiorPeso() {
        int pesoMaximo = -1;
        int[] jogada = new int[2];
        for (int i = 0; i < modelo.getMatriz().length; i++) {
            for (int j = 0; j < modelo.getMatriz()[i].length; j++) {
                if (modelo.verificaSeAPosicaoEstaLivre(i, j)) {
                    int peso = calcularPesoDaCelula(i, j);
                    if (peso > pesoMaximo) {
                        pesoMaximo = peso;
                        jogada[0] = i;
                        jogada[1] = j;
                    }
                }
            }
        }
        return jogada;
    }

    private int calcularPesoDaCelula(int linha, int coluna) {
        int peso = 0;
        if (modelo.getCelula(linha, 0) != 1 && modelo.getCelula(linha, 1) != 1 && modelo.getCelula(linha, 2) != 1)
            peso++;
        if (modelo.getCelula(0, coluna) != 1 && modelo.getCelula(1, coluna) != 1 && modelo.getCelula(2, coluna) != 1)
            peso++;
        if (linha == coluna) {
            if (modelo.getCelula(0, 0) != 1 && modelo.getCelula(1, 1) != 1 && modelo.getCelula(2, 2) != 1)
                peso++;
        }
        if (linha + coluna == 2) {
            if (modelo.getCelula(0, 2) != 1 && modelo.getCelula(1, 1) != 1 && modelo.getCelula(2, 0) != 1)
                peso++;
        }
        return peso;
    }

    private int verificaSeTemVitoria() {
        int resultado = verificaSeXGanhou();
        if (resultado == 3) return 3;
        resultado = verificaSeOGanhou();
        if (resultado == 1) return 1;
        return 2;
    }

    private int verificaSeXGanhou() {
        modelo.calculaOsPesos();
        int[] pesos = modelo.getPesos();
        for (int peso : pesos) {
            if (peso == 27) {
                return 3;
            }
        }
        return 2;
    }

    private int verificaSeOGanhou() {
        modelo.calculaOsPesos();
        int[] pesos = modelo.getPesos();
        for (int peso : pesos) {
            if (peso == 1) {
                return 1;
            }
        }
        return 2;
    }

    private void mostrarVencedor(int vencedor) {
        if (vencedor == 1) {
            visao.mostrarMensagem("Você venceu!");
        } else if (vencedor == 3) {
            visao.mostrarMensagem("Computador venceu!");
        }
    }
}
