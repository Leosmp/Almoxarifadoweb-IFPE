<%@page
	import="br.recife.edu.ifpe.model.repositorios.RepositorioFuncionario"%>
<%@page import="br.recife.edu.ifpe.model.classes.Funcionario"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listagem de Funcionários</title>
</head>
<body>
	<jsp:include page="index.html"></jsp:include>
	<div class="container-fluid">
		<h3 class="text-primary col-md-3 mt-5">Funcionários Cadastrados</h3>
		<%
			List<Funcionario> list = RepositorioFuncionario.getCurrentInstance().readAll();
		%>		
		<table class="table table-hover">
			<thead class="thead-light">
				<tr class="table-active">
					<th >Código</th>
					<th>Nome</th>
					<th>Departamento</th>
					<th colspan="2">Operações</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (Funcionario f : list) {
				%>
				<tr>
					<td><%=f.getCodigo()%></td>
					<td><%=f.getNome()%></td>
					<td><%=f.getDepartamento()%></td>
					<td><a
						href="FuncionarioServlet?codigo=<%=f.getCodigo()%>&action=editar">Atualizar
							| </a> <a href="#" onclick="deletar(<%=f.getCodigo()%>)">Deletar
					</a></td>
				</tr>
				<%
					}
				%>
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