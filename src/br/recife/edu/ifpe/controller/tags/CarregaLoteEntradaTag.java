package br.recife.edu.ifpe.controller.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteEntrada;

public class CarregaLoteEntradaTag extends SimpleTagSupport{
	
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		super.doTag();
		
		List<LoteEntrada> listLote = RepositorioLoteEntrada.getCurrentInstance().readAll();
		getJspContext().setAttribute("listaLoteEntrada", listLote,PageContext.PAGE_SCOPE);
	}

}
