<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="br.recife.edu.ifpe.customtags" prefix="ifpe"%>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Produtos em estoque</title>
</head>
<body>
	<jsp:include page="index.html" />
	<ifpe:carregarLista carregar="estoque" />

	<h3 class="text-primary col-md-6 mt-5">Produtos no estoque</h3>

	<table class="table table-hover ml-2 mr-2">
		<thead class="table-info">
			<tr class="table-active">
				<th>Código</th>
				<th>Nome do produto</th>
				<th>Marca</th>
				<th>Categoria</th>
				<th>Quantidade em estoque</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listaEstoque}">
				<tr>
					<td><c:out value="${item.codigo}"/></td>
					<td><c:out value="${item.produto.nome}"/></td>
					<td><c:out value="${item.produto.marca}"/></td>
					<td><c:out value="${item.produto.categoria}"/></td>
					<td><c:out value="${item.quantidade}"/></td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>

</body>
</html>