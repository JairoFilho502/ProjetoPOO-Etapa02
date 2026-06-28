public class PagamentoDinheiro extends Pagamento {

    public PagamentoDinheiro(int indiceConsulta, double valorBase) {
        super(indiceConsulta, valorBase);
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * 0.95;
    }
}
