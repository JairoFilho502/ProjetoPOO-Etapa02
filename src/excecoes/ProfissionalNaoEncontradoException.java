package excecoes;

// lancada quando o profissional nao e achado
public class ProfissionalNaoEncontradoException extends Exception {
    public ProfissionalNaoEncontradoException(String mensagem) { super(mensagem); }
    public ProfissionalNaoEncontradoException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
