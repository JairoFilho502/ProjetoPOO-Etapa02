// R6: classe abstrata - não pode ser instanciada diretamente
public abstract class Pessoa {

    // R1: atributo privado - acesso apenas via getter/setter
    private String nome;

    public Pessoa(String nome) {
        setNome(nome);
    }

    // R1: getter
    public String getNome() {
        return nome;
    }

    // R1: setter com validação (obrigatório ao menos 2 setters com validação no projeto)
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    // R4: sobrescrita obrigatória em todas as subclasses
    public abstract String exibirResumo();
}
