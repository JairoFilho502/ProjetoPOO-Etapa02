public class PagamentoCartao extends Pagamento {
    private int parcelas;

    public PagamentoCartao(int indiceConsulta, double valorBase, int parcelas) {
        super(indiceConsulta, valorBase);
        this.parcelas = parcelas;
    }

    public int getParcelas() { return parcelas; }
    public void setParcelas(int parcelas) { this.parcelas = parcelas; }

    @Override
    public double calcularValorFinal() {
        if (parcelas < 1 || parcelas > 6) {
            System.out.println("Erro: Parcelas deve ser entre 1 e 6.");
            return 0.0;
        }
        if (parcelas <= 3) return getValorBase();
        double taxaJuros = (parcelas - 3) * 0.025;
        return getValorBase() + (getValorBase() * taxaJuros);
    }
}
