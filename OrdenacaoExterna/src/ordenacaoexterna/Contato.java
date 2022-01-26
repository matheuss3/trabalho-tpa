package ordenacaoexterna;

public class Contato implements Comparable<Contato> {
    private String nome;
    private String telefone;
    private String cidade;
    private String pais;

    public Contato(String nome, String telefone, String cidade, String pais) {
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setCidade(cidade);
        this.setPais(pais);
    }

    public Contato(String linhaArquivo) {
        String[] atributos = linhaArquivo.strip().split(",");

        this.setNome(atributos[0]);
        this.setTelefone(atributos[1]);
        this.setCidade(atributos[2]);
        this.setPais(atributos[3]);
    }

    @Override
    public int compareTo(Contato c) {
        return this.getNome().compareTo(c.getNome());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
