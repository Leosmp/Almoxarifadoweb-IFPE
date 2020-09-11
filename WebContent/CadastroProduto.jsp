<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div>
		<h1 style="margin-left: 10px;">Cadastro de Produtos</h1>

		<form action="ProdutoServlet" method="POST">
			<div style="margin-left: 10px;">
				<div style="padding-bottom: 5px;">
					<label> Código: 
						<input type="text" name="codigo" value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.codigo : ''}"
						style="width: 100px; margin-left: 16px;">
					</label>
				</div>
				<div style="padding-bottom: 5px;">
					<label>Nome: 
						<input type="text" name="nome" placeholder="Insira o nome do produto" value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.nome : ''}"
						style="width: 250px; margin-left: 24px;">
					</label>
				</div>
				<div style="padding-bottom: 5px;">
					<label>Marca: 
						<input type="text" name="marca"	placeholder="Insira a marca do produto" value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.marca : ''}"
						style="width: 250px; margin-left: 24px;">
					</label>
				</div>

				<div style="padding-bottom: 5px;">
					<label>Categoria: 
						<input type="text" name="categoria"	placeholder="Insira a categoria do produto" value="${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.categoria : ''}"
						style="width: 250px;">
					</label>
				</div>

				<div style="margin-bottom: 15px;">
					<label>Descrição: 
						<textarea name="descricao"
						style="width: 250px; height: 100px; vertical-align: top">${(param.redirect != null && param['redirect'] eq 'atualiza')? produto.descricao : ''}</textarea>
					</label>
				</div>
				
				<input type="hidden" name="${(param.redirect != null && param.redirect eq 'atualiza')? 'atualizar': ''}" value="atualizar">

				<input type="submit" value="${(param.redirect != null && param.redirect eq 'atualiza')? 'atualizar': 'Cadastrar'}"
					style="height: 25px; margin-right: 15px;"> <input
					type="reset" value="Limpar" style="height: 25px;">
			</div>
		</form>
	</div>

</body>
</html>
