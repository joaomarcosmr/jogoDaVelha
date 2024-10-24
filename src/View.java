public class View {
    public void mostrarTabuleiro(int[][] matriz) {
        System.out.println();
        for (int i = 0; i < matriz.length; i++) {
            String linha = "";
            for (int j = 0; j < matriz[i].length; j++) {
                linha += converter(matriz[i][j]);
                if (j < matriz[i].length - 1) linha += "|";
            }
            System.out.println(linha);
            if (i < matriz.length - 1) System.out.println("-----");
        }
        System.out.println();
    }

    private char converter(int numero) {
        switch (numero) {
            case 1:
                return 'O';
            case 3:
                return 'X';
            default:
                return ' ';
        }
    }

    public void mostrarMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
