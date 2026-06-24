import java.util.Objects;

public class HorarioDisponivel {
    private String diaSemana;
    private String turno; 

    public HorarioDisponivel(String diaSemana, String turno) {
        setDiaSemana(diaSemana);
        setTurno(turno);
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        if (diaSemana == null || diaSemana.trim().isEmpty()) {
            throw new IllegalArgumentException("Dia da semana não pode ser vazio.");
        }
        this.diaSemana = diaSemana.trim();
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        if (turno == null || turno.trim().isEmpty()) {
            throw new IllegalArgumentException("Turno não pode ser vazio.");
        }

        String valor = turno.trim().toLowerCase();

        if (!valor.equals("manha") && !valor.equals("manhã") && !valor.equals("tarde")) {
            throw new IllegalArgumentException("Turno inválido. Use manhã ou tarde.");
        }

        if (valor.equals("manha")) {
            valor = "manhã";
        }

        this.turno = valor;
    }

    @Override
    public String toString() {
        return diaSemana + " - " + turno;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HorarioDisponivel)) return false;
        HorarioDisponivel outro = (HorarioDisponivel) obj;
        return diaSemana.equalsIgnoreCase(outro.diaSemana)
                && turno.equalsIgnoreCase(outro.turno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaSemana.toLowerCase(), turno.toLowerCase());
    }
}