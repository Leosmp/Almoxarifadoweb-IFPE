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

	private Connection conn;
	
	public RepositorioEstoqueJDBC(Connection conn) {
		this.conn = conn;
	}

	public void insert(ItemEstoque itemEstoque) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tb_item_estoque(fk_produto, quantidade) VALUES (?, ?)",
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
	
	public void update(Produto p) {

		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tb_item_estoque SET nome = ?, marca = ?, categoria = ?, descricao = ? WHERE codigo = ?");
			
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

	
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement(
					"SELECT * FROM tb_item_estoque WHERE codigo = ?");
			
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
	
	public List<ItemEstoque> findAll(){
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("SELECT tb_item_estoque.*, tb_produto.nome, tb_produto.marca, tb_produto.categoria, tb_produto.descricao "
										+ "FROM tb_item_estoque "
										+ "INNER JOIN tb_produto "
										+ "ON tb_item_estoque.fk_produto = tb_produto.codigo");
			rs = st.executeQuery();
			
			Map<Integer, Produto> map = new HashMap<>();
			List<ItemEstoque> list = new ArrayList<>();
			
			while(rs.next()) {
				Produto prod = map.get(rs.getInt("fk_produto"));
				
				if(prod == null) {
					prod = instantiateProduto(rs);
					map.put(rs.getInt("fk_produto"), prod);
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
		
		p.setCodigo(rs.getInt("fk_produto"));
		p.setNome(rs.getString("nome"));
		p.setMarca(rs.getString("marca"));
		p.setCategoria(rs.getString("categoria"));
		p.setDescricao(rs.getString("descricao"));
		return p;
	}
}
