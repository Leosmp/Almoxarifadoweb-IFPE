<%@page import="br.recife.edu.ifpe.model.dao.DaoFactory"%>
<%@page import="br.recife.edu.ifpe.model.classes.Produto"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Almoxarifado Web</title>
</head>
<body>

	<jsp:include page="index.html"></jsp:include>

	<div class="container-fluid">
		<h3 class="text-primary col-md-6 mt-5">Produtos Cadastrados</h3>
		
		<%
			String msg = (String) session.getAttribute("msg");
			if (msg != null) {
			out.println("<h2>" + msg + "</h2>");
			session.removeAttribute("msg");
		}
		%>

		<div id="modal"
			style="position: absolute; top: 200px; left: 200px; border: 1px solid black; background: white;">
			<jsp:include page="CadastroProduto.jsp${'?codigo=1'}" />
			<br>
			<button onclick="modalClose()">Close</button>
		</div>

		<div id="modalRedirect"
			style="position: absolute; top: 200px; left: 200px; border: 1px solid black; background: white;">
			<%@include file="VisualizarProduto.jsp"%>
			<br>
			<button onclick="modalRedirectClose()">Close</button>
		</div>

		<%
			List<Produto> produtos = DaoFactory.createProdutosJDBC().findAll();
		%>

		<table class="table table-hover">
			<thead class="table-primary">
				<tr class="table-active">
					<th>Código</th>
					<th>Nome</th>
					<th>Marca</th>
					<th>Categoria</th>
					<th>Operações</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Produto p : produtos) {
				%>
				<tr>
					<td><%=p.getCodigo()%></td>
					<td><%=p.getNome()%></td>
					<td><%=p.getMarca()%></td>
					<td><%=p.getCategoria()%></td>
					<td><a
						href="ProdutoServlet?codigo=<%=p.getCodigo()%>&redirect=visualiza">Visualizar
					</a> <a
						href="ProdutoServlet?codigo=<%=p.getCodigo()%>&redirect=atualiza">Atualizar
					</a> <a href="#" onclick="deletar(<%=p.getCodigo()%>)">Deletar </a></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>		
		
		<button class="btn btn-primary mb-4"
			onclick="modalOpen()">Novo Produto</button>
		
	</div>
	<script>
		var modal = document.getElementById("modal");		
		
		var modalRedirect = document.getElementById("modalRedirect");
		
		<%String redirect = request.getParameter("redirect");
			if (redirect == null) {%>
				modal.remove();
				modalRedirect.remove();				
		<%} else if (redirect.equals("visualiza")) {%>		
				modal.remove();				
		<%} else {%>
				modalRedirect.remove();	
		<%}%>
		
		function modalOpen(){
			document.body.appendChild(modal);
		}
		
		function modalClose() {
			modal.remove();
		}
		
		function modalRedirectClose() {
			modalRedirect.remove();
		}
		
		function deletar(codigo){
			fetch("ProdutoServlet?codigo="+codigo,{method:'delete'})
				.then(function(response){
				location.reload();
			});
		};
	</script>
	
	<script 
		src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>

</body>
</html>