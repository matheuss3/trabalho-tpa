package ordenacaoexterna;

public class Contato implements Comparable<Contato> {
    /**
     * Atributos do modelo dos contatos presente no arquivo
     */
    private String nome;
    private String telefone;
    private String cidade;
    private String pais;

    /**
     * Construtor padrão do contato
     * @param nome
     * @param telefone
     * @param cidade
     * @param pais
     */
    public Contato(String nome, String telefone, String cidade, String pais) {
        this.setNome(nome);
        this.setTelefone(telefone);
        this.setCidade(cidade);
        this.setPais(pais);
    }

    /**
     * Construtor para construir o objeto contato através
     * do padrão da linha do arquivo
     * @param linhaArquivo
     */
    public Contato(String linhaArquivo) {
        String[] atributos = linhaArquivo.strip().split(",");

        /**
         * Condição criada pois as ultimas linhas dos aquivos
         * não possuem todos os atributos, então a verificação é feita
         */
        if (atributos.length >= 4) {
            this.setTelefone(atributos[1]);
            this.setCidade(atributos[2]);
            this.setPais(atributos[3]);
        } else {
            this.setTelefone("");
            this.setCidade("");
            this.setPais("");
        }

        /**
         * Se uma linha não possuir pelo menos o atributo nome o
         * sistema será finalizado
         */
        this.setNome(atributos[0]);

    }

    // Converte o contato em uma string no padrão para salva-la no arquivo de contatos
    public String contatoToString() {
        return getNome() + "," + getTelefone() + "," + getCidade() + "," +
                getPais();
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
