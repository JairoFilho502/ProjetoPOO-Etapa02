package interfaces_horario;

import excecoes.OperacaoInvalidaException;

public interface Agendavel {
    void agendar();
    void cancelar() throws OperacaoInvalidaException;
    void remarcar() throws OperacaoInvalidaException;
}