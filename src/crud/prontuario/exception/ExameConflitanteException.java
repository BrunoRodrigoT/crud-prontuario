package crud.prontuario.exception;

public class ExameConflitanteException extends Exception {
    private static final long serialVersionUID = 1L;

    public ExameConflitanteException(Long pacienteId) {
        super("O paciente com ID " + pacienteId + " já possui um exame marcado para o mesmo horário.");
    }
}
