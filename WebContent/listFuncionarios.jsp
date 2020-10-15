<%@page import="br.recife.edu.ifpe.model.classes.Funcionario"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<%@taglib uri="br.recife.edu.ifpe.customtags" prefix="ifpe" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listagem de Funcionários</title>
<body>
	<jsp:include page="index.html"></jsp:include>
	
	<div class="container-fluid">
		<h3 class="text-primary col-md-6 mt-5">Funcionários Cadastrados</h3>
		
		<table class="table table-hover">
			<thead class="table-info">
				<tr class="table-active">
					<th >Código</th>
					<th>Nome</th>
					<th>Departamento</th>
					<th colspan="2">Operações</th>
				</tr>
			</thead>
			<tbody>
				<ifpe:carregarLista carregar="funcionario"/>
				<c:forEach var="item" items="${listFuncionario}">
					<tr>
						<td><c:out value="${item.codigo}"/></td>
						<td><c:out value="${item.nome}"/></td>
						<td><c:out value="${item.departamento}"/></td>
						<td>
							<a href="FuncionarioServlet?codigo=${item.codigo}&action=editar" class="pr-3">
								<i class="fas fa-edit"></i>
							</a> 
							<a href="#" onclick="deletar(${item.codigo})" class="text-danger pl-3">
								<i class="far fa-trash-alt"></i>
							</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<button id="btnCad" class="btn btn-primary mb-4" onclick="cadastrar()">Novo Funcionário</button>

	</div>

	<script>
		function cadastrar(){
			window.location.href = "funcionarios.jsp?action=cadastro";
		}

		function deletar(codigo){
			fetch("FuncionarioServlet?codigo="+codigo,{method:'delete'})
				.then(function(response){
				location.reload();
			});
		};
		
	</script>
</body>
</html>