package ordenacaoexterna;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
    private BufferedReader br;
    private int qtdLinhasLidas;
    private String ultimaLinhaLida;

    public Reader(String arquivo) {
        this.setQtdLinhasLidas(0);
        try {
            setBr(new BufferedReader(new FileReader(arquivo)));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read() {
        try {
            setUltimaLinhaLida(getBr().readLine());
            setQtdLinhasLidas(getQtdLinhasLidas() + 1);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void close() {
        try {
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public int getQtdLinhasLidas() {
        return qtdLinhasLidas;
    }

    public void setQtdLinhasLidas(int qtdLinhasLidas) {
        this.qtdLinhasLidas = qtdLinhasLidas;
    }

    public String getUltimaLinhaLida() {
        return ultimaLinhaLida;
    }

    public void setUltimaLinhaLida(String ultimaLinhaLida) {
        this.ultimaLinhaLida = ultimaLinhaLida;
    }
}
