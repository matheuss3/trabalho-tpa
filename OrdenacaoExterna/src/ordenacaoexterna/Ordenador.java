package ordenacaoexterna;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe ordenador com os métodos + atributos
 * necessários para a ordenação do arquivo utilizando
 * o modelo do K-way merge e o Merge Sort Externo
 */
public class Ordenador {
    // Arquivo que desejo ordenar
    private String arquivo;

    // Tamanho do buffer
    private int bufferSize;

    public Ordenador(String arquivo, int bufferSize) {
        this.arquivo = arquivo;
        this.bufferSize = bufferSize;
    }

    /**
     * Função responsavel por gerar vários arquivos ordenados,
     * a quantidade de arquivo depende do tamanho do buffer informado.
     * A função retorna a quantidade de arquivos gerados
     * @return
     * @throws IOException
     */
    public int geraArquivosOrdenados() throws IOException {
        try {
            // Leitor do arquivo
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);

            // Buffer de contatos
            ArrayList<Contato> contatosBuffer = new ArrayList<>();

            // Contador de iterações
            int i = 1;

            String linha = br.readLine();
            while (linha != null) {
                Contato contato = new Contato(linha);
                contatosBuffer.add(contato);

                // Buffer cheio ?
                if (contatosBuffer.size() >= bufferSize) {
                    // Ordenar o buffer
                    contatosBuffer.sort(Contato::compareTo);

                    // Salvar dados do buffer na memória secundária
                    FileWriter fw = new FileWriter("./files/b" + i++ + ".csv");
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (Contato c : contatosBuffer) {
                        bw.write(c.contatoToString() + "\n");
                    }
                    bw.close();
                    fw.close();

                    // Esvaziando buffer
                    contatosBuffer.clear();
                }

                linha = br.readLine();
            }

            // Buffer com residuos?
            if (contatosBuffer.size() > 0) {
                // Ordena buffer
                contatosBuffer.sort(Contato::compareTo);

                FileWriter fw = new FileWriter("./files/b" + i++ + ".csv");
                BufferedWriter bw = new BufferedWriter(fw);
                for (Contato c : contatosBuffer) {
                    bw.write(c.contatoToString() + ",\n");
                }
                bw.close();
                fw.close();

                contatosBuffer.clear();
            }

            return i - 1;
        } catch (IOException e) {
            throw e;
        }
    }

    public void ordenar() throws IOException {
        try {
            // Gera arquivos ordenados
            int qtdArquivos = geraArquivosOrdenados();


            // Gera Lista de leitores para cada arquivo
            ArrayList<FileReader> fileReaders = new ArrayList<>();
            ArrayList<BufferedReader> bufferedReaders = new ArrayList<>();
            for (int i = 1; i <= qtdArquivos; i++) {
                FileReader fr = new FileReader("./files/b" + i + ".csv");
                BufferedReader br = new BufferedReader(fr);

                fileReaders.add(fr);
                bufferedReaders.add(br);
            }

            // Lista de linhas lidas em cada arquivo
            ArrayList<String> linhas = new ArrayList<>();
            for (BufferedReader br : bufferedReaders) linhas.add(br.readLine());

            // Arquivo de saida ordenado
            FileWriter fw = new FileWriter("./files/sortOut.csv");
            BufferedWriter bw = new BufferedWriter(fw);


            boolean flag = true;
            while (flag) {
                flag = false;
                // Menor contato
                int indexMenorContato = -1;
                Contato menorContato = null;

                // Comparando todos os contatos de cada linha
                for (String linha : linhas) {
                    if (linha != null) {
                        /**
                         * Linha diferente de nulo, logo ainda podem existir linhas
                         * para serem lidas
                          */
                        flag = true;

                        Contato contato = new Contato(linha);
                        // Atualiza menor contato se nulo ou se menor que o menor
                        if (menorContato == null || menorContato.compareTo(contato) > 0) {
                            indexMenorContato = linhas.indexOf(linha);
                            menorContato = contato;
                        }
                    }
                }

                // Escrevendo o menor contato no arquivo de saida
                if (flag) {
                    bw.write(menorContato.contatoToString() + ",\n");

                    // Lendo as proximas linhas
                    ArrayList<String> newLinhas = new ArrayList<>();
                    for (BufferedReader br : bufferedReaders) {
                        int indexBuffer = bufferedReaders.indexOf(br);
                        if (indexMenorContato == indexBuffer) {
                            newLinhas.add(br.readLine());
                        } else {
                            newLinhas.add(linhas.get(indexBuffer));
                        }
                    }
                    linhas = newLinhas;
                }

            }

            bw.close();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Divide o arquivo em n partes, método utilizado no
     * algoritmo que realiza o merge sort externo
     * @param qtdDivisoes
     * @throws IOException
     */
    public void divideArquivo(int qtdDivisoes) throws IOException{
        ArrayList<BufferedWriter> bws = new ArrayList<>();
        try {
            for (int i = 0; i < qtdDivisoes; i++) {
                bws.add(new BufferedWriter(new FileWriter("./files/m" + (i + 1) + ".csv")));
            }

            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);

            String linha = br.readLine();
            while (linha != null) {
                bws.get(0).write(linha + "\n");

                for (int i = 1; i < qtdDivisoes; i++) {
                    linha = br.readLine();
                    if (linha != null) {
                        bws.get(i).write(linha + "\n");
                    }
                }

                linha = br.readLine();
            }

            for (BufferedWriter bw : bws) {
                bw.close();
            }
        } catch (IOException e) {
            throw e;
        }

    }

    /**
     * Algoritmo que gera o arquivo ordenado utilizando o método do merge
     * sort externo
     * @throws IOException
     */
    public void mergeSortExterno() throws IOException {
        try {
            // Dividindo o arquivo em 2
            divideArquivo(2);

            // Os dois arquivos de leitura
            Reader r1 = new Reader("./files/m1.csv");
            Reader r2 = new Reader("./files/m2.csv");


            int iArquivo = 1;
            // Os dois arquivos de saida
            BufferedWriter bwo1 = new BufferedWriter(new FileWriter("./files/o1.csv"));
            BufferedWriter bwo2 = new BufferedWriter(new FileWriter("./files/o2.csv"));

            int r = 1; // Numero da rodada
            int iteracao = 1; // Iteração

            // Parametro para decidis se o arquvo foi finalizado
            int qtdDadosEscritosO1 = 0;
            int qtdDadosEscritosO2 = 0;
            boolean flag = true;
            while (flag) {

                if (r1.getQtdLinhasLidas() < r) r1.read();
                if (r2.getQtdLinhasLidas() < r) r2.read();

                /**
                 * For da quantidade de vezes em que é necessária
                 * escrever em um arquivo antes de alterna-lo em uma rodada
                 *
                 * Na rodada 1 escreverei 2 valores antes de alternar o arquivo
                 * Na rodada 2 escreverei 4 valores ...
                 * ...
                 */
                for (int i = 0; i < r * 2; i++) {
                    Contato menorContato = null;
                    Reader menorReader = null;

                    if (r1.getQtdLinhasLidas() <= r * iteracao && r1.getUltimaLinhaLida() != null) {
                        menorContato = new Contato(r1.getUltimaLinhaLida());
                        menorReader = r1;
                    }

                    if (r2.getQtdLinhasLidas() <= r * iteracao && r2.getUltimaLinhaLida() != null) {
                        Contato caux = new Contato(r2.getUltimaLinhaLida());
                        if (menorContato == null || menorContato.compareTo(caux) > 0) {
                            menorContato = caux;
                            menorReader = r2;
                        }
                    }

                    if (menorContato != null){
                        // Escolhendo os arquivos de escrita
                        if (iteracao % 2 != 0) {
                            bwo1.write(menorContato.contatoToString() + ",\n");

                            qtdDadosEscritosO1++;
                        } else {
                            bwo2.write(menorContato.contatoToString() + ",\n");

                            qtdDadosEscritosO2++;
                        }

                        menorReader.read();
                    }
                }

                iteracao++;
                /**
                 * A rodada finaliza quando os dois arquivos foram lidos completamente
                 */
                if (r1.getUltimaLinhaLida() == null && r2.getUltimaLinhaLida() == null) {
                    /**
                     * A ordenação finaliza quando em um arquivo nenhum dado é escrito
                     */
                    if (qtdDadosEscritosO1 == 0 || qtdDadosEscritosO2 == 0) flag = false;

                    // Ainda existem rodadas, logo reset dos valores é feito abaixo
                    qtdDadosEscritosO1 = 0;
                    qtdDadosEscritosO2 = 0;

                    bwo1.close();
                    bwo2.close();
                    r1.close();
                    r2.close();

                    // Setando o valor da proxima rodada
                    r *= 2;


                    // Alternado os arquivos de leitura e escrita
                    iArquivo++;
                    if (iArquivo % 2 == 0) {
                        r1 = new Reader("./files/o1.csv");
                        r2 = new Reader("./files/o2.csv");

                        bwo1 = new BufferedWriter(new FileWriter("./files/m1.csv"));
                        bwo2 = new BufferedWriter(new FileWriter("./files/m2.csv"));
                    } else {
                        r1 = new Reader("./files/m1.csv");
                        r2 = new Reader("./files/m2.csv");

                        bwo1 = new BufferedWriter(new FileWriter("./files/o1.csv"));
                        bwo2 = new BufferedWriter(new FileWriter("./files/o2.csv"));

                    }

                    iteracao = 1;
                }
            }

            // Dando close nos arquivos
            bwo1.close();
            bwo2.close();
            r1.close();
            r2.close();



        } catch (IOException e) {
            throw e;
        }
    }
}
