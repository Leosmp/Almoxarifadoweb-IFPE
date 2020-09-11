<%@page import="br.recife.edu.ifpe.model.classes.Produto"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Produto cadastrado</h1>

	<%
		Produto produto = (Produto) request.getAttribute("produto");

	if (produto != null) {
	%>

	<table border="1" style="margin-bottom: 15px;">
		<thead>
			<tr>
				<th>Código</th>
				<th>Nome</th>
				<th>Marca</th>
				<th>Categoria</th>
				<th>Descrição</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><%=produto.getCodigo()%></td>
				<td><%=produto.getNome()%></td>
				<td><%=produto.getMarca()%></td>
				<td><%=produto.getCategoria()%></td>
				<td><%=produto.getDescricao()%></td>
			</tr>
		</tbody>
	</table>
	<%
		}
	%>
</body>
</html>