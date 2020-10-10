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
import br.recife.edu.ifpe.model.classes.Produto;

public class RepositorioProdutosJDBC {

	private Connection conn;
	
	public RepositorioProdutosJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert(Produto p) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_produto(nome, marca, categoria, descricao) VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, p.getNome());
			st.setString(2, p.getMarca());
			st.setString(3, p.getCategoria());
			st.setString(4, p.getDescricao());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					p.setCodigo(id);
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
	
	public void update(Produto p) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_produto SET nome = ?, marca = ?, categoria = ?, descricao = ? WHERE codigo = ?");
			
			st.setString(1, p.getNome());
			st.setString(2, p.getMarca());
			st.setString(3, p.getCategoria());
			st.setString(4, p.getDescricao());
			st.setInt(5, p.getCodigo());
			
			st.executeUpdate();	
			
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}		
	}
	
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_produto WHERE codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Produto prod = instantiateProduto(rs);
				
				return prod;
			} else {
				throw new DbException("Produto não encontrado!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<Produto> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Produto> list = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT * FROM tb_produto");
			rs = st.executeQuery();
			
			while(rs.next()) {
				Produto prod = instantiateProduto(rs);
				list.add(prod);
			}			
			return list;
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Produto instantiateProduto(ResultSet rs) throws SQLException{
		Produto p = new Produto();
		p.setCodigo(rs.getInt("codigo"));
		p.setNome(rs.getString("nome"));
		p.setMarca(rs.getString("marca"));
		p.setCategoria(rs.getString("categoria"));
		p.setDescricao(rs.getString("descricao"));
		return p;
	}
}
