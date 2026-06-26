// R9: Exceção personalizada - lançada para forma inválida ("cheque"), parcelas fora do range (1-6)
//     ou valor negativo
public class PagamentoInvalidoException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public PagamentoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public PagamentoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
