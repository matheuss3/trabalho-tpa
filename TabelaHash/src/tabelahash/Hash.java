package tabelahash;

import java.io.*;
import java.util.ArrayList;

public class Hash {
    ArrayList<Elemento> hashContato;
    static int MAXtamanho = 4321;
    public int qtdColisoes;
     
    public Hash(){
        
        hashContato = new ArrayList<>();
        this.inicializaTabela(); 
        this.qtdColisoes = 0;
    }
    
    public void inicializaTabela(){  // inicializando tabela hash
        for (int i=0; i<MAXtamanho; i++) {
            Elemento e = new Elemento();
            e.c = null;
            e.ocupado = false;   
            e.listaproximos = new ArrayList<>();
            hashContato.add(e);
        }
    }

    private int calculaChave(String nomeContato) {  //Calculando chave para guardar os contatos na tabela
        int chave = 0;
        int i = 1;
        for (char letra : nomeContato.toCharArray()) { // Converte a String em um array de caracteres "abc" -> ['a', 'b', 'c']
            int ascii =  letra;
            chave += i * ascii;
            i++;
        }
        
        while (chave >= MAXtamanho){  //para não passar do tamanho da tabela
            chave -= MAXtamanho;
            if (chave < 0){
                return 0;
            }
        }

        return chave;
    }
    
    public void trataColisao(Elemento e, Contato c){
        
        e.listaproximos.add(c);
    }
    
    public void imprimeContato(Contato c){
        System.out.println("Imprimindo contato de " + c.getNomeCompleto());
        System.out.println("Telefone: " + c.getTelefone());
        System.out.println("Cidade: " + c.getCidade());
        System.out.println("País: " + c.getPais());
    }

    public void inserir(Contato c) {  //insere novo contato
        
        int chave = calculaChave(c.getNomeCompleto());
        System.out.println(chave + " = " + c.getNomeCompleto());
        Elemento e = hashContato.get(chave);  //pega o elemento armazenado na hash

        Contato c1 = localizar(c.getNomeCompleto());
        if (c1 != null) { //nao permite contato com o mesmo nome
//            System.out.println("Contato Existente");
            return;
        }

        if (!e.ocupado){ //posição vazia
            e.ocupado = true;    //seta posição para ocupado
            e.c = c;    //armazena o contato
        }
        else{//tratar colisão
            System.out.println("Tratando colisão");
            this.qtdColisoes ++;
            trataColisao(e,c); //preciso percorrer os proximos nós ate q seja null, dps insiro onde for null
        }
    }

    public Contato localizar(String nome){  //localiza contato
        
        int chave = calculaChave(nome);
        Elemento e = hashContato.get(chave);

        if (!e.ocupado) return null;

        if (e.c.getNomeCompleto().equals(nome)) return e.c;

        for (Contato cont : e.listaproximos){
            if (cont.getNomeCompleto().equals(nome)){
                return cont;
            }
        }

        return null;
    }
    
    public void excluir(String nome){  //exclui contato
        
        int chave = calculaChave(nome);
        Elemento e = hashContato.get(chave);
        
        if (e.listaproximos.isEmpty()){
            e.c = null;
            e.ocupado = false;
        }
        else{
            Contato cont = localizar(nome);
            e.listaproximos.remove(cont);
        }
    }

    public void atualizar(String tel, String cid, String p, String nome){  //atualiza contato
      
        Contato cont = localizar(nome);
        cont.setTelefone(tel);
        cont.setCidade(cid);
        cont.setPais(p); 
    }
    
    public int qtdContatosTabela(){
        
        int qtd = 0;
        for (Elemento e : this.hashContato){
            if (e.ocupado){  //tem contato dentro
                qtd += 1 + e.listaproximos.size();
            }
        } 
        return qtd;
    }

    public void salvar(){ //salva dados
        
        try{       
            FileWriter fw = new FileWriter("./arquivos/contatos.csv");
            BufferedWriter bw = new BufferedWriter(fw);

            for (Elemento e : this.hashContato){
                if (e.ocupado) bw.write(e.c.contatoToString() + "\n");

                if (!e.listaproximos.isEmpty()){
                    for (Contato cont : e.listaproximos){
                        bw.write(cont.contatoToString() + "\n");
                    }
                }
            }
            bw.close();
            System.out.println("Contato salvo com sucesso!");
        }
        catch(IOException e){ 
            System.out.println("---- Erro ao tentar salvar no arquivo! ----");
        }
    }
    
    public void ler(){ //Vou ler o arquivo de contatos (Agenda) e salvar no array
        try{
            
            FileReader fr = new FileReader("./arquivos/contatos.csv");
            BufferedReader br = new BufferedReader(fr);
            String linha = br.readLine();
            int i = 1;
            System.out.println(i);
            while (linha != null){
                System.out.println(++i);
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
