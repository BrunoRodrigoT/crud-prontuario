package crud.prontuario.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crud.prontuario.database.IConnection;
import crud.prontuario.exception.CpfJaExisteException;
import crud.prontuario.model.Paciente;

public class PacienteDAO implements IEntityDAO<Paciente> {
	
	private IConnection conn;
	
	public PacienteDAO(IConnection connection) {
		this.conn = connection;
	}

	public void create(Paciente paciente) throws CpfJaExisteException {
	    if (cpfJaCadastrado(paciente.getCpf())) {
	        throw new CpfJaExisteException(paciente.getCpf());
	    }

	    String sql = "INSERT INTO paciente (nome, cpf) VALUES (?, ?)";
	    try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
	        pstm.setString(1, paciente.getNome());
	        pstm.setString(2, paciente.getCpf());
	        pstm.execute();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public Paciente findById(Long id) {
	    String sql = "SELECT id, nome, cpf FROM paciente WHERE id = ?";
	    Paciente p = null;
	    try {
	    	PreparedStatement pstm = conn.getConnection().prepareStatement(sql);
	        pstm.setLong(1, id);

	        ResultSet rs = pstm.executeQuery();
	        while (rs.next()) {
	            p =  new Paciente();
	            p.setId(rs.getLong("id")); 
	            p.setCpf(rs.getString("nome"));
	            p.setNome(rs.getString("cpf"));  
	        }
	        pstm.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return p;
	}

	@Override
	public void delete(Paciente t) {
	    String sql = "DELETE FROM paciente WHERE id = ?";
	    try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
	        pstm.setLong(1, t.getId());
	        pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public List<Paciente> findAll() {
	    String sql = "SELECT id, nome, cpf FROM paciente";
	    List<Paciente> pacientes = new ArrayList<>();
	    try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
	        var rs = pstm.executeQuery();
	        while (rs.next()) {
	            Paciente paciente = new Paciente(
	                rs.getLong("id"),
	                rs.getString("nome"),
	                rs.getString("cpf")
	            );
	            pacientes.add(paciente);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pacientes;
	}


	@Override
	public void update(Paciente paciente) throws CpfJaExisteException {
	    if (cpfJaCadastradoParaOutro(paciente.getId(), paciente.getCpf())) {
	        throw new CpfJaExisteException(paciente.getCpf());
	    }

	    String sql = "UPDATE paciente SET nome = ?, cpf = ? WHERE id = ?";
	    try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
	        pstm.setString(1, paciente.getNome());
	        pstm.setString(2, paciente.getCpf());
	        pstm.setLong(3, paciente.getId());
	        pstm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private boolean cpfJaCadastrado(String cpf) {
	    String sql = "SELECT COUNT(*) FROM paciente WHERE cpf = ?";
	    try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
	        pstm.setString(1, cpf);
	        ResultSet rs = pstm.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	private boolean cpfJaCadastradoParaOutro(Long pacienteId, String cpf) {
	    String sql = "SELECT COUNT(*) FROM paciente WHERE cpf = ? AND id <> ?";
	    try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
	        pstm.setString(1, cpf);
	        pstm.setLong(2, pacienteId);
	        ResultSet rs = pstm.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
}
