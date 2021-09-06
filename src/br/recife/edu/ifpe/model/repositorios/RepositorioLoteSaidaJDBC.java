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
import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.classes.ItemSaida;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.dao.DaoFactory;

public class RepositorioLoteSaidaJDBC {

	private static Connection conn;
	private static RepositorioLoteSaidaJDBC myself = null;
	
	private RepositorioLoteSaidaJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public static RepositorioLoteSaidaJDBC getCurrentInstance(Connection conn){
        if(myself == null)
            myself = new RepositorioLoteSaidaJDBC(conn);
        
        return myself;
    }

	public void insert(LoteSaida loteSaida) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_lote_saida(tb_funcionario_codigo, dataSaida) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, loteSaida.getResponsavel().getCodigo());
			st.setDate(2, new java.sql.Date(loteSaida.getData().getTime()));

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					loteSaida.setCodigo(id);
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
	
	public void update(LoteSaida loteSaida) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_lote_saida SET tb_funcionario_codigo = ?, dataSaida = ? WHERE codigo = ?");
			
			st.setInt(1, loteSaida.getResponsavel().getCodigo());
			st.setDate(2, new java.sql.Date(loteSaida.getData().getTime()));
			st.setInt(3, loteSaida.getCodigo());
			
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
			st = conn.prepareStatement("DELETE from tb_lote_saida WHERE codigo = ?");
			
			st.setInt(1, codigo);
			
			st.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}
	}
	
	public LoteSaida findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT tb_lote_saida.*, tb_funcionario.nome, tb_funcionario.departamento " + 
					"FROM tb_lote_saida " + 
					"INNER JOIN tb_funcionario ON tb_funcionario_codigo = tb_funcionario.codigo " + 
					"WHERE tb_lote_saida.codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Funcionario funcionario = instantiateFuncionario(rs);
				LoteSaida loteSaida = instantiateLoteSaida(rs, funcionario);
				
				return loteSaida;
			} else {
				throw new DbException("LoteSaida não encontrado!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<LoteSaida> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		List<LoteSaida> list = new ArrayList<>();		
		
		try {
			st = conn.prepareStatement(
					"SELECT tb_lote_saida.*, tb_funcionario.nome, tb_funcionario.departamento "
					+ "FROM tb_lote_saida "
					+ "INNER JOIN tb_funcionario ON tb_funcionario_codigo = tb_funcionario.codigo");
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				Funcionario funcionario = instantiateFuncionario(rs);
				LoteSaida func = instantiateLoteSaida(rs, funcionario);
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
	
	private Funcionario instantiateFuncionario(ResultSet rs) throws SQLException {
		Funcionario func = new Funcionario();
		func.setCodigo(rs.getInt("tb_funcionario_codigo"));
		func.setNome(rs.getString("nome"));
		func.setDepartamento(rs.getString("departamento"));
		return func;
	}

	private LoteSaida instantiateLoteSaida(ResultSet rs, Funcionario funcionario) throws SQLException{
		LoteSaida loteSaida = new LoteSaida();
		List<ItemSaida> listItemSaida = DaoFactory.createItemSaidaJDBC().findItensDoLote(rs.getInt("codigo"));
		loteSaida.setCodigo(rs.getInt("codigo"));
		loteSaida.setResponsavel(funcionario);
		loteSaida.setData(new java.util.Date(rs.getTimestamp("dataSaida").getTime()));
		loteSaida.setItens(listItemSaida);
		return loteSaida;
	}
}