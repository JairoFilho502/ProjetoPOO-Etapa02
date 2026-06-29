package excecoes;

// lancada quando a gente tenta buscar uma consulta que nao existe
public class ConsultaNaoEncontradaException extends Exception {
    // mensagem de erro
    public ConsultaNaoEncontradaException(String mensagem) { super(mensagem); }
    //  causa original do erro
    public ConsultaNaoEncontradaException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
