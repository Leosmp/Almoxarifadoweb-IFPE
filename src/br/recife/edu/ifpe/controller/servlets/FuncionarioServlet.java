package br.recife.edu.ifpe.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.recife.edu.ifpe.model.classes.Funcionario;
import br.recife.edu.ifpe.model.dao.DaoFactory;

/**
 * Servlet implementation class FuncionarioServlet
 */
@WebServlet("/FuncionarioServlet")
public class FuncionarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FuncionarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String forward = "";
		String action = request.getParameter("action");		
		
		if(action.equals("List_Funcionarios")) {
			forward = "/listFuncionarios.jsp";
		} else if(action.equals("editar")) {
			forward = "/funcionarios.jsp";
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			Funcionario func = DaoFactory.createFuncionariosJDBC().findById(codigo);
			request.setAttribute("func", func);
		} else if(action.equals("cadastro")) {
			forward = "/funcionarios.jsp";
		} 
		
		request.getRequestDispatcher(forward).forward(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		String departamento = request.getParameter("departamento");
		HttpSession session = request.getSession();
		
		Funcionario func = new Funcionario(nome, departamento);
		
		String action = request.getParameter("editar");
		
		if(action == null) {
			DaoFactory.createFuncionariosJDBC().insert(func);
			session.setAttribute("msg", "Funcionário " + func.getNome() + " foi cadastrado!");			
		} else if(action.equals("Editar")) {
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			func.setCodigo(codigo);
			DaoFactory.createFuncionariosJDBC().update(func);			
			session.setAttribute("msg", "Funcionário " + func.getNome() + " foi atualizado!");
		} 		
		response.sendRedirect("listFuncionarios.jsp");
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		
		DaoFactory.createFuncionariosJDBC().delete(codigo);
	}

}
