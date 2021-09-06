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
import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.Produto;

public class RepositorioEstoqueJDBC {

	private static Connection conn;
	private static RepositorioEstoqueJDBC myself = null;
	
	private RepositorioEstoqueJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public static RepositorioEstoqueJDBC getCurrentInstance(Connection conn){
        if(myself == null)
            myself = new RepositorioEstoqueJDBC(conn);
        
        return myself;
    }

	public void insert(ItemEstoque itemEstoque) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_item_estoque(tb_produto_codigo, quantidade) VALUES (?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, itemEstoque.getProduto().getCodigo());
			st.setInt(2, itemEstoque.getQuantidade());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					itemEstoque.setCodigo(id);
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
	
	public void update(ItemEstoque itemEstoque) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_item_estoque SET tb_produto_codigo = ?, quantidade = ? WHERE codigo = ?");
			
			st.setInt(1, itemEstoque.getProduto().getCodigo());
			st.setInt(2, itemEstoque.getQuantidade());
			st.setInt(3, itemEstoque.getCodigo());
			
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
			st = conn.prepareStatement("DELETE from tb_item_estoque WHERE codigo = ?");
			
			st.setInt(1, codigo);
			
			st.executeQuery();
		} catch (SQLException e) {
			throw new DbException("Unexpected error! No rows affected.");
		} finally {
			DB.closeStatement(st);
		}
	}

	
	public ItemEstoque findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT tb_item_estoque.*, tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao "
					+ "FROM tb_item_estoque "
					+ "INNER JOIN tb_produto "
					+ "ON tb_item_estoque.tb_produto_codigo = tb_produto.codigo "
					+ "WHERE tb_item_estoque.codigo = ?");
			
			st.setInt(1, id);
			
			rs = st.executeQuery();
			
			if(rs.next()) {
				Produto prod = instantiateProduto(rs);
				ItemEstoque itemEstoque = instantiateItemEstoque(rs, prod);
				
				return itemEstoque;
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
	
	public List<ItemEstoque> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT tb_item_estoque.*, tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao "
										+ "FROM tb_item_estoque "
										+ "INNER JOIN tb_produto "
										+ "ON tb_item_estoque.tb_produto_codigo = tb_produto.codigo");
			rs = st.executeQuery();
			
			Map<Integer, Produto> map = new HashMap<>();
			List<ItemEstoque> list = new ArrayList<>();
			
			while(rs.next()) {
				Produto prod = map.get(rs.getInt("tb_produto_codigo"));
				
				if(prod == null) {
					prod = instantiateProduto(rs);
					map.put(rs.getInt("tb_produto_codigo"), prod);
				}
				
				ItemEstoque itemEstoque = instantiateItemEstoque(rs, prod);
				list.add(itemEstoque);	
			}			
			return list;
			
		} catch(SQLException e){
			throw new DbException(e.getMessage());
			
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private ItemEstoque instantiateItemEstoque(ResultSet rs, Produto produto) throws SQLException{
		ItemEstoque itemEstoque = new ItemEstoque();
		itemEstoque.setCodigo(rs.getInt("codigo"));
		itemEstoque.setProduto(produto);
		itemEstoque.setQuantidade(rs.getInt("quantidade"));
		return itemEstoque;
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
}
