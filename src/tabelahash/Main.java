package tabelahash;

import java.util.Scanner;
    
public class Main {
    
    public static void menu(){
        System.out.println("\tMenu");
        System.out.println("0. Encerrar programa");
        System.out.println("1. Localizar contato (chave de busca: Nome Completo)");
        System.out.println("2. Inserir novo contato");
        System.out.println("3. Excluir contato");
        System.out.println("4. Atualizar contato (exemplo: telefone, cidade e país)");
        System.out.println("5. Salvar dados");
        System.out.println("Opcao:");
    }
  
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        Hash hash = new Hash();

        Contato c = new Contato();
        c.nomeCompleto = "Thais";
        c.cidade = "Vitória";
        c.pais = "Brasil";
        c.telefone = 89123897;

        int opcao;
        menu();
        opcao = s.nextInt();
        
        while (opcao != 0) {
            switch(opcao){ //apenas uma opção
                case 1:
                    Contato a = hash.localizar("Thais");
                    System.out.println("Numero: " + a.telefone);
                    break;
                case 2:
                    hash.inserir(c);
                    break;

                case 3:
                    // hash.excluir();
                    break;

                case 4:
                    // hash.atualizar();
                    break;
                case 5:
                    // hash.salvar();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            menu();
            opcao = s.nextInt();

        }
    } 
}

