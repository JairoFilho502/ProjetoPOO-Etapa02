package interfaces;

// coisas agendaveis implementam esses tres metodos
public interface Agendavel {
    void agendar();   // marca como agendado
    void cancelar();  // cancela o agendamento
    void remarcar();  // muda pra outro horario
}
