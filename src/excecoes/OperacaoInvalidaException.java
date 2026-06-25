// R9: Exceção personalizada - lançada ao tentar cancelar consulta já realizada/cancelada
//     ou registrar atendimento em consulta não agendada
public class OperacaoInvalidaException extends Exception {

    // SOBRECARGA: mesmo nome de construtor, parâmetros diferentes (R4)
    public OperacaoInvalidaException(String mensagem) {
        super(mensagem);
    }

    public OperacaoInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
