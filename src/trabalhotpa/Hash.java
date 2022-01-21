package trabalhotpa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Hash {
    ArrayList<Elemento> hashContato;
    static int MAXtamanho = 20000;
     
    public Hash(){
        
        hashContato = new ArrayList<>();
        this.inicializaTabela();       
    }
    
    public void inicializaTabela(){  // inicializando tabela hash
        for (int i=0; i<MAXtamanho; i++) {
            Elemento e = new Elemento();
            e.c = null;
            e.ocupado = false;   
            hashContato.add(e);
        }
    }

    private int calculaChave(String nomeContato) {  //Calculando chave para guardar os contatos na tabela
        int chave = 0;
        int i = 1;
        for (char letra : nomeContato.toCharArray()) { // Converte a String em um array de caracteres "abc" -> ['a', 'b', 'c']
            int ascii =  letra;
            chave += i * letra;
            i++;
        }

        return chave;
    }
    
    public void imprimeContato(String nome){
        
        int chave = calculaChave(nome);
        Elemento e = hashContato.get(chave);
        System.out.println("Imprimindo contato de " + e.c.getNomeCompleto());
        System.out.println("Telefone: " + e.c.getTelefone());
        System.out.println("Cidade: " + e.c.getCidade());
        System.out.println("País: " + e.c.getPais());     
    }

    public void inserir(Contato c) {  //insere novo contato
        
        int chave = calculaChave(c.getNomeCompleto());
        Elemento e = hashContato.get(chave);  //pega o elemento armazenado
        e.ocupado = true;    //seta posição para ocupado
        e.c = c;    //armazena o contato
    }

    public Contato localizar(String nome){  //localiza contato
        
        int chave = calculaChave(nome);
        Elemento e = hashContato.get(chave);
        if (e.ocupado == true){
            return e.c;
        }
        return null;
    }
    
    public void excluir(String nome){  //exclui contato
        
        int chave = calculaChave(nome);
        Elemento e = hashContato.get(chave);
        e.c = null;
        e.ocupado = false;  
    }

    public void atualizar(int tel, String cid, String p, String nome){  //atualiza contato
                
        int chave = calculaChave(nome);
        Elemento e = hashContato.get(chave);
        e.c.setTelefone(tel);
        e.c.setCidade(cid);
        e.c.setPais(p);
    }

    public void salvar(){ //salva dados
        
        try{       
            FileWriter fw = new FileWriter("AgendaTeste1M.csv"); 
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (Elemento e : this.hashContato){
                bw.write(e.c.contatoToString() + "\n"); 
            }   
            bw.close();
        }
        catch(IOException e){ 
            System.out.println("---- Erro ao tentar salvar no arquivo! ----");
        }
    }
    
    public void ler(){ //Vou ler o arquivo de contatos (Agenda) e salvar no array
        try{
            
            FileReader fr = new FileReader("AgendaTeste1M.csv");
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();
            
            while (linha != null){
                
               Contato c = new Contato(linha); //Apenas precisa criar o contato, a função de inserir já cria o elemento e insere no array             
               inserir(c);
               linha = br.readLine();
            }
            br.close();
        }
        catch(IOException e){          
            System.out.println("---- Erro ao tentar ler arquivo! ----");
        }
    } 
}
