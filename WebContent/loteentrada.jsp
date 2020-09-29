<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="ifpe" uri="br.recife.edu.ifpe.customtags" %>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="index.html"></jsp:include>
	<h1>Cadastro de lote de entrada</h1>
	
	<ifpe:carregaprodutos/>
	
	<table class="table table-hover">
			<thead class="table-info">
				<tr class="table-active">
					<th >Código</th>
					<th>Nome</th>
					<th>Marca</th>
					<th>Categoria</th>
					<th colspan="2">Operações</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="pAux" items="${produtos}">
					<tr>
						<td>${pAux.codigo}</td>
						<td>${pAux.nome}</td>
						<td>${pAux.marca}</td>
						<td>${pAux.categoria}</td>
						<td>
							<a href="#" class="text-dark pl-3" onclick="inserir(${pAux.codigo})">
								<i class="fas fa-plus-square"></i>
							</a>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
	</table>
	
	<hr>
	
	<table class="table table-hover">
			<thead class="table-info">
				<tr class="table-active">
					<th >Código</th>
					<th>Nome</th>
					<th>Marca</th>
					<th>Categoria</th>
					<th>Quantidade</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${loteEntrada.itens}">
					<tr>
						<td>${item.produto.codigo}</td>
						<td>${item.produto.nome}</td>
						<td>${item.produto.marca}</td>
						<td>${item.produto.categoria}</td>
						<td>${item.quantidade}</td>
						<td>
							<a href="#" class="text-dark pl-3" onclick="inserir(${pAux.codigo})">
								<i class="fas fa-plus-square"></i>
							</a>
						</td>
					</tr>
				</c:forEach>			
			</tbody>
	</table>
	
	<script>
		function inserir(codigo){
			fetch("LoteEntradaServlet?codigo=" + codigo,{method:'put'})
				.then(function(){
					location.reload();
			});
	};
	</script>
</body>
</html>