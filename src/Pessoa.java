public abstract class Pessoa {
    public String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String exibirResumo() {
        return "nome: " + nome;
    }
    
}


// criação de uma superclasse pessoa para servir de extends em profissional e paciente
