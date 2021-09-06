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

import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.ItemSaida;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.dao.DaoFactory;

/**
 * Servlet implementation class LoteSaidaServlet
 */
@WebServlet("/LoteSaidaServlet")
public class LoteSaidaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoteSaidaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int codigo = Integer.parseInt(request.getParameter("codigo"));

		LoteSaida loteSaida = DaoFactory.createLoteSaidaJDBC().findById(codigo);
		response.setCharacterEncoding("UTF-8");

		String responseJSON = "{\"codigo\":" + loteSaida.getCodigo() + "," + "\"descricao\":\""
								+ loteSaida.getDescricao() + "\",\"itens\":[";
		for (ItemSaida item : loteSaida.getItens()) {
			responseJSON 	+= "{\"codigo\":" + item.getCodigo() 
							+ ",\"nomeProduto\":\"" + item.getProduto().getNome()
							+ "\"" + ",\"quantidade\":" + item.getQuantidade() + "}";
			if (loteSaida.getItens().indexOf(item) != loteSaida.getItens().size() - 1) {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession(); //informação vinda do put
		LoteSaida loteSaida = (LoteSaida) session.getAttribute("loteSaida");
		ItemEstoque estoque = new ItemEstoque();
		int codigoFuncionario = Integer.parseInt(request.getParameter("id"));
		Funcionario Funcionario = DaoFactory.createFuncionariosJDBC().findById(codigoFuncionario);
		
		for (ItemSaida x : loteSaida.getItens()) {
			estoque = DaoFactory.createEstoqueJDBC().findById(x.getProduto().getCodigo());
			if (x.getQuantidade() > estoque.getQuantidade()) {				
				session.setAttribute("msg", "Não tem quantidade suficiente do produto "
						+ x.getProduto().getNome() + " no seu estoque!");
				response.sendError(500);
				return;
			}
		}
		
		List<ItemEstoque> listaEstoque = DaoFactory.createEstoqueJDBC().findAll();

		for (ItemSaida x : loteSaida.getItens()) {
			for (ItemEstoque y : listaEstoque) {
				if (x.getProduto().getCodigo() == y.getProduto().getCodigo()) {
					DaoFactory.createEstoqueJDBC().findById(y.getCodigo());
					y.subtrair(x.getQuantidade());
					DaoFactory.createEstoqueJDBC().update(y);
					break;
				}
			}
		}
		
		loteSaida.setResponsavel(Funcionario);		
		DaoFactory.createLoteSaidaJDBC().insert(loteSaida);
		
		for (ItemSaida x : loteSaida.getItens()) {
			x.setLoteSaida(loteSaida);
			DaoFactory.createItemSaidaJDBC().insert(x);
		}

		session.removeAttribute("loteSaida");
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);

		String operacao = req.getParameter("operacao");

		int codigo = Integer.parseInt(req.getParameter("codigo"));

		HttpSession session = req.getSession();

		LoteSaida loteSaida = (LoteSaida) session.getAttribute("loteSaida");

		if (loteSaida == null) {
			loteSaida = new LoteSaida();
			session.setAttribute("loteSaida", loteSaida);
		}

		boolean controle = false;
		for (ItemSaida item : loteSaida.getItens()) {
			if (item.getProduto().getCodigo() == codigo) {
				if (operacao.equals("mais")) {
					item.setQuantidade(item.getQuantidade() + 1);
					controle = true;
					session.setAttribute("msg", "O produto foi incrementado no lote");
					break;
				} else if (operacao.equals("menos")) {
					if (item.getQuantidade() == 1) {
						loteSaida.getItens().remove(item);
						controle = true;
						if (loteSaida.getItens().size() == 0) {
							session.removeAttribute("loteSaida");
						}
						break;
					}
					item.setQuantidade(item.getQuantidade() - 1);
					controle = true;
					break;
				}
			}
		}

		if (!controle) {
			ItemSaida item = new ItemSaida();

			Produto p = DaoFactory.createProdutosJDBC().findById(codigo);

			item.setProduto(p);
			item.setCodigo((int) Math.random() * 10000);
			item.setQuantidade(1);

			loteSaida.addItem(item);

			session.setAttribute("msg", "O produto " + p.getNome() + " foi inserido no lote");
		} else {

		}
	}

}
