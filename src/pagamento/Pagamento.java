public abstract class Pagamento {
    protected int indiceConsulta;
    protected double valorBase;

    public Pagamento(int indiceConsulta, double valorBase) {
        this.indiceConsulta = indiceConsulta;
        setValorBase(valorBase);
    }

    public int getIndiceConsulta() { return indiceConsulta; }
    public void setIndiceConsulta(int indiceConsulta) { this.indiceConsulta = indiceConsulta; }
    public double getValorBase() { return valorBase; }

    public void setValorBase(double valorBase) {
        if (valorBase >= 0) this.valorBase = valorBase;
        else System.out.println("Erro: Valor nao pode ser negativo.");
    }

    public abstract double calcularValorFinal();

    public String exibirResumo() {
        return "Consulta #" + indiceConsulta + " | Valor Base: R$" + valorBase;
    }
}
