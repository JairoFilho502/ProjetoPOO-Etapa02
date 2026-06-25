public class PagamentoCartao extends Pagamento {

    private int parcelas;

    public PagamentoCartao(int indiceConsulta, double valorBase, int parcelas) {
        super(indiceConsulta, valorBase); 
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return parcelas; 
    }
    public void setParcelas(int parcelas) {
        this.parcelas = parcelas; 
    }
    @Override
    public double calcularValorFinal() {
        if (parcelas <1 || parcelas >6) {
            System.out.println("Erro: Parcelamento inválido. O número de parcelas deve estar entre 1 e 6.");
            return 0.0;
        }
        if (parcelas <=3) {
            return getValorBase(); 
        }
        int parcelasExtras = parcelas - 3;
        double taxaJuros = parcelasExtras * 0.025; 

        double valorFinal = getValorBase() + (getValorBase() * taxaJuros);
        return valorFinal; 
    }
}