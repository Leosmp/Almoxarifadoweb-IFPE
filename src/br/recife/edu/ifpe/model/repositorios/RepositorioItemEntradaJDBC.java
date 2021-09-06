package br.recife.edu.ifpe.model.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.recife.edu.ifpe.controller.db.DB;
import br.recife.edu.ifpe.controller.db.DbException;
import br.recife.edu.ifpe.model.classes.ItemEntrada;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.Produto;

public class RepositorioItemEntradaJDBC {

	private static Connection conn;
	private static RepositorioItemEntradaJDBC myself = null;
	
	private RepositorioItemEntradaJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public static RepositorioItemEntradaJDBC getCurrentInstance(Connection conn){
        if(myself == null)
            myself = new RepositorioItemEntradaJDBC(conn);
        
        return myself;
    }	

	public void insert(ItemEntrada itemEntrada) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_item_entrada(quantidade, tb_produto_codigo,tb_lote_entrada_codigo) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, itemEntrada.getQuantidade());
			st.setInt(2, itemEntrada.getProduto().getCodigo());
			st.setInt(3, itemEntrada.getLoteEntrada().getCodigo());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					itemEntrada.setCodigo(id);
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
	
	public void update(ItemEntrada itemEntrada) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_item_entrada SET quantidade = ?, tb_produto_codigo = ?, tb_lote_entrada_codigo = ? WHERE codigo = ?");
			
			st.setInt(1, itemEntrada.getQuantidade());
			st.setInt(2, itemEntrada.getProduto().getCodigo());
			st.setInt(3, itemEntrada.getLoteEntrada().getCodigo());
			st.setInt(4, itemEntrada.getCodigo());
			
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
			st = conn.prepareStatement("DELETE from tb_item_entrada WHERE codigo = ?");
			
			st.setInt(1, codigo);
			
			st.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}
	}

	
	public ItemEntrada findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT tb_item_entrada.codigo, tb_item_entrada.quantidade, "
							+ "tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao"
							+ "tb_lote_entrada.*"
							+ "FROM tb_item_entrada "
							+ "INNER JOIN tb_produto ON tb_produto_codigo = tb_produto.codigo"
							+ "INNER JOIN tb_lote_entrada ON tb_lote_entrada_codigo = tb_lote_entrada.codigo"
							+ "Where codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Produto produto = instantiateProduto(rs);
				LoteEntrada loteEntrada = instantiateLoteEntrada(rs);
				ItemEntrada itemEntrada = instantiateItemEntrada(rs, produto, loteEntrada);
				
				return itemEntrada;
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
	
	public  List<ItemEntrada> findItensDoLote(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT 	tb_item_entrada.*,\r\n" + 
					"		tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao, \r\n" + 
					"        tb_lote_entrada.dataEntrada \r\n" + 
					"from tb_item_entrada\r\n" + 
					"INNER JOIN tb_produto ON tb_item_entrada.tb_produto_codigo = tb_produto.codigo\r\n" + 
					"INNER JOIN tb_lote_entrada ON tb_item_entrada.tb_lote_entrada_codigo = tb_lote_entrada.codigo\r\n" + 
					"where tb_item_entrada.tb_lote_entrada_codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			Map<Integer, Produto> mapProduto = new HashMap<>();
			Map<Integer, LoteEntrada> mapLoteEntrada = new HashMap<>();
			List<ItemEntrada> list = new ArrayList<>();
			
			while(rs.next()) {
				Produto prod = mapProduto.get(rs.getInt("tb_produto_codigo"));
				LoteEntrada loteEntrada = mapLoteEntrada.get(rs.getInt("tb_lote_entrada_codigo"));
				
				
				if(prod == null) {
					prod = instantiateProduto(rs);
					mapProduto.put(rs.getInt("tb_produto_codigo"), prod);
				}
				
				if(loteEntrada == null) {
					loteEntrada = instantiateLoteEntrada(rs);
					mapLoteEntrada.put(rs.getInt("tb_lote_entrada_codigo"), loteEntrada);
				}
				
				ItemEntrada itemEntrada = instantiateItemEntrada(rs, prod, loteEntrada);
				list.add(itemEntrada);			
			} 
			
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<ItemEntrada> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT 	tb_item_entrada.*, " + 
					"tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao, " + 
					"tb_lote_entrada.dataEntrada " + 
					"from tb_item_entrada " + 
					"INNER JOIN tb_produto ON tb_item_entrada.tb_produto_codigo = tb_produto.codigo " + 
					"INNER JOIN tb_lote_entrada ON tb_item_entrada.tb_lote_entrada_codigo = tb_lote_entrada.codigo");
			rs = st.executeQuery();
			
			Map<Integer, Produto> mapProduto = new HashMap<>();
			Map<Integer, LoteEntrada> mapLoteEntrada = new HashMap<>();
			List<ItemEntrada> list = new ArrayList<>();
			
			while(rs.next()) {
				Produto prod = mapProduto.get(rs.getInt("tb_produto_codigo"));
				LoteEntrada loteEntrada = mapLoteEntrada.get(rs.getInt("tb_lote_entrada_codigo"));
				
				
				if(prod == null) {
					prod = instantiateProduto(rs);
					mapProduto.put(rs.getInt("tb_produto_codigo"), prod);
				}
				
				if(loteEntrada == null) {
					loteEntrada = instantiateLoteEntrada(rs);
					mapLoteEntrada.put(rs.getInt("tb_lote_entrada_codigo"), loteEntrada);
				}
				
				ItemEntrada itemEntrada = instantiateItemEntrada(rs, prod, loteEntrada);
				list.add(itemEntrada);	
			}			
			return list;
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private ItemEntrada instantiateItemEntrada(ResultSet rs, Produto produto, LoteEntrada loteEntrada) throws SQLException{
		ItemEntrada itemEntrada = new ItemEntrada();
		itemEntrada.setCodigo(rs.getInt("codigo"));
		itemEntrada.setProduto(produto);
		itemEntrada.setQuantidade(rs.getInt("quantidade"));
		itemEntrada.setLoteEntrada(loteEntrada);
		return itemEntrada;
	}
	
	private Produto instantiateProduto(ResultSet rs) throws SQLException{
		Produto p = new Produto();		
		p.setCodigo(rs.getInt("tb_produto_codigo"));
		p.setNome(rs.getString("nome"));
		p.setMarca(rs.getString("marca"));
		p.setCategoria(rs.getString("categoria"));
		p.setDescricao(rs.getString("descricao"));
		return p;
	}
	
	private LoteEntrada instantiateLoteEntrada(ResultSet rs) throws SQLException{
		LoteEntrada loteEntrada = new LoteEntrada();
		loteEntrada.setCodigo(rs.getInt("tb_lote_entrada_codigo"));
		loteEntrada.setData(new java.util.Date(rs.getTimestamp("dataEntrada").getTime()));
		return loteEntrada;
	}
}
