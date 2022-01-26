package ordenadorarquivo;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;

public class Ordenador {
    private String arquivo;
    private int bufferSize;

    public Ordenador(String arquivo, int bufferSize) {
        this.arquivo = arquivo;
        this.bufferSize = bufferSize;
    }

    public int geraArquivosOrdenados() throws IOException {
        try {
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);

            ArrayList<Contato> contatos = new ArrayList<>();
            String linha = br.readLine();
            int i = 1;
            while (linha != null) {
                String[] atributos = linha.strip().split(",");
                Contato contato = new Contato(atributos[0], atributos[1], atributos[2], atributos[3]);
                contatos.add(contato);
                if (contatos.size() >= bufferSize) {
                    FileWriter fw = new FileWriter("./files/b" + i++ + ".csv");
                    BufferedWriter bw = new BufferedWriter(fw);
                    contatos.sort(Contato::compareTo);
                    for (Contato c : contatos) {
                        bw.write(c.getNome() + "," + c.getTelefone() + "," + c.getCidade() + "," +
                                c.getPais() + "\n");
                    }
                    bw.close();
                    fw.close();

                    contatos.clear();
                }

                linha = br.readLine();
            }

            if (contatos.size() > 0) {
                FileWriter fw = new FileWriter("./files/b" + i++ + ".csv");
                BufferedWriter bw = new BufferedWriter(fw);
                contatos.sort(Contato::compareTo);
                for (Contato c : contatos) {
                    bw.write(c.getNome() + "," + c.getTelefone() + "," + c.getCidade() + "," +
                            c.getPais() + ",\n");
                }
                bw.close();
                fw.close();

                contatos.clear();
            }

            return i - 1;
        } catch (IOException e) {
            throw e;
        }
    }

    public void ordenar() throws IOException {
        try {
            int qtdArquivos = geraArquivosOrdenados();

            ArrayList<FileReader> fileReaders = new ArrayList<>();
            ArrayList<BufferedReader> bufferedReaders = new ArrayList<>();

            for (int i = 1; i <= qtdArquivos; i++) {
                FileReader fr = new FileReader("./files/b" + i + ".csv");
                BufferedReader br = new BufferedReader(fr);

                fileReaders.add(fr);
                bufferedReaders.add(br);
            }

            ArrayList<String> linhas = new ArrayList<>();
            for (BufferedReader br : bufferedReaders) linhas.add(br.readLine());

            boolean flag = true;

            FileWriter fw = new FileWriter("./files/sortOut.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            int qtdContatos = 0;
            while (flag) {
                flag = false;
                int indexMenorContato = -1;
                Contato menorContato = null;

                for (String linha : linhas) {
                    if (linha != null) {
                        flag = true;
                        String[] atributos = linha.strip().split(",");
                        Contato contato = new Contato(atributos[0], atributos[1], atributos[2], atributos[3]);

                        if (menorContato == null || menorContato.compareTo(contato) > 0) {
                            indexMenorContato = linhas.indexOf(linha);
                            menorContato = contato;
                        }
                    }
                }

                if (flag) {
                    System.out.println("Quantidade de contatos: " + ++qtdContatos);
                    System.out.println(menorContato.getNome() + "," + menorContato.getTelefone() + "," +
                            menorContato.getCidade() + "," + menorContato.getPais() + "\n");
                    bw.write(menorContato.getNome() + "," + menorContato.getTelefone() + "," +
                            menorContato.getCidade() + "," + menorContato.getPais() + ",\n");
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
            /*
             * Dar close em todos os readers e writers
             * */
        } catch (IOException e) {
            throw e;
        }
    }

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


    public void mergeSortExterno() throws IOException {
        try {
            divideArquivo(2);

            Reader r1 = new Reader("./files/m1.csv");
            Reader r2 = new Reader("./files/m2.csv");

            int iArquivo = 1;

            BufferedWriter bwo1 = new BufferedWriter(new FileWriter("./files/o1.csv"));
            BufferedWriter bwo2 = new BufferedWriter(new FileWriter("./files/o2.csv"));

            int r = 1;
            int iteracao = 1;

            int qtdDadosEscritosO1 = 0;
            int qtdDadosEscritosO2 = 0;

            boolean flag = true;
            while (flag) {

                if (r1.getQtdLinhasLidas() < r) r1.read();
                if (r2.getQtdLinhasLidas() < r) r2.read();
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
                        if (iteracao % 2 != 0) {
                            bwo1.write(menorContato.getNome() + "," + menorContato.getTelefone() + "," +
                                    menorContato.getCidade() + "," + menorContato.getPais() + ",\n");

                            qtdDadosEscritosO1++;
                        } else {
                            bwo2.write(menorContato.getNome() + "," + menorContato.getTelefone() + "," +
                                    menorContato.getCidade() + "," + menorContato.getPais() + ",\n");

                            qtdDadosEscritosO2++;
                        }

                        menorReader.read();
                    }
                }

                iteracao++;

                if (r1.getUltimaLinhaLida() == null && r2.getUltimaLinhaLida() == null) {
                    if (qtdDadosEscritosO1 == 0 || qtdDadosEscritosO2 == 0) flag = false;

                    qtdDadosEscritosO1 = 0;
                    qtdDadosEscritosO2 = 0;

                    bwo1.close();
                    bwo2.close();
                    r1.close();
                    r2.close();

                    r *= 2;

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

            bwo1.close();
            bwo2.close();
            r1.close();
            r2.close();



        } catch (IOException e) {
            throw e;
        }
    }
}
