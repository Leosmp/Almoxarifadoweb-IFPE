<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de funcionários</title>
</head>
<body>
	<div>
		<h1 style="margin-left: 10px;">Cadastro de Funcionários</h1>

		<form action="FuncionarioServlet" method="POST">
			<div style="margin-left: 10px;">
				<div style="padding-bottom: 5px;">
					<label> Código: <input type="text" name="codigo"
						value="${func.codigo }"
						style="width: 100px;">
					</label>
				</div>
				<div style="padding-bottom: 5px;">
					<label>Nome: <input type="text" name="nome"
						placeholder="Insira o nome"
						value="${func.nome }"
						style="width: 250px;">
					</label>
				</div>
				<div style="padding-bottom: 5px;">
					<label>Departamento: <input type="text" name="departamento"
						placeholder="Insira o departarmento"
						value="${func.departamento }"
						style="width: 250px;">
					</label>
				</div>
				<input 	type="submit" 
						value= "Cadastrar" name= "cadastro"	id="submit"				
						style="height: 25px; margin-right: 15px;"> 
				<input type="reset" value="Limpar" style="height: 25px;">
			</div>
		</form>
	</div>
	
	<script>
		<%String action = request.getParameter("action"); 
			if(action.equals("editar")){
		%>
			var action = document.getElementById("submit");
			action.setAttribute("value", "Editar");
			action.setAttribute("name", "editar")
			<%}		
		%>

	</script>

</body>
</html>