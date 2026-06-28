// escrevendo o requisito de excecao customizada 
public class PagamentoInvalidoException extends RuntimeException {
    public PagamentoInvalidoException(String mensagem) { 
        super(mensagem);
    }
}