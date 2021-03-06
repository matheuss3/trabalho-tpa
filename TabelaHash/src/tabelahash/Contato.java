package tabelahash;

public class Contato {
    private String nomeCompleto;
    private String telefone;
    private String cidade;
    private String pais;
    
    public Contato(String nome, String tel, String cid, String pais){
        
        this.setNomeCompleto(nome);
        this.setTelefone(tel);
        this.setCidade(cid);
        this.setPais(pais);
    }
    
    public Contato(String linha){
        
        String l[] = linha.split(",");
        this.nomeCompleto = l[0];
        this.telefone = l[1];
        this.cidade = l[2];
        this.pais = l[3]; 
    }
    
    public String contatoToString(){
        
        return getNomeCompleto()+","+getTelefone()+","+getCidade()+","+getPais();
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
