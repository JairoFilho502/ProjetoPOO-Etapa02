public class PagamentoConvenio extends Pagamento {
    private String especialidadeConsulta;
    private double taxaCoberturaTemporaria; 

    public PagamentoConvenio(int indiceConsulta, double valorBase, String especialidadeConsulta, double taxaCoberturaTemporaria) {
        super(indiceConsulta, valorBase); 
        this.especialidadeConsulta = especialidadeConsulta;
        this.taxaCoberturaTemporaria = taxaCoberturaTemporaria;
    }
    @Override
    public double calcularValorFinal() { 
        double desconto = getValorBase() * taxaCoberturaTemporaria;
        return getValorBase() - desconto;
    }
}

 