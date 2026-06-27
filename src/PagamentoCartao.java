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
        //aplicando excecao aqui se as parcelas forem invalidas
        if(parcelas <1 || parcelas > 6) {
            throw new PagamentoInvalidoException("Erro: O número de parcelas deve ser entre 1x e 6x" + " Tentativa de" + parcelas + "x");
        }
        if (parcelas <=3) {
            return getValorBase();
        }
        int parcelasExtras = parcelas - 3;
        double taxaJuros = parcelasExtras * 0.025;

        return getValorBase() + (getValorBase() * taxaJuros); 
    }
}