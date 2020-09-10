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
		String atualizar = request.getParameter("atualizar");
		String deletar = request.getParameter("deletar");
		
		if(deletar != null) {
			int codigo = Integer.parseInt(codAux);
			
			Produto p = RepositorioProdutos.getCurrentInstance().read(codigo);
			RepositorioProdutos.getCurrentInstance().delete(p);
			
			response.setContentType("text/html;charset=UTF-8");
			try (PrintWriter out = response.getWriter()) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet ProdutoServlet</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>O Produto " + p.getNome() + " foi deletado com sucesso!</h1>");
				out.println("<a href = \"index.html\">home</a>");
				out.println("</body>");
				out.println("</html>");
			}
		}
		
		if(atualizar != null) {
			
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
				out.println("<a href = \"ProdutoServlet\">Lista de produtos</a>");
				out.println("<form action=\"ProdutoServlet\" method=\"POST\">\r\n" 
						+ "            <div style=\"margin-left: 10px;\">\r\n" 
						+ "            \r\n" 
						+ "            	<div style=\"padding-bottom: 5px;\">\r\n" 
						+ "                  <label>\r\n" 
						+ "                        Código: <input type=\"hidden\" name=\"codigo\" value=\""+ p.getCodigo()+"\"\r\n"
						+ "                              style=\"width: 100px; margin-left: 16px;\">\r\n" 
						+ "                    </label>\r\n" 
						+ "                </div>\r\n" 
						+ "                <div style=\"padding-bottom: 5px;\">\r\n" 
						+ "                    <label>Nome: \r\n" 
						+ "                        <input type=\"text\" name=\"nome\" placeholder=\"Insira o nome do produto\" value=\""+ p.getNome()+"\"\r\n" 
						+ "                        style=\"width: 250px; margin-left: 24px;\">\r\n" 
						+ "                    </label>\r\n" 
						+ "                </div>\r\n" 
						+ "                <div style=\"padding-bottom: 5px;\">\r\n" 
						+ "                    <label>Marca: \r\n" 
						+ "                        <input type=\"text\" name=\"marca\" placeholder=\"Insira a marca do produto\" value=\""+ p.getMarca()+"\"\r\n" 
						+ "                        style=\"width: 250px; margin-left: 24px;\">\r\n" 
						+ "                    </label>\r\n" 
						+ "                </div>\r\n" 
						+ "    \r\n" 
						+ "                <div style=\"padding-bottom: 5px;\">\r\n" 
						+ "                    <label>Categoria: \r\n" 
						+ "                        <input type=\"text\" name=\"categoria\" placeholder=\"Insira a categoria do produto\" value=\""+ p.getCategoria()+"\"\r\n" 
						+ "                        style=\"width: 250px;\">\r\n" 
						+ "                    </label>\r\n" 
						+ "                </div>\r\n" 
						+ "    \r\n" 
						+ "                <div style=\"margin-bottom: 15px;\">\r\n" 
						+ "                    <label>Descrição: \r\n" 
						+ "                        <textarea name=\"descricao\"\r\n" 
						+ "                        style=\"width: 250px; height: 100px; vertical-align: top\">" + p.getDescricao()+ "</textarea>\r\n" 
						+ "                    </label>\r\n" 
						+ "                </div>\r\n" 
						+ "    \r\n"
						+ "                    <input type=\"hidden\" name=\"atualizar\" value=\"1\">\r\n"
						+ "                    <input type=\"submit\" value=\"Atualizar\" style=\"height: 25px; margin-right: 15px;\">\r\n" 
						+ "                    <input type=\"reset\" value=\"Limpar\" style=\"height: 25px;\">\r\n"
						+ "\r\n" 
						+ "            </div>            \r\n" 
						+ "\r\n" 
						+ "        </form>");
				out.println("</body>");
				out.println("</html>");
			}
		}

		if (codAux == null && atualizar == null && deletar == null) {
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
				out.println("<tr>" + "<th>Código</th>"
								   + "<th>Nome</th>"
								   + "<th>Marca</th>"
								   + "<th>Categoria</th>"
								   + "<th>Operações</th>"
								   + "</tr>");
				for (Produto p : produtos) {					
					out.println("<tr>");
					out.println("<td>" + p.getCodigo() + "</td>");
					out.println("<td>" + p.getNome() + "</td>");
					out.println("<td>" + p.getMarca() + "</td>");
					out.println("<td>" + p.getCategoria() + "</td>");
					out.println("<td>"
									+ "<a href=\"ProdutoServlet?codigo=" + p.getCodigo()+ "\">Visualizar </a>"
									+ "<a href=\"ProdutoServlet?codigo=" + p.getCodigo() + "&atualizar=1\">Atualizar </a>"
									+ "<a href=\"ProdutoServlet?codigo=" + p.getCodigo() + "&deletar=1\">Deletar</a>"
								+ "</td>");
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
			
			response.setContentType("text/html;charset=UTF-8");
			try (PrintWriter out = response.getWriter()) {
				out.println("<!DOCTYPE html>");
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Servlet ProdutoServlet</title>");
				out.println("</head>");
				out.println("<body>");
				out.println("<h1>Produtos Recuperados</h1>");
				out.println("<div><p>Código: " + p.getCodigo() + "</p></div>");
				out.println("<div><p>Nome: " + p.getNome() + "</p></div>");
				out.println("<div><p>Marca: " + p.getMarca() + "</p></div>");
				out.println("<div><p>Categoria: " + p.getCategoria() + "</p></div>");
				out.println("<div><p>Descrição: " + p.getDescricao() + "</p></div>");
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
			
			String atualizar = request.getParameter("atualizar");
			
			Produto produto = new Produto(codigo, nome, marca, categoria, descricao);
			
			if(atualizar != null) {
				RepositorioProdutos.getCurrentInstance().update(produto);
			} else {	
				RepositorioProdutos.getCurrentInstance().create(produto);
	
				ItemEstoque itemEstoque = new ItemEstoque();
				itemEstoque.setProduto(produto);
				itemEstoque.setQuantidade(0);
				itemEstoque.setCodigo(produto.getCodigo());
	
				RepositorioEstoque.getCurrentInstance().read().addItem(itemEstoque);
			}

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Servlet ProdutoServlet</title>");
			out.println("</head>");
			out.println("<body>");
			if(atualizar == null) {
				out.println("<h1>O Produto " + produto.getNome() + " foi cadastrado com sucesso!</h1>");
			}else {
				out.println("<h1>O Produto " + produto.getNome() + " foi atualizado com sucesso!</h1>");
			}
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
