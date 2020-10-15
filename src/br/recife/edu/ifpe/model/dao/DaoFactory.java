package br.recife.edu.ifpe.model.dao;

import br.recife.edu.ifpe.controller.db.DB;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoqueJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioFuncionariosJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioItemEntradaJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteEntradaJDBC;
import br.recife.edu.ifpe.model.repositorios.RepositorioProdutosJDBC;

public class DaoFactory {
	
	public static RepositorioProdutosJDBC createProdutosJDBC() {
		return new RepositorioProdutosJDBC(DB.getConnection());
	}
	
	public static RepositorioFuncionariosJDBC createFuncionariosJDBC() {
		return new RepositorioFuncionariosJDBC(DB.getConnection());
	}
	
	public static RepositorioEstoqueJDBC createEstoqueJDBC() {
		return new RepositorioEstoqueJDBC(DB.getConnection());
	}
	
	public static RepositorioItemEntradaJDBC createItemEntradaJDBC() {
		return new RepositorioItemEntradaJDBC(DB.getConnection());
	}
	
	public static RepositorioLoteEntradaJDBC createLoteEntradaJDBC() {
		return new RepositorioLoteEntradaJDBC(DB.getConnection());
	}
}
