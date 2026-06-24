//Nao instanciem pagamentos diretamente 
public abstract class Pagamento {
    
    // Atributos privados para armazenar o indice da cunsulta e o valor base
    protected int indiceConsulta;
    protected double valorBase;
    
    public Pagamento(int indiceConsulta, double valorBase) {
        this.indiceConsulta = indiceConsulta;
        this.valorBase = valorBase;
    }

    // Getters e Setters
    public int getIndiceConsulta() { return indiceConsulta; }
    public void setIndiceConsulta(int indiceConsulta) { this.indiceConsulta = indiceConsulta; }
    public double getValorBase() { return valorBase; }
    
    public void setValorBase(double valorBase) { 
        if (valorBase >= 0) this.valorBase = valorBase; 
        else System.out.println("Erro: Valor não pode ser negativo.");
    }

    // O método calcularValorFinal será implementada nas classes filhas pq cada pagamento vai ter uma forma de calcular o valor
    public abstract double calcularValorFinal();
    
    // método concreto que exibe o resumo do pagamento e os valores base e final
    public String exibirResumo() {
        return "Consulta #" + indiceConsulta + " | Valor Base: R$" + valorBase;
    }
}