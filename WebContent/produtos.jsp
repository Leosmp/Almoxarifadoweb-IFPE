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
		}
	%>
	
	<button style="margin-bottom: 15px; display: block;" onclick="modalOpen()">Novo Produto</button>
	<div id="modal" style="position:absolute; top: 200px; left: 200px; border: 1px solid black;">
		<%@include file="CadastroProduto.jsp" %>
		<br>
		<button onclick="modalClose()">Close</button>	
	</div>
	
	<%
		List<Produto> produtos = RepositorioProdutos.getCurrentInstance().readAll();
	%>
	
	<table>
		<thead>
			<tr></tr>
		</thead>
	
	</table>
	
	<a href="index.html">Home</a>
	
	<script>
		var modal = document.getElementById("modal");
		document.body.removeChild(modal);
		
		function modalOpen(){
			document.body.appendChild(modal);
		}
		
		function modalClose() {
			document.body.removeChild(modal);
		}
	</script>

</body>
</html>