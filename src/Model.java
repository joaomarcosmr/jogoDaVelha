public class Model {
    private int[][] matriz;
    private int[] pesos;
    private final int TAMANHO = 3;

    public Model() {
        matriz = new int[TAMANHO][TAMANHO];
        pesos = new int[8];
        inicializarMatriz();
    }

    private void inicializarMatriz() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                matriz[i][j] = 2;
            }
        }
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public int getCelula(int linha, int coluna) {
        return matriz[linha][coluna];
    }

    public void setCelula(int linha, int coluna, int valor) {
        matriz[linha][coluna] = valor;
    }

    public boolean verificaSeAPosicaoEstaLivre(int linha, int coluna) {
        return matriz[linha][coluna] == 2;
    }

    public void calculaOsPesos() {
        int indice = 0;
        for (int i = 0; i < TAMANHO; i++) {
            pesos[indice++] = matriz[i][0] * matriz[i][1] * matriz[i][2];
        }
        for (int j = 0; j < TAMANHO; j++) {
            pesos[indice++] = matriz[0][j] * matriz[1][j] * matriz[2][j];
        }
        pesos[indice++] = matriz[0][0] * matriz[1][1] * matriz[2][2];
        pesos[indice++] = matriz[0][2] * matriz[1][1] * matriz[2][0];
    }

    public int[] getPesos() {
        return pesos;
    }
}
