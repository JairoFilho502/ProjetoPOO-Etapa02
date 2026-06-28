package financeiro;

import interfaces.Exportavel;
import excecoes.PagamentoInvalidoException;

public abstract class Pagamento implements Exportavel {
    protected int    indiceConsulta;
    protected double valorBase;

    public Pagamento(int indiceConsulta, double valorBase) {
        this.indiceConsulta = indiceConsulta;
        this.valorBase      = valorBase;
    }

    public int    getIndiceConsulta() { return indiceConsulta; }
    public double getValorBase()      { return valorBase; }

    public void setIndiceConsulta(int i) { this.indiceConsulta = i; }

    public void setValorBase(double v) {
        if (v >= 0) this.valorBase = v;
        else System.out.println("Erro: Valor nao pode ser negativo.");
    }

    public abstract double calcularValorFinal();

    public String exibirResumo() {
        return "Consulta #" + indiceConsulta + " | Valor Base: R$" + valorBase;
    }

    @Override
    public String exportarDados() {
        return "Pagamento | Consulta#" + indiceConsulta
                + " | Valor Final: R$" + calcularValorFinal();
    }

    // Metodos fabrica — permitem criar subclasses fora do pacote financeiro
    public static Pagamento criarDinheiro(int idx, double valor) {
        return new PagamentoDinheiro(idx, valor);
    }

    public static Pagamento criarCartao(int idx, double valor, int parcelas) {
        return new PagamentoCartao(idx, valor, parcelas);
    }

    public static Pagamento criarConvenio(int idx, double valor, String especialidade, double taxa) {
        return new PagamentoConvenio(idx, valor, especialidade, taxa);
    }
}

// -------------------------------------------------------

class PagamentoDinheiro extends Pagamento {

    public PagamentoDinheiro(int indiceConsulta, double valorBase) {
        super(indiceConsulta, valorBase);
    }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * 0.95; // 5% de desconto dinheiro/PIX
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Tipo: Dinheiro/PIX | Desconto: 5%";
    }
}

// -------------------------------------------------------

class PagamentoCartao extends Pagamento {
    private int parcelas;

    public PagamentoCartao(int indiceConsulta, double valorBase, int parcelas) {
        super(indiceConsulta, valorBase);
        if (parcelas < 1 || parcelas > 6)
            throw new PagamentoInvalidoException(
                    "Parcelas invalidas: deve ser entre 1 e 6. Informado: " + parcelas);
        this.parcelas = parcelas;
    }

    public int getParcelas() { return parcelas; }

    public void setParcelas(int p) {
        if (p < 1 || p > 6)
            throw new PagamentoInvalidoException("Parcelas invalidas: deve ser entre 1 e 6.");
        this.parcelas = p;
    }

    @Override
    public double calcularValorFinal() {
        if (parcelas <= 3) return getValorBase();
        double taxaJuros = (parcelas - 3) * 0.025;
        return getValorBase() * (1 + taxaJuros);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo() + " | Tipo: Cartao | Parcelas: " + parcelas + "x";
    }
}

// -------------------------------------------------------

class PagamentoConvenio extends Pagamento {
    private String especialidadeConsulta;
    private double taxaCobertura;

    public PagamentoConvenio(int indiceConsulta, double valorBase,
                             String especialidadeConsulta, double taxaCobertura) {
        super(indiceConsulta, valorBase);
        this.especialidadeConsulta = especialidadeConsulta;
        this.taxaCobertura         = taxaCobertura;
    }

    public String getEspecialidadeConsulta() { return especialidadeConsulta; }
    public double getTaxaCobertura()         { return taxaCobertura; }

    @Override
    public double calcularValorFinal() {
        return getValorBase() * (1 - taxaCobertura);
    }

    @Override
    public String exibirResumo() {
        return super.exibirResumo()
                + " | Tipo: Convenio | Especialidade: " + especialidadeConsulta
                + " | Cobertura: " + (int)(taxaCobertura * 100) + "%";
    }
}
