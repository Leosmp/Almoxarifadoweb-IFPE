<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="ifpe" uri="br.recife.edu.ifpe.customtags"%>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="index.html"></jsp:include>
	<h4 class="mt-3">Cadastro de lote de entrada</h4>

	<p>
		<c:out value="${msg}"></c:out>
	</p>

	<ifpe:carregarLista carregar="produtos"/>

	<table class="table table-hover">
		<thead class="table-info">
			<tr class="table-active">
				<th>Código</th>
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
					<td><a href="#" class="text-dark pl-3"
						onclick="inserir(${pAux.codigo})"> <i
							class="fas fa-plus-square"></i>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<c:if test="${loteEntrada != null }">

		<hr>

		<table class="table table-hover">
			<thead class="table-info">
				<tr class="table-active">
					<th>Código</th>
					<th>Nome</th>
					<th>Marca</th>
					<th>Categoria</th>
					<th>Quantidade</th>
					<th colspan="2">Operações</th>
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
							<a href="#" class="text-dark pl-3" onclick="inserir(${item.produto.codigo})"> 
								<i class="fas fa-plus-square"></i>
							</a> 
							<a href="#" class="text-dark pl-3" onclick="diminuir(${item.produto.codigo})"> 
								<i class="fas fa-minus-square"></i>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<button class="btn btn-info ml-2" onclick="cadastrar()">Cadastrar</button>
	</c:if>

	<script>
		function inserir(codigo){
			fetch("LoteEntradaServlet?operacao=mais&codigo=" + codigo,{method:'put'})
				.then(function(){
					location.reload();
			});
	};

		function diminuir(codigo){
			fetch("LoteEntradaServlet?operacao=menos&codigo=" + codigo,{method:'put'})
				.then(function(){
					location.reload();
			});
	};

	function cadastrar(){
		fetch("LoteEntradaServlet",{method:'post'})
			.then(function(response){
				if(response.status === 500){
					location.reload();
					}else{
						location.href = "listaLoteEntrada.jsp";
						}
						
				
		}).catch(function(erro){
				location.reload();
		});
};
	</script>
</body>
</html>