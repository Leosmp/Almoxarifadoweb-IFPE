<%@page import="br.recife.edu.ifpe.model.repositorios.RepositorioFuncionario"%>
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

	<h1>Funcionários Cadastrados</h1>
	
	<%
		List<Funcionario> list = RepositorioFuncionario.getCurrentInstance().readAll();
	%>
	
	<button id="btnCad" onclick="cadastrar()" style="margin-bottom: 15px;">Novo Funcionário</button>

	<table border="1" style="margin-bottom: 15px;">
		<thead>
			<tr>
				<th>Código</th>
				<th>Nome</th>
				<th>Departamento</th>
				<th colspan="2">Operações</th>
			</tr>
		</thead>
		<tbody>
			<%
			for(Funcionario f : list){
		%>
			<tr>
				<td><%= f.getCodigo() %></td>
				<td><%= f.getNome() %></td>
				<td><%= f.getDepartamento() %></td>
				<td><a href="FuncionarioServlet?codigo=<%=f.getCodigo() %>&action=editar">Atualizar | </a>
				    <a href="#" onclick="deletar(<%= f.getCodigo() %>)">Deletar </a>
			    </td>
			</tr>		
		<% } %>
		</tbody>
	</table>
	<a href="index.html">Home</a>
	
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