package br.recife.edu.ifpe.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.recife.edu.ifpe.model.classes.ItemEntrada;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.repositorios.RepositorioProdutos;

/**
 * Servlet implementation class LoteEntradaServlet
 */
@WebServlet("/LoteEntradaServlet")
public class LoteEntradaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoteEntradaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
		
		int codigo = Integer.parseInt(req.getParameter("codigo"));
		
		HttpSession session = req.getSession();
		
		LoteEntrada loteEntrada = (LoteEntrada) session.getAttribute("loteEntrada");
		
		if(loteEntrada == null) {
			loteEntrada = new LoteEntrada();
			session.setAttribute("loteEntrada", loteEntrada);
		}
		
		boolean controle = false;
		for (ItemEntrada ie: loteEntrada.getItens()) {
			if(ie.getProduto().getCodigo() == codigo) {
				ie.setQuantidade(ie.getQuantidade()+1);
				controle = true;
			}			
		}
		
		if(!controle) {
			ItemEntrada item = new ItemEntrada();
			
			Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
			
			item.setProduto(p);
			item.setCodigo((int) Math.random()*10000);
			item.setQuantidade(1);
			
			loteEntrada.addItem(item);
			
			session.setAttribute("msg", "O produto " + p.getNome() + " foi inserido no lote");
		} else {
			session.setAttribute("msg", "O produto foi incrementado no lote");
		}
	}

}
