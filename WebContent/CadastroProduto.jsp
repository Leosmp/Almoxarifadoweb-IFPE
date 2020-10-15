<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Cadastro de Produto</title>
</head>
<body>
	<jsp:include page="index.html"></jsp:include>
	<div class="container-fluid">
		<h1 class="text-primary col-md-6 mt-5">Cadastro de Produtos</h1>

		<form action="ProdutoServlet" method="POST">
			<div class="form-row">
				<div class="form-group col-md-1">
					<input type="text" name="codigo" readonly="readonly"
						value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.codigo : ''}"
						class="form-control">
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-4">
					<input 	type="text" name="nome" placeholder="Insira o nome do produto"
							value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.nome : ''}"
							class="form-control">
				</div>
				<div class="form-group col-md-4">
					<input type="text" name="marca"
						placeholder="Insira a marca do produto"
						value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.marca : ''}"
						class="form-control">
				</div>
				<div class="form-group col-md-4">
					<input type="text" name="categoria"
						placeholder="Insira a categoria do produto"
						value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.categoria : ''}"
						class="form-control">
				</div>
			</div>
			
			<div class="input-group mb-3">
			  <div class="input-group-prepend">
			    <span class="input-group-text">Descrição</span>
			  </div>
			  <textarea name="descricao" class="form-control" aria-label="descricao"></textarea>
			</div>

			<input 	type="hidden"
					name="${(param.redirect != null && param.redirect eq 'atualiza')? 'atualizar': ''}"
					value="atualizar"
					class="btn btn-primary"> 
			<input 	type="submit"
					value="${(param.redirect != null && param.redirect eq 'atualiza')? 'atualizar': 'Cadastrar'}"
					class="btn btn-primary"> 
			<input 	type="reset" 
					value="Limpar" 
					class="btn btn-primary">
		</form>
	</div>
</body>
</html>
