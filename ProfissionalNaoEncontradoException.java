package excecoes;

// lancada quando buscamos um paciente pelo cpf e ele nao existe
public class PacienteNaoEncontradoException extends Exception {
    public PacienteNaoEncontradoException(String mensagem) { super(mensagem); }
    public PacienteNaoEncontradoException(String mensagem, Throwable causa) { super(mensagem, causa); }
}
