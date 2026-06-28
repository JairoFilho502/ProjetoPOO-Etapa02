public abstract class Pessoa {
    private String nome;

    public Pessoa(String nome) {
        setNome(nome);
    }

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome nao pode ser vazio.");
        this.nome = nome.trim();
    }

    public abstract String exibirResumo();
}
