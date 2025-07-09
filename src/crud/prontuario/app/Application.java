package crud.prontuario.app;

import crud.prontuario.dao.ExameDAO;
import crud.prontuario.dao.PacienteDAO;
import crud.prontuario.database.DatabaseConnectionPgSQL;
import crud.prontuario.exception.CpfJaExisteException;
import crud.prontuario.exception.ExameConflitanteException;
import crud.prontuario.model.Exame;
import crud.prontuario.model.Paciente;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Application {

    private static final Scanner scanner = new Scanner(System.in);
    private static final PacienteDAO pacienteDAO = new PacienteDAO(new DatabaseConnectionPgSQL());
    private static final ExameDAO exameDAO = new ExameDAO(new DatabaseConnectionPgSQL());

    public static void main(String[] args) {
        int opcao = -1;
        do {
            System.out.println("\n--- Sistema de Gerenciamento de Pacientes e Exames ---");
            System.out.println("1. Cadastrar Paciente");
            System.out.println("2. Listar Pacientes");
            System.out.println("3. Editar Paciente");
            System.out.println("4. Remover Paciente");
            System.out.println("5. Cadastrar Exame");
            System.out.println("6. Listar Exames");
            System.out.println("7. Editar Exame");
            System.out.println("8. Remover Exame");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
            	System.out.println("");
                System.out.println("Opção inválida! Digite um número inteiro.");
                continue;
            }

            switch (opcao) {
                case 1 -> cadastrarPaciente();
                case 2 -> listarPacientes();
                case 3 -> editarPaciente();
                case 4 -> removerPaciente();
                case 5 -> cadastrarExame();
                case 6 -> listarExames();
                case 7 -> editarExame();
                case 8 -> removerExame();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        Paciente paciente = new Paciente();
        paciente.setNome(nome);
        paciente.setCpf(cpf);
        try {
            pacienteDAO.create(paciente);
            System.out.println("Paciente cadastrado com sucesso!");
        } catch (CpfJaExisteException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void listarPacientes() {
        List<Paciente> pacientes = pacienteDAO.findAll();
        if (pacientes.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        } else {
            pacientes.forEach(System.out::println);
        }
    }

    private static void editarPaciente() {
        listarPacientes();
        System.out.print("ID do paciente a editar: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        System.out.print("Novo CPF: ");
        String cpf = scanner.nextLine();

        Paciente paciente = new Paciente(id, nome, cpf);
        try {
            pacienteDAO.update(paciente);
            System.out.println("Paciente atualizado com sucesso!");
        } catch (CpfJaExisteException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void removerPaciente() {
        listarPacientes();
        System.out.print("ID do paciente a remover: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }

        Paciente paciente = new Paciente();
        paciente.setId(id);
        pacienteDAO.delete(paciente);
        System.out.println("Paciente removido.");
    }

    private static void cadastrarExame() {
        listarPacientes();
        System.out.print("ID do paciente para o exame: ");
        long pacienteId;
        try {
            pacienteId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }

        System.out.print("Descrição do exame: ");
        String descricao = scanner.nextLine();
        System.out.print("Data e hora do exame (yyyy-MM-dd HH:mm): ");
        String dataStr = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime data;
        try {
            data = LocalDateTime.parse(dataStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data inválido!");
            return;
        }

        Exame exame = new Exame();
        exame.setDescricao(descricao);
        exame.setData(data);
        exame.setPacienteId(pacienteId);

        try {
            exameDAO.create(exame);
            System.out.println("Exame cadastrado com sucesso!");
        } catch (ExameConflitanteException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void listarExames() {
        List<Exame> exames = exameDAO.findAll();
        if (exames.isEmpty()) {
            System.out.println("Nenhum exame encontrado.");
        } else {
            exames.forEach(System.out::println);
        }
    }

    private static void editarExame() {
        listarExames();
        System.out.print("ID do exame a editar: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }

        System.out.print("Nova descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Nova data e hora (yyyy-MM-dd HH:mm): ");
        String dataStr = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime data;
        try {
            data = LocalDateTime.parse(dataStr, formatter);
        } catch (Exception e) {
            System.out.println("Formato de data inválido!");
            return;
        }

        System.out.print("ID do paciente: ");
        long pacienteId;
        try {
            pacienteId = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }

        Exame exame = new Exame(id, descricao, data);
        exame.setPacienteId(pacienteId);

        try {
            exameDAO.update(exame);
            System.out.println("Exame atualizado com sucesso!");
        } catch (ExameConflitanteException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void removerExame() {
        listarExames();
        System.out.print("ID do exame a remover: ");
        long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido!");
            return;
        }

        Exame exame = new Exame();
        exame.setId(id);
        exameDAO.delete(exame);
        System.out.println("Exame removido.");
    }
}