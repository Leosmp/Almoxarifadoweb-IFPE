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
import br.recife.edu.ifpe.model.classes.ItemSaida;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.classes.Produto;

public class RepositorioItemSaidaJDBC {

	private static Connection conn;
	private static RepositorioItemSaidaJDBC myself = null;
	
	private RepositorioItemSaidaJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public static RepositorioItemSaidaJDBC getCurrentInstance(Connection conn){
        if(myself == null)
            myself = new RepositorioItemSaidaJDBC(conn);
        
        return myself;
    }

	public void insert(ItemSaida itemSaida) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_item_saida(quantidade, tb_produto_codigo,tb_lote_saida_codigo) VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, itemSaida.getQuantidade());
			st.setInt(2, itemSaida.getProduto().getCodigo());
			st.setInt(3, itemSaida.getLoteSaida().getCodigo());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					itemSaida.setCodigo(id);
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
	
	public void update(ItemSaida itemSaida) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_item_saida SET quantidade = ?, tb_produto_codigo = ?, tb_lote_saida_codigo = ? WHERE codigo = ?");
			
			st.setInt(1, itemSaida.getQuantidade());
			st.setInt(2, itemSaida.getProduto().getCodigo());
			st.setInt(3, itemSaida.getLoteSaida().getCodigo());
			st.setInt(4, itemSaida.getCodigo());
			
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
			st = conn.prepareStatement("DELETE from tb_item_saida WHERE codigo = ?");
			
			st.setInt(1, codigo);
			
			st.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}
	}

	
	public ItemSaida findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT tb_item_saida.codigo, tb_item_saida.quantidade, "
							+ "tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao"
							+ "tb_lote_saida.*"
							+ "FROM tb_item_saida "
							+ "INNER JOIN tb_produto ON tb_produto_codigo = tb_produto.codigo"
							+ "INNER JOIN tb_lote_saida ON tb_lote_saida_codigo = tb_lote_saida.codigo"
							+ "Where codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Produto produto = instantiateProduto(rs);
				LoteSaida loteSaida = instantiateLoteSaida(rs);
				ItemSaida itemSaida = instantiateItemSaida(rs, produto, loteSaida);
				
				return itemSaida;
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
	
	public  List<ItemSaida> findItensDoLote(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT 	tb_item_saida.*,\r\n" + 
					"		tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao, \r\n" + 
					"        tb_lote_saida.dataSaida \r\n" + 
					"from tb_item_saida\r\n" + 
					"INNER JOIN tb_produto ON tb_item_saida.tb_produto_codigo = tb_produto.codigo\r\n" + 
					"INNER JOIN tb_lote_saida ON tb_item_saida.tb_lote_saida_codigo = tb_lote_saida.codigo\r\n" + 
					"where tb_item_saida.tb_lote_saida_codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			Map<Integer, Produto> mapProduto = new HashMap<>();
			Map<Integer, LoteSaida> mapLoteSaida = new HashMap<>();
			List<ItemSaida> list = new ArrayList<>();
			
			while(rs.next()) {
				Produto prod = mapProduto.get(rs.getInt("tb_produto_codigo"));
				LoteSaida loteSaida = mapLoteSaida.get(rs.getInt("tb_lote_saida_codigo"));				
				
				if(prod == null) {
					prod = instantiateProduto(rs);
					mapProduto.put(rs.getInt("tb_produto_codigo"), prod);
				}
				
				if(loteSaida == null) {
					loteSaida = instantiateLoteSaida(rs);
					mapLoteSaida.put(rs.getInt("tb_lote_saida_codigo"), loteSaida);
				}
				
				ItemSaida itemSaida = instantiateItemSaida(rs, prod, loteSaida);
				list.add(itemSaida);			
			} 
			
			return list;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	public List<ItemSaida> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT 	tb_item_saida.*, " + 
					"tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao, " + 
					"tb_lote_saida.dataSaida " + 
					"FROM tb_item_saida " + 
					"INNER JOIN tb_produto ON tb_item_saida.tb_produto_codigo = tb_produto.codigo " + 
					"INNER JOIN tb_lote_saida ON tb_item_saida.tb_lote_saida_codigo = tb_lote_saida.codigo");
			rs = st.executeQuery();
			
			Map<Integer, Produto> mapProduto = new HashMap<>();
			Map<Integer, LoteSaida> mapLoteSaida = new HashMap<>();
			List<ItemSaida> list = new ArrayList<>();
			
			while(rs.next()) {
				Produto prod = mapProduto.get(rs.getInt("tb_produto_codigo"));
				LoteSaida loteSaida = mapLoteSaida.get(rs.getInt("tb_lote_saida_codigo"));
				
				
				if(prod == null) {
					prod = instantiateProduto(rs);
					mapProduto.put(rs.getInt("tb_produto_codigo"), prod);
				}
				
				if(loteSaida == null) {
					loteSaida = instantiateLoteSaida(rs);
					mapLoteSaida.put(rs.getInt("tb_lote_saida_codigo"), loteSaida);
				}
				
				ItemSaida itemSaida = instantiateItemSaida(rs, prod, loteSaida);
				list.add(itemSaida);	
			}			
			return list;
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private ItemSaida instantiateItemSaida(ResultSet rs, Produto produto, LoteSaida loteSaida) throws SQLException{
		ItemSaida itemSaida = new ItemSaida();
		itemSaida.setCodigo(rs.getInt("codigo"));
		itemSaida.setProduto(produto);
		itemSaida.setQuantidade(rs.getInt("quantidade"));
		itemSaida.setLoteSaida(loteSaida);
		return itemSaida;
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
	
	private LoteSaida instantiateLoteSaida(ResultSet rs) throws SQLException{
		LoteSaida loteSaida = new LoteSaida();
		loteSaida.setCodigo(rs.getInt("tb_lote_saida_codigo"));
		loteSaida.setData(new java.util.Date(rs.getTimestamp("dataSaida").getTime()));
		return loteSaida;
	}
}
