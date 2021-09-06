package br.recife.edu.ifpe.controller.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.recife.edu.ifpe.model.classes.LoteEntrada;
import br.recife.edu.ifpe.model.classes.LoteSaida;
import br.recife.edu.ifpe.model.dao.DaoFactory;

/**
 * Servlet implementation class RelatorioServlet
 */
@WebServlet("/RelatorioServlet")
public class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatorioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String stringDataInicio = request.getParameter("dataInicio");
		String stringDataFinal = request.getParameter("dataFim");
		
		String lote = request.getParameter("lote");
		request.setAttribute("lote", lote);
		request.setAttribute("exibir", true);

		try {
			Date dataInicio = sdf.parse(stringDataInicio);
			Date dataFim = sdf.parse(stringDataFinal);
			
			if(lote.equals("loteEntrada")) {
				List<LoteEntrada> listaLoteEntrada = DaoFactory.createRelatorioJDBC().findLoteEntradaByDate(dataInicio, dataFim);
				request.setAttribute("listaLoteEntrada", listaLoteEntrada);
				request.getRequestDispatcher("/relatorio.jsp").forward(request, response);
			} 
			
			if(lote.equals("loteSaida")) {
				List<LoteSaida> listaLoteSaida = DaoFactory.createRelatorioJDBC().findLoteSaidaByDate(dataInicio, dataFim);
				request.setAttribute("listaLoteSaida", listaLoteSaida);
				request.getRequestDispatcher("/relatorio.jsp").forward(request, response);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
