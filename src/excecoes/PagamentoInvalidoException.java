// R9 — excecao customizada para pagamentos invalidos (parcelas fora do limite, valor negativo)
public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }
    public PagamentoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
