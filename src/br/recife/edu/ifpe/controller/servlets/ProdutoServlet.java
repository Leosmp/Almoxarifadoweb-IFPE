package br.recife.edu.ifpe.controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.recife.edu.ifpe.model.classes.ItemEstoque;
import br.recife.edu.ifpe.model.classes.Produto;
import br.recife.edu.ifpe.model.repositorios.RepositorioEstoque;
import br.recife.edu.ifpe.model.repositorios.RepositorioProdutos;

/**
 * Servlet implementation class ProdutoServlet
 */
@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public ProdutoServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String codAux = request.getParameter("codigo");

		if (codAux == null) {
			List<Produto> produtos = RepositorioProdutos.getCurrentInstance().readAll();
			
			response.setContentType("text/html;charset=UTF-8");
			try (PrintWriter out = response.getWriter()) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");				
				out.println("<title>Servlet ProdutoServlet</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Produtos Cadastrados</h1>");
				out.println("<table border=\"1\" style=\"margin-bottom: 10px\">");
				out.println("<tr>" + "<th>C�digo</th>"
								   + "<th>Nome</th>"
								   + "<th>Marca</th>"
								   + "<th>Categoria</th>"
								   + "</tr>");
				for (Produto p : produtos) {					
					out.println("<tr>");
					out.println("<td>" + p.getCodigo() + "</td>");
					out.println("<td>" + p.getNome() + "</td>");
					out.println("<td>" + p.getMarca() + "</td>");
					out.println("<td>" + p.getCategoria() + "</td>");
					out.println("</tr>");
				}
				out.println("</table>");
				out.println("<a href = \"index.html\">home</a>");
				out.println("</body>");
				out.println("</html>");
			}
		} else {
			int codigo = Integer.parseInt(codAux);

			Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);

			try (PrintWriter out = response.getWriter()) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<meta charset=\"UTF-8\">");
				out.println("<title>Servlet ProdutoServlet</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Produtos Recuperados</h1>");
				out.println("<div><p>C�digo: " + p.getCodigo() + "</p></div>");
				out.println("<div><p>Nome: " + p.getNome() + "</p></div>");
				out.println("<div><p>Marca: " + p.getMarca() + "</p></div>");
				out.println("<div><p>Categoria: " + p.getCategoria() + "</p></div>");
				out.println("<div><p>Descri��o: " + p.getDescricao() + "</p></div>");
				out.println("<a href = \"index.html\">home</a>");
				out.println("</body>");
				out.println("</html>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		try (PrintWriter out = response.getWriter()) {

			int codigo = Integer.parseInt(request.getParameter("codigo"));
			String nome = request.getParameter("nome");
			String marca = request.getParameter("marca");
			String categoria = request.getParameter("categoria");
			String descricao = request.getParameter("descricao");

			Produto produto = new Produto(codigo, nome, marca, categoria, descricao);

			RepositorioProdutos.getCurrentInstance().create(produto);

			ItemEstoque itemEstoque = new ItemEstoque();
			itemEstoque.setProduto(produto);
			itemEstoque.setQuantidade(0);
			itemEstoque.setCodigo(produto.getCodigo());

			RepositorioEstoque.getCurrentInstance().read().addItem(itemEstoque);

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Servlet ProdutoServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>O Produto " + produto.getNome() + " foi cadastrado com sucesso!</h1>");
			out.println("<a href = \"index.html\">home</a>");
			out.println("</body>");
			out.println("</html>");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

}
