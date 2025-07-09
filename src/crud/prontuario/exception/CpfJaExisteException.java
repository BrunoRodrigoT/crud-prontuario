package crud.prontuario.exception;

public class CpfJaExisteException extends Exception {
    private static final long serialVersionUID = 1L;

    public CpfJaExisteException(String cpf) {
        super("Já existe um paciente cadastrado com o CPF: " + cpf);
    }
}
