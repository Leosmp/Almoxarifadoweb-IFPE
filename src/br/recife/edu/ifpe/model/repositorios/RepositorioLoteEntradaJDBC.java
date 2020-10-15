/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import br.recife.edu.ifpe.model.classes.LoteEntrada;

public class RepositorioLoteEntradaJDBC {

	private Connection conn;
	
	public RepositorioLoteEntradaJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert(LoteEntrada loteEntrada) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_lote_entrada(data, quantidadeTotal) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setDate(1, new java.sql.Date(loteEntrada.getData().getTime()));
			st.setInt(2, loteEntrada.getQuantidadeTotal());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					loteEntrada.setCodigo(id);
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
	
	public void update(LoteEntrada loteEntrada) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_lote_entrada SET data = ?, quantidadeTotal = ? WHERE codigo = ?");
			
			st.setDate(1, new java.sql.Date(loteEntrada.getData().getTime()));
			st.setInt(2, loteEntrada.getQuantidadeTotal());
			st.setInt(3, loteEntrada.getCodigo());
			
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
			st = conn.prepareStatement("DELETE from tb_lote_entrada WHERE codigo = ?");
			
			st.setInt(1, codigo);
			
			st.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}
	}
	
	public LoteEntrada findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_lote_entrada WHERE codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				LoteEntrada loteEntrada = instantiateLoteEntrada(rs);
				
				return loteEntrada;
			} else {
				throw new DbException("LoteEntrada não encontrado!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<LoteEntrada> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		List<LoteEntrada> list = new ArrayList<>();
		
		try {
			st = conn.prepareStatement("SELECT * FROM tb_lote_entrada");
			rs = st.executeQuery();
			
			while(rs.next()) {
				LoteEntrada func = instantiateLoteEntrada(rs);
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
	
	private LoteEntrada instantiateLoteEntrada(ResultSet rs) throws SQLException{
		LoteEntrada loteEntrada = new LoteEntrada();
		loteEntrada.setCodigo(rs.getInt("codigo"));
		loteEntrada.setData(new java.util.Date(rs.getTimestamp("data").getTime()));
		loteEntrada.setQuantidadeTotal(rs.getInt("quantidadeTotal"));
		return loteEntrada;
	}
}