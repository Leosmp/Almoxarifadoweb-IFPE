package br.recife.edu.ifpe.controller.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.dao.DaoFactory;

public class CarregarListaTag extends SimpleTagSupport{
	
	private String carregar;

	public void setCarregar(String carregar) {
		this.carregar = carregar;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		super.doTag();
		
		if(carregar.equals("produtos")) {
			List<Produto> produtos = DaoFactory.createProdutosJDBC().findAll();
			getJspContext().setAttribute("produtos", produtos, PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("loteentrada")) {
			List<LoteEntrada> listLote = DaoFactory.createLoteEntradaJDBC().findAll();
			getJspContext().setAttribute("listaLoteEntrada", listLote,PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("funcionario")) {
			List<Funcionario> listFuncionario = DaoFactory.createFuncionariosJDBC().findAll();
			getJspContext().setAttribute("listFuncionario", listFuncionario, PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("estoque")) {
			List<ItemEstoque> listaEstoque = DaoFactory.createEstoqueJDBC().findAll();
			getJspContext().setAttribute("listaEstoque", listaEstoque, PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("lotesaida")) {
			List<LoteSaida> listLoteSaida = DaoFactory.createLoteSaidaJDBC().findAll();
			getJspContext().setAttribute("listLoteSaida", listLoteSaida,PageContext.PAGE_SCOPE);
		}
	}
}
