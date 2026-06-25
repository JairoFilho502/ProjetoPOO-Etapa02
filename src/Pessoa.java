public abstract class Pessoa {
    public String nome;


    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String exibirResumo() {
        return "nome: " + nome;
    }
    
}


// criação de uma superclasse abstrata pessoa para servir na hierarquia em profissional e paciente
