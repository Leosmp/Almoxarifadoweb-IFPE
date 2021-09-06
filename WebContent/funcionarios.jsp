<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Cadastro de funcionários</title>
</head>

<body>
	<jsp:include page="index.html"/>
	<div class="container-fluid">
		<h4 class="text-primary col-md-6 mt-3">Cadastro de Funcionários</h4>

		<form action="FuncionarioServlet" method="POST">
			<div class="form-row">
				<div class="form-group col-md-1">								
					<input type="text" name="codigo" class="form-control" readonly="readonly"
						value="${func.codigo }">					
				</div>
			</div>
			
			<div class="form-row">
				<div class="form-group col-md-3">					
					<input type="text" name="nome" class="form-control bg-light"
						placeholder="Insira o nome"
						value="${func.nome }">					
				</div>
				<div class="form-group col-md-3">					
					<input type="text" name="departamento" class="form-control bg-light"
						placeholder="Insira o departarmento"
						value="${func.departamento }">					
				</div>
			</div>
				
				<input 	type="submit" 
						value= "Cadastrar" name= "cadastro"	id="submit"	class="btn btn-primary"> 
				<input type="reset" value="Limpar" class="btn btn-primary">			
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