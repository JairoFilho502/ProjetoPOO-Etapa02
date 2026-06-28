package excecoes;

// runtimeexception  erros de pagamento sem obrigar o try-catch
public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String mensagem) { super(mensagem); }
    public PagamentoInvalidoException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
