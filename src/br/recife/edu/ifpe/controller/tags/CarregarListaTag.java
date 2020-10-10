package br.recife.edu.ifpe.controller.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import br.recife.edu.ifpe.model.classes.Estoque;
import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoque;
import br.recife.edu.ifpe.model.repositorios.RepositorioFuncionario;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteEntrada;
import br.recife.edu.ifpe.model.repositorios.RepositorioProdutos;

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
			List<Produto> produtos = RepositorioProdutos.getCurrentInstance().readAll();
			getJspContext().setAttribute("produtos", produtos, PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("loteentrada")) {
			List<LoteEntrada> listLote = RepositorioLoteEntrada.getCurrentInstance().readAll();
			getJspContext().setAttribute("listaLoteEntrada", listLote,PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("funcionario")) {
			List<Funcionario> listFuncionario = RepositorioFuncionario.getCurrentInstance().readAll();
			getJspContext().setAttribute("listFuncionario", listFuncionario, PageContext.PAGE_SCOPE);
		}
		
		if(carregar.equals("estoque")) {
			Estoque estoque = RepositorioEstoque.getCurrentInstance().read();
			getJspContext().setAttribute("estoque", estoque, PageContext.PAGE_SCOPE);
		}
	}

}
