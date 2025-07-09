package crud.prontuario.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Exame {
	
	@Override
	public String toString() {
		return "Exame [id=" + id + ", descricao=" + descricao + ", data=" + data + ", pacienteId=" + pacienteId + "]";
	}

	private long id;
	private String descricao;
	private LocalDateTime data;
	private long pacienteId;
	
	public Exame() {
		super();
	}
	
	public Exame(long id, String descricao, LocalDateTime data) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.data = data;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exame other = (Exame) obj;
		return id == other.id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	public long getPacienteId() {
	    return pacienteId;
	}

	public void setPacienteId(long pacienteId) {
	    this.pacienteId = pacienteId;
	}
	

}
