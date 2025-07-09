package crud.prontuario.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crud.prontuario.database.IConnection;
import crud.prontuario.exception.ExameConflitanteException;
import crud.prontuario.model.Exame;

public class ExameDAO implements IEntityDAO<Exame> {

    private IConnection conn;

    public ExameDAO(IConnection connection) {
        this.conn = connection;
    }

    @Override
    public void create(Exame t) throws ExameConflitanteException {
        if (existeConflitoHorario(t)) {
            throw new ExameConflitanteException(t.getPacienteId());
        }

        String sql = "INSERT INTO exame (descricao, data, paciente_id) VALUES (?, ?, ?)";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            pstm.setString(1, t.getDescricao());
            pstm.setTimestamp(2, java.sql.Timestamp.valueOf(t.getData()));
            pstm.setLong(3, t.getPacienteId());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Exame findById(Long id) {
        String sql = "SELECT id, descricao, data, paciente_id FROM exame WHERE id = ?";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            pstm.setLong(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                Exame exame = new Exame();
                exame.setId(rs.getLong("id"));
                exame.setDescricao(rs.getString("descricao"));
                exame.setData(rs.getTimestamp("data").toLocalDateTime());
                exame.setPacienteId(rs.getLong("paciente_id"));
                return exame;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Exame t) throws ExameConflitanteException {
        if (existeConflitoHorarioAtualizacao(t)) {
            throw new ExameConflitanteException(t.getPacienteId());
        }

        String sql = "UPDATE exame SET descricao = ?, data = ?, paciente_id = ? WHERE id = ?";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            pstm.setString(1, t.getDescricao());
            pstm.setTimestamp(2, java.sql.Timestamp.valueOf(t.getData()));
            pstm.setLong(3, t.getPacienteId());
            pstm.setLong(4, t.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Exame> findAll() {
        List<Exame> exames = new ArrayList<>();
        String sql = "SELECT id, descricao, data, paciente_id FROM exame";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Exame exame = new Exame();
                exame.setId(rs.getLong("id"));
                exame.setDescricao(rs.getString("descricao"));
                exame.setData(rs.getTimestamp("data").toLocalDateTime());
                exame.setPacienteId(rs.getLong("paciente_id"));
                exames.add(exame);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exames;
    }

    @Override
    public void delete(Exame t) {
        String sql = "DELETE FROM exame WHERE id = ?";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            pstm.setLong(1, t.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean existeConflitoHorario(Exame t) {
        String sql = "SELECT COUNT(*) FROM exame WHERE paciente_id = ? AND data = ?";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            pstm.setLong(1, t.getPacienteId());
            pstm.setTimestamp(2, java.sql.Timestamp.valueOf(t.getData()));
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean existeConflitoHorarioAtualizacao(Exame t) {
        String sql = "SELECT COUNT(*) FROM exame WHERE paciente_id = ? AND data = ? AND id <> ?";
        try (PreparedStatement pstm = conn.getConnection().prepareStatement(sql)) {
            pstm.setLong(1, t.getPacienteId());
            pstm.setTimestamp(2, java.sql.Timestamp.valueOf(t.getData()));
            pstm.setLong(3, t.getId());
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
