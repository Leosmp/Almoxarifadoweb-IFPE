package br.recife.edu.ifpe.model.dao;

import br.recife.edu.ifpe.controller.db.DB;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoqueJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioFuncionariosJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioItemEntradaJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioItemSaidaJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteEntradaJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteSaidaJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioProdutosJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioRelatorioJDBC;

public class DaoFactory {
	
	public static RepositorioProdutosJDBC createProdutosJDBC() {
		return RepositorioProdutosJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioFuncionariosJDBC createFuncionariosJDBC() {
		return RepositorioFuncionariosJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioEstoqueJDBC createEstoqueJDBC() {
		return RepositorioEstoqueJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioItemEntradaJDBC createItemEntradaJDBC() {
		return RepositorioItemEntradaJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioLoteEntradaJDBC createLoteEntradaJDBC() {
		return RepositorioLoteEntradaJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioItemSaidaJDBC createItemSaidaJDBC() {
		return RepositorioItemSaidaJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioLoteSaidaJDBC createLoteSaidaJDBC() {
		return RepositorioLoteSaidaJDBC.getCurrentInstance(DB.getConnection());
	}
	
	public static RepositorioRelatorioJDBC createRelatorioJDBC() {
		return RepositorioRelatorioJDBC.getCurrentInstance(DB.getConnection());
	}
}
