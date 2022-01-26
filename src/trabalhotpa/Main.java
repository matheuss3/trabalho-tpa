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
        
        int opcao;
        menu();
        opcao = s.nextInt();
        
        String nome;
        int telefone;
        String cidade;
        String pais;
        
        while (opcao != 0) {
            switch(opcao){ //apenas uma opção
                case 1:
                    s.nextLine(); //pegando residuo
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
                    s.nextLine(); //pegando residuo
                                        
                    System.out.println("Digite nome completo de contato: ");
                    nome = s.nextLine();
                    
                    System.out.println("\nDigite número de telefone: ");
                    telefone = s.nextInt();
                    
                    s.nextLine(); //pegando residuo
                    System.out.println("\nDigite a cidade: ");
                    cidade = s.nextLine();

                    System.out.println("\nDigite o país: ");
                    pais = s.nextLine();
                    
                    Contato d = new Contato(nome, telefone, cidade, pais);
                    hash.inserir(d);
                    
                    System.out.println("\nContato de " + nome + " inserido com sucesso!\n");
                    break;

                case 3:    
                    s.nextLine(); //pegando residuo
                    System.out.println("\nDigite o nome completo do contato que deseja excluir:");
                    nome = s.nextLine();
                    hash.excluir(nome);
                    System.out.println("\nContato de " + nome + " excluido com sucesso!");
                    break;
                case 4:
                    s.nextLine(); //pegando residuo
                    System.out.println("\nDigite o nome completo do contato que deseja atualizar:");
                    nome = s.nextLine();
                    Contato l = hash.localizar(nome);
                    
                    if (l != null){
                        
                        System.out.println("\nSeu número de telefone antigo era: " + l.getTelefone());
                        System.out.println("\nDigite seu novo número de telefone: ");
                        telefone = s.nextInt();

                        s.nextLine(); //pegando residuo
                        System.out.println("\nSua cidade antiga era: " + l.getCidade());
                        System.out.println("\nDigite sua nova cidade: ");
                        cidade = s.nextLine();

                        System.out.println("\nSeu país antigo era: " + l.getPais());
                        System.out.println("\nDigite seu novo país: ");
                        pais = s.nextLine();

                        hash.atualizar(telefone,cidade,pais,nome);
                        System.out.println("\nDados atualizados com sucesso!");
                        hash.imprimeContato(nome);
                    }
                    else{
                        System.out.println("Contato não foi localizado!");
                    }
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
