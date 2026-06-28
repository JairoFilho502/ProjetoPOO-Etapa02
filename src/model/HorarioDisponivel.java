package model;

import java.util.Objects;

public class HorarioDisponivel {
    private String diaSemana;
    private String turno;

    public HorarioDisponivel(String diaSemana, String turno) {
        setDiaSemana(diaSemana);
        setTurno(turno);
    }

    public String getDiaSemana() { return diaSemana; }
    public String getTurno()     { return turno; }

    public void setDiaSemana(String diaSemana) {
        if (diaSemana == null || diaSemana.trim().isEmpty())
            throw new IllegalArgumentException("Dia da semana nao pode ser vazio.");
        this.diaSemana = diaSemana.trim().toLowerCase();
    }

    public void setTurno(String turno) {
        if (turno == null || turno.trim().isEmpty())
            throw new IllegalArgumentException("Turno nao pode ser vazio.");
        String v = turno.trim().toLowerCase();
        if (!v.equals("manha") && !v.equals("tarde"))
            throw new IllegalArgumentException("Turno invalido. Use manha ou tarde.");
        this.turno = v;
    }

    @Override
    public String toString() { return diaSemana + " - " + turno; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof HorarioDisponivel)) return false;
        HorarioDisponivel o = (HorarioDisponivel) obj;
        return diaSemana.equalsIgnoreCase(o.diaSemana) && turno.equalsIgnoreCase(o.turno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diaSemana.toLowerCase(), turno.toLowerCase());
    }
}
