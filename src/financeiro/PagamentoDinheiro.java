// REQUISITO R4: Comentario obrigatorio para a banca
// SOBRESCRITA: aqui vai calcular o valor final com desconto de 5% pros pagamentos em dinheiro ou pix.

public class PagamentoDinheiro extends Pagamento {

    public PagamentoDinheiro(int indiceConsulta, double valorBase) {
        super(indiceConsulta, valorBase);
    }

    @Override
    public double calcularValorFinal() {
        // REQUISITO 3.4: Regra de 5% de desconto para Dinheiro/PIX
        // aqui calcula o valor final aplicanto o desconto de 5% sobre o valor base
        double desconto = getValorBase() * 0.05;
        return getValorBase() - desconto;
    }
}