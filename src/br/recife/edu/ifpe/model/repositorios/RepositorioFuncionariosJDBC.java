package br.recife.edu.ifpe.model.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.recife.edu.ifpe.controller.db.DB;
import br.recife.edu.ifpe.controller.db.DbException;
import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.dao.DaoFactory;

public class RepositorioFuncionariosJDBC {

	private Connection conn;
	
	public RepositorioFuncionariosJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert(Funcionario func) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_funcionario(nome, departamento) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, func.getNome());
			st.setString(2, func.getDepartamento());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					func.setCodigo(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}
		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			DB.closeStatement(st);
		}
	}
	
	public void update(Funcionario func) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_funcionario SET nome = ?, departamento = ? WHERE codigo = ?");
			
			st.setString(1, func.getNome());
			st.setString(2, func.getDepartamento());
			st.setInt(3, func.getCodigo());
			
			st.executeUpdate();	
			
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}		
	}
	
	public void delete(Integer codigo) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE from tb_funcionario WHERE codigo = ?");
			
			st.setInt(1, codigo);
			
			st.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}
	}
	
	public Funcionario findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_funcionario WHERE codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Funcionario prod = instantiateFuncionario(rs);
				
				return prod;
			} else {
				throw new DbException("Funcionario não encontrado!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<Funcionario> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Funcionario> list = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT * FROM tb_funcionario");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Funcionario func = instantiateFuncionario(rs);
				list.add(func);
			}			
			return list;
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Funcionario instantiateFuncionario(ResultSet rs) throws SQLException{
		Funcionario func = new Funcionario();
		func.setCodigo(rs.getInt("codigo"));
		func.setNome(rs.getString("nome"));
		func.setDepartamento(rs.getString("departamento"));
		return func;
	}
}
