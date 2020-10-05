<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt"  prefix="fmt"%>
<%@taglib prefix="ifpe" uri="br.recife.edu.ifpe.customtags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista de Itens de Entrada</title>
<style>
	.modal1{
		position: absolute;
		background: white;
		top: 100px;
		left: 100px;
		width: 250px;
	}
</style>
</head>
<body>
	<jsp:include page="index.html" />

	<c:out value="${msg}" />
	<c:remove var="msg" scope="session"/>
	<ifpe:carregaritens carregar="loteentrada"/>

	<table class="table table-hover">
		<thead class="table-info">
			<tr class="table-active">
				<th>Data</th>
				<th>Código</th>
				<th>Quantidade Total</th>
				<th>Visualizar</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${listaLoteEntrada}">
				<tr>
					<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.data}" type="date"/></td>
					<td>${item.codigo}</td>
					<td>${item.quantidadeTotal }</td>
					<td>
						<a href='#' onclick="carregarItens(${item.codigo})">visualiza itens</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script>

		var meuDiv;
	
		function carregarItens(codigo){			
			
			fetch("LoteEntradaServlet?codigo="+codigo,{method:"get"}).then(function(response){
                response.text().then(function(text){
                    let objeto = JSON.parse(text);
                    
                    meuDiv = document.createElement("div");
                    meuDiv.setAttribute("class","modal1");
                    
                    document.body.appendChild(meuDiv);
                    
                    //meuDiv.innerHTML = objeto.codigo+"<br>"+objeto.descricao+"<br>";
                    
                    let tabela = document.createElement("table");
                    tabela.setAttribute("border","1");
                    
                    meuDiv.appendChild(tabela);
                    
                    for(let i = 0; i < objeto.itens.length; i++){
                        let tr = document.createElement("tr");
                        let td1 = document.createElement("td");
                        td1.innerHTML = objeto.itens[i].codigo;
                        let td2 = document.createElement("td");
                        td2.innerHTML = objeto.itens[i].nomeProduto;
                        let td3 = document.createElement("td");
                        td3.innerHTML = objeto.itens[i].quantidade;
                        
                        tr.appendChild(td1);
                        tr.appendChild(td2);
                        tr.appendChild(td3);
                        tabela.appendChild(tr);
                    }
                    
                    let botao = document.createElement("button");
                    botao.appendChild(document.createTextNode("fechar"));
                    
                    meuDiv.appendChild(botao);
                    
                    botao.addEventListener("click",function(){
                        document.body.removeChild(meuDiv);
                        meuDiv = "";
                    });
                    
                });
            });
			}

	</script>
</body>
</html>