package ordenacaoexterna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    /**
     * Função auxiliar para calcular o tamanho do arquivo passado na função
     */
    public static int contaElementos(String filename) {
        try {
            FileReader f = new FileReader(filename);
            BufferedReader br = new BufferedReader(f);
            int i = 0;
            while(br.readLine() != null) {
                i++;
            }

            return i;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }


    public static void main(String[] args) {
        /**
         * Arquivo que deseja ordenar
         */
        String arquivo = "./files/Entrada20M.csv";

        /**
         * A classe ordenador possui todos os metodos necessários
         * para a ordenação do arquivo
         */
        Ordenador o = new Ordenador(arquivo, 100000);

        try {
            System.out.println("Qtd Elementos: " + contaElementos(arquivo));

            /**
             * Bloco de tempo do merge sort externo
             */
            final long startTime = System.currentTimeMillis();// tempo incial
            o.mergeSortExterno();
            final long elapsedTimeMillis = System.currentTimeMillis() - startTime; //tempo total de execução do programa
            System.out.println("Time Merge sort Externo: " + elapsedTimeMillis);

            /**
             * Bloco da ordenação utilizando o kway merge
             */
            final long startTime2 = System.currentTimeMillis();// tempo incial
            o.ordenar();
            final long elapsedTimeMillis2 = System.currentTimeMillis() - startTime2; //tempo total de execução do programa
            System.out.println("Time k-way: " + elapsedTimeMillis2);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
