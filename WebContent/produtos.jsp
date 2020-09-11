<%@page import="br.recife.edu.ifpe.model.classes.Produto"%>
<%@page import="br.recife.edu.ifpe.model.repositorios.RepositorioProdutos"%>
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
	<h1>Produtos Cadastrados</h1>
	
	<%
		String msg = (String) session.getAttribute("msg");
		if(msg != null){
			out.println("<h2>"+msg+"</h2>");
			session.removeAttribute("msg");
		}
	%>
	
	<button style="margin-bottom: 15px; display: block;" onclick="modalOpen()">Novo Produto</button>
	
	<div id="modal" style="position:absolute; top: 200px; left: 200px; border: 1px solid black; background: white;">
		<%@include file="CadastroProduto.jsp" %>
		<br>
		<button onclick="modalClose()">Close</button>	
	</div>
	
	<div id="modalRedirect" style="position:absolute; top: 200px; left: 200px; border: 1px solid black; background: white;">
		<%@include file="VisualizarProduto.jsp" %>
		<br>
		<button onclick="modalRedirectClose()">Close</button>	
	</div>
	
	<%
		List<Produto> produtos = RepositorioProdutos.getCurrentInstance().readAll();
	%>
	
	<table border="1" style="margin-bottom: 15px;">
		<thead>
			<tr>
				<th>Código</th>
				<th>Nome</th>
				<th>Marca</th>
				<th>Categoria</th>
				<th>Operações</th>
			</tr>
		</thead>		
		<tbody>
		<%
			for(Produto p : produtos){ 
		%>
			<tr>
				<td><%= p.getCodigo() %></td>
				<td><%= p.getNome() %></td>
				<td><%= p.getMarca() %></td>
				<td><%= p.getCategoria() %></td>
				<td><a href="ProdutoServlet?codigo=<%=p.getCodigo() %>&redirect=visualiza">Visualizar </a>
				    <a href="ProdutoServlet?codigo=<%=p.getCodigo() %>&redirect=atualiza">Atualizar </a>
				    <a href="#" onclick="deletar(<%= p.getCodigo() %>)">Deletar </a></td>
			</tr>		
		<% } %>
		</tbody>	
	</table>
	
	<a href="index.html">Home</a>
	
	<script>
		var modal = document.getElementById("modal");		
		
		var modalRedirect = document.getElementById("modalRedirect");
		
		<% 
			String redirect = request.getParameter("redirect"); 
			if(redirect == null){		
		%>
				document.body.removeChild(modal);
				document.body.removeChild(modalRedirect);				
		<% } else if(redirect.equals("visualiza")){ %>		
				document.body.removeChild(modal);				
		<% } else { %>
				document.body.removeChild(modalRedirect);
		<% } %>
		
		function modalOpen(){
			document.body.appendChild(modal);
		}
		
		function modalClose() {
			document.body.removeChild(modal);
		}
		
		function modalRedirectClose() {
			document.body.removeChild(modalRedirect);
		}
		
		function deletar(codigo){
			fetch("ProdutoServlet?codigo="+codigo,{method:'delete'})
				.then(function(response){
				location.reload();
			});
		};
	</script>

</body>
</html>