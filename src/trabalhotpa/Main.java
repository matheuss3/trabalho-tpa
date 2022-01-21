package trabalhotpa;

import java.util.Scanner;
    
public class Main {
    
    public static void menu(){
        System.out.println("\n\tMenu");
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

        Contato c = new Contato("Thais",89123897,"Vitória","Brasil");
        Contato b = new Contato("Matheus de Souza",999999999,"Vitória","Brasil");
        
        int opcao;
        menu();
        opcao = s.nextInt();
        String nome;
        
        while (opcao != 0) {
            switch(opcao){ //apenas uma opção
                case 1:
                    
                    s.nextLine();
                    System.out.println("\nDigite o nome completo do contato que deseja localizar:");
                    nome = s.nextLine();
                    Contato a = hash.localizar(nome);
                    
                    if (a != null){
                        System.out.println("\nContato localizado com sucesso!");
                        hash.imprimeContato(nome);
                    }
                    else{
                        System.out.println("Nenhum contato foi localizado com o nome de " + nome + "\n");
                    }  
                    break;
                case 2: 
                    System.out.println("\nContato de " + c.getNomeCompleto() + " inserido com sucesso!\n");
                    hash.inserir(c);
                    System.out.println("\nContato de " + b.getNomeCompleto() + " inserido com sucesso!\n");
                    hash.inserir(b);
                    break;

                case 3:    
                    System.out.println("\nDigite o nome completo do contato que deseja excluir:");
                    nome = s.nextLine();
                    hash.excluir(nome);
                    System.out.println("\nContato excluido com sucesso!");
                    break;
                case 4:
                    hash.atualizar(99606060,"vix","EUA","Thais");
                    System.out.println("Dados atualizados com sucesso!");
                    hash.imprimeContato("Thais");
                    break;    
                case 5:
                    hash.salvar();
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            menu();
            opcao = s.nextInt();
        }
    } 
}


