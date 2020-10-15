package br.recife.edu.ifpe.controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.recife.edu.ifpe.model.classes.Estoque;
import br.recife.edu.ifpe.model.classes.ItemEntrada;
import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.dao.DaoFactory;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoque;
import br.recife.edu.ifpe.model.repositorios.RepositorioLoteEntrada;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int codigo = Integer.parseInt(request.getParameter("codigo"));

		LoteEntrada loteEntrada = RepositorioLoteEntrada.getCurrentInstance().read(codigo);

		String responseJSON = "{\"codigo\":" + loteEntrada.getCodigo() + "," + "\"descricao\":\""
								+ loteEntrada.getDescricao() + "\",\"itens\":[";
		for (ItemEntrada item : loteEntrada.getItens()) {
			responseJSON 	+= "{\"codigo\":" + item.getCodigo() 
							+ ",\"nomeProduto\":\"" + item.getProduto().getNome()
							+ "\"" + ",\"quantidade\":" + item.getQuantidade() + "}";
			if (loteEntrada.getItens().indexOf(item) != loteEntrada.getItens().size() - 1) {
				responseJSON += ",";
			}
		}
		responseJSON += "]}";

		response.setContentType("text/plain");

		try (PrintWriter out = response.getWriter()) {
			out.println(responseJSON);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(); //informação vinda do put
		LoteEntrada loteEntrada = (LoteEntrada) session.getAttribute("loteEntrada");

		for (ItemEntrada x : loteEntrada.getItens()) {
			if (x.getQuantidade() > 10) {
				session.setAttribute("msg", "Você está tentando inserir mais de 10 itens do produto "
						+ x.getProduto().getNome() + " no seu lote!");
				response.sendError(500);
				return;
			}
		}
		
		List<ItemEstoque> listaEstoque = DaoFactory.createEstoqueJDBC().findAll();

		for (ItemEntrada x : loteEntrada.getItens()) {
			for (ItemEstoque y : listaEstoque) {
				if (x.getProduto().getCodigo() == y.getProduto().getCodigo()) {
					DaoFactory.createEstoqueJDBC().findById(y.getCodigo());
					y.adicionar(x.getQuantidade());
					DaoFactory.createEstoqueJDBC().update(y);
					break;
				}
			}
		}
		
		DaoFactory.createLoteEntradaJDBC().insert(loteEntrada);
		
		for (ItemEntrada x : loteEntrada.getItens()) {
			x.setLoteEntrada(loteEntrada);
			DaoFactory.createItemEntradaJDBC().insert(x);
		}

		session.removeAttribute("loteEntrada");

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);

		String operacao = req.getParameter("operacao");

		int codigo = Integer.parseInt(req.getParameter("codigo"));

		HttpSession session = req.getSession();

		LoteEntrada loteEntrada = (LoteEntrada) session.getAttribute("loteEntrada");

		if (loteEntrada == null) {
			loteEntrada = new LoteEntrada();
			session.setAttribute("loteEntrada", loteEntrada);
		}

		boolean controle = false;
		for (ItemEntrada ie : loteEntrada.getItens()) {
			if (ie.getProduto().getCodigo() == codigo) {
				if (operacao.equals("mais")) {
					ie.setQuantidade(ie.getQuantidade() + 1);
					controle = true;
					session.setAttribute("msg", "O produto foi incrementado no lote");
					break;
				} else if (operacao.equals("menos")) {
					if (ie.getQuantidade() == 1) {
						loteEntrada.getItens().remove(ie);
						controle = true;
						if (loteEntrada.getItens().size() == 0) {
							session.removeAttribute("loteEntrada");
						}
						break;
					}
					ie.setQuantidade(ie.getQuantidade() - 1);
					controle = true;
					break;
				}
			}
		}

		if (!controle) {
			ItemEntrada item = new ItemEntrada();

			Produto p = DaoFactory.createProdutosJDBC().findById(codigo);

			item.setProduto(p);
			item.setCodigo((int) Math.random() * 10000);
			item.setQuantidade(1);

			loteEntrada.addItem(item);

			session.setAttribute("msg", "O produto " + p.getNome() + " foi inserido no lote");
		} else {

		}
	}

}
