<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt"  prefix="fmt"%>
<%@taglib prefix="ifpe" uri="br.recife.edu.ifpe.customtags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lista de Itens de Saída</title>
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
	<ifpe:carregarLista carregar="lotesaida"/>

	<h4 class="text-info mt-4 mb-4 ml-4">Listagem de Lotes de saída</h4>

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
			<c:forEach var="item" items="${listLoteSaida}">
				<tr>
					<td><fmt:formatDate pattern="dd/MM/yyyy" value="${item.data}" type="date"/></td>
					<td>${item.codigo}</td>
					<td>${item.quantidadeTotal}</td>
					<td>
						<a href='#' onclick="carregarItens(${item.codigo})">visualiza itens</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<!-- Modal -->
	<div class="modal fade" id="modalVisualizar" tabindex="-1"
		role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div id="modalHeader">
					<h5 class="modal-title" id="modalLabel">Lista de Itens do Lote</h5>
				</div>
				<div class="modal-body" id="modalBody"></div>
				<div class="modal-footer">
					<button type="button" id="modalButton" data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
	
	<script>

		document.getElementById("modalButton").addEventListener("click",function(){
			modalBody.innerHTML = "";
		 });
	
		function carregarItens(codigo){			
			
			fetch("LoteSaidaServlet?codigo="+codigo,{method:"get"}).then(function(response){
                response.text().then(function(text){
                    let objeto = JSON.parse(text);
                    
                    let modalVisualizar = document.getElementById("modalVisualizar");

			       	let modalBody = document.getElementById("modalBody");
			      	document.getElementById("modalHeader").className = "modal-header text-info";
			      	document.getElementById("modalButton").className = "btn btn-info"                      
                    
                    let tabela = document.createElement("table");
                    tabela.className = "table table-bordered";

                    let th1 = document.createElement("th");
                    let th2 = document.createElement("th");
                    let th3 = document.createElement("th");

                   th1.appendChild(document.createTextNode("Código"));
                   th2.appendChild(document.createTextNode("Nome"));
                   th3.appendChild(document.createTextNode("Quantidade"));

                   tabela.appendChild(th1);
                   tabela.appendChild(th2);
                   tabela.appendChild(th3);
					
                    modalBody.appendChild(tabela);
                    
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

                    $("#modalVisualizar").modal({backdrop: 'static', keyboard: true, show: true	});  
                    
                });
            });
			}

	</script>
</body>
</html>