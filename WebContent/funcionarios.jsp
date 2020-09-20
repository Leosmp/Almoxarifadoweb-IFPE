<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Cadastro de funcionários</title>
</head>
<link rel="stylesheet" href="bootstrap.min.css">
<body>
	<div class="container">
		<h1>Cadastro de Funcionários</h1>

		<form action="FuncionarioServlet" method="POST">
			<div class="form-group">
				<div>
					<label class="col-sm-1 col-form-label"> Código: </label>					
					<input type="text" name="codigo" class="col-sm-11" 
						value="${func.codigo }"
						style="width: 100px;">					
				</div>
			<div>
					<label class="col-sm-1 col-form-label">Nome: </label>
					<input type="text" name="nome" class="col-sm-11"
						placeholder="Insira o nome"
						value="${func.nome }"
						style="width: 250px;">
					
			</div>
			<div>
					<label class="col-sm-2 col-form-label">Departamento: </label>
					<input type="text" name="departamento" class="col-sm-10"
						placeholder="Insira o departarmento"
						value="${func.departamento }"
						style="width: 250px;">
					
			</div>
				<input 	type="submit" 
						value= "Cadastrar" name= "cadastro"	id="submit"	class="btn btn-primary"> 
				<input type="reset" value="Limpar" class="btn btn-primary">
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