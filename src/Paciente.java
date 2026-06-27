// R3: Paciente estende Pessoa - antes NÃO estendia, corrigido na refatoração
public class Paciente extends Pessoa {

    // R1: todos os atributos privados
    private String cpf;
    private int idade;
    private String telefone;
    private String convenioNome;
    private boolean ativo;

    // SOBRECARGA de construtores (R4)
    public Paciente(String nome, String cpf) {
        super(nome);
        this.cpf = cpf;
        this.idade = 0;
        this.telefone = "";
        this.convenioNome = "";
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone) {
        super(nome);
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.convenioNome = "";
        this.ativo = true;
    }

    public Paciente(String nome, String cpf, int idade, String telefone, String convenioNome) {
        super(nome);
        this.cpf = cpf;
        this.idade = idade;
        this.telefone = telefone;
        this.convenioNome = convenioNome;
        this.ativo = true;
    }

    // R1: getters
    public String getCpf() { return cpf; }
    public int getIdade() { return idade; }
    public String getTelefone() { return telefone; }
    public String getConvenioNome() { return convenioNome; }
    public boolean isAtivo() { return ativo; }

    // R1: setters com validação (2 setters com validação aqui)
    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF não pode ser vazio.");
        }
        this.cpf = cpf.trim();
    }

    public void setIdade(int idade) {
        if (idade < 0 || idade > 150) {
            throw new IllegalArgumentException("Idade inválida: " + idade);
        }
        this.idade = idade;
    }

    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setConvenioNome(String convenioNome) { this.convenioNome = convenioNome; }

    // SOBRECARGA de métodos (R4)
    public void complementar(int idade, String telefone) {
        setIdade(idade);
        this.telefone = telefone;
    }

    public void complementar(int idade, String telefone, String convenioNome) {
        setIdade(idade);
        this.telefone = telefone;
        this.convenioNome = convenioNome;
    }

    public void desativar() {
        this.ativo = false;
    }

    // R4: sobrescrita de exibirResumo (declarado abstract em Pessoa)
    @Override
    public String exibirResumo() {
        String status = ativo ? "Sim" : "Nao";
        return "Nome: " + getNome() + " | CPF: " + cpf + " | Idade: " + idade
                + " | Tel: " + telefone + " | Convenio: " + convenioNome
                + " | Ativo: " + status;
    }
}
