package br.recife.edu.ifpe.model.repositorios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.classes.ItemEntrada;
import br.recife.edu.ifpe.model.classes.ItemSaida;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.dao.DaoFactory;

public class RepositorioRelatorioJDBC {
	
	private static Connection conn;
	private static RepositorioRelatorioJDBC myself = null;
	
	private RepositorioRelatorioJDBC(Connection conn) {
		this.conn = conn;
	}
	
	public static RepositorioRelatorioJDBC getCurrentInstance(Connection conn){
        if(myself == null)
            myself = new RepositorioRelatorioJDBC(conn);
        
        return myself;
    }
	
	public List<LoteEntrada> findLoteEntradaByDate(Date dataInicio, Date dataFim){
		List<LoteEntrada> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		java.sql.Date dataInicioSQL = new java.sql.Date(dataInicio.getTime());
		java.sql.Date dataFimSQL = new java.sql.Date(dataFim.getTime());
		
		try {
			st = conn.prepareStatement(""
					+ "SELECT * "
					+ "FROM tb_lote_entrada "
					+ "WHERE dataEntrada between ? AND ?");
			
			st.setDate(1, dataInicioSQL);
			st.setDate(2, dataFimSQL);			
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				LoteEntrada loteEntrada = instantiateLoteEntrada(rs);
				list.add(loteEntrada);
			}
			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;	
	}	
	
	public List<LoteSaida> findLoteSaidaByDate(Date dataInicio, Date dataFim){
		List<LoteSaida> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;
		
		java.sql.Date dataInicioSQL = new java.sql.Date(dataInicio.getTime());
		java.sql.Date dataFimSQL = new java.sql.Date(dataFim.getTime());
		
		try {
			st = conn.prepareStatement(""
					+ "SELECT tb_lote_saida.*, tb_funcionario.nome, tb_funcionario.departamento "
					+ "FROM tb_lote_saida "
					+ "INNER JOIN tb_funcionario ON tb_funcionario_codigo = tb_funcionario.codigo "
					+ "WHERE dataSaida between ? AND ?");
			
			st.setDate(1, dataInicioSQL);
			st.setDate(2, dataFimSQL);			
			
			rs = st.executeQuery();
			
			while(rs.next()) {
				Funcionario funcionario = instantiateFuncionario(rs);
				LoteSaida loteSaida = instantiateLoteSaida(rs, funcionario);
				list.add(loteSaida);
			}
			
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return list;	
	}
	
	private LoteEntrada instantiateLoteEntrada(ResultSet rs) throws SQLException{
		LoteEntrada loteEntrada = new LoteEntrada();
		List<ItemEntrada> listItemEntrada = DaoFactory.createItemEntradaJDBC().findItensDoLote(rs.getInt("codigo"));
		loteEntrada.setCodigo(rs.getInt("codigo"));
		loteEntrada.setData(new java.util.Date(rs.getTimestamp("dataEntrada").getTime()));
		loteEntrada.setItens(listItemEntrada);
		return loteEntrada;
	}
	

	private LoteSaida instantiateLoteSaida(ResultSet rs, Funcionario funcionario) throws SQLException{
		LoteSaida LoteSaida = new LoteSaida();
		List<ItemSaida> listItemSaida = DaoFactory.createItemSaidaJDBC().findItensDoLote(rs.getInt("codigo"));
		LoteSaida.setCodigo(rs.getInt("codigo"));
		LoteSaida.setResponsavel(funcionario);
		LoteSaida.setData(new java.util.Date(rs.getTimestamp("dataSaida").getTime()));
		LoteSaida.setItens(listItemSaida);
		return LoteSaida;
	}
	
	private Funcionario instantiateFuncionario(ResultSet rs) throws SQLException {
		Funcionario func = new Funcionario();
		func.setCodigo(rs.getInt("tb_funcionario_codigo"));
		func.setNome(rs.getString("nome"));
		func.setDepartamento(rs.getString("departamento"));
		return func;
	}
}
