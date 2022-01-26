package ordenadorarquivo;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Ordenador o = new Ordenador("./files/AgendaTesteLight.csv", 50000);
        try {
            o.mergeSortExterno();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
