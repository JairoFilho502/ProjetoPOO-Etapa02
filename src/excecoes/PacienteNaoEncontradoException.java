// R9: Exceção personalizada - lançada quando CPF informado não existe no sistema
public class PacienteNaoEncontradoException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public PacienteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public PacienteNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
