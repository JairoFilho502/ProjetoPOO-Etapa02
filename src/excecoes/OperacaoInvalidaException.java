package excecoes;

// para quando alguem tenta fazer algo que nao faz sentido no sistema
public class OperacaoInvalidaException extends Exception {
    public OperacaoInvalidaException(String mensagem) { super(mensagem); }
    public OperacaoInvalidaException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
