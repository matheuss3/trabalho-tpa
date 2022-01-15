package tabelahash;

import java.util.ArrayList;

public class Hash {
    ArrayList<Elemento> hashContato;
    static int MAXtamanho = 2000;
     
    public Hash(){
        
        hashContato = new ArrayList<>();
        this.inicializaTabela();       
    }
    
    public void inicializaTabela(){  // inicializando tabela hash
        for (int i=0; i<MAXtamanho; i++) {
            Elemento e = new Elemento();
            e.ocupado = false;
            hashContato.add(e);
        }
    }

    private int calculaChave(String nomeContato) {
        int chave = 0;
        int i = 1;
        for (char letra : nomeContato.toCharArray()) { // Converte a String em um array de caracteres "abc" -> ['a', 'b', 'c']
            int ascii =  letra;
            chave += i * letra;
            i++;
        }

        System.out.println("Chave:  " + chave);
        return chave;
    }


    
//    public void setOcupado(int chave) {


    public void inserir(Contato c) {  //insere novo contato
        int chave = calculaChave(c.nomeCompleto);
        Elemento e = hashContato.get(chave);
        e.ocupado = true;
        e.c = c;
    }

    public Contato localizar(String nome){  //localiza contato
        int chave = calculaChave(nome);
        return hashContato.get(chave).c;
    }
//
//
//
//    public static void excluir(Hash hash, String nome){  //exclui contato
//
//    }
//
//    public static void atualizar(){  //atualiza contato
//
//    }
//
//    public static void salvar(){ //salva dados
//
//    }
    
}
