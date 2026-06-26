// R9: Exceção personalizada - lançada quando nome do profissional não existe no sistema
public class ProfissionalNaoEncontradoException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public ProfissionalNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProfissionalNaoEncontradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
