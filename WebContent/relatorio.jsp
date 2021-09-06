<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="br.recife.edu.ifpe.customtags" prefix="ifpe"%>
<%@taglib uri='http://java.sun.com/jstl/core_rt' prefix='c'%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Relatório</title>
</head>
<body>
	<jsp:include page="index.html" />

	<div class="container-fluid">

		<h4 class="mt-4 text-primary font-weight-bold">Relatório dos
			Lotes Cadastrados</h4>

		<form id="formRelatorio" action="RelatorioServlet" method="post">

			<div class="form-row mt-3 form-inline">
				<div class="form-group col-md-3">
					<label class="text-primary pr-2 font-weight-bold" for="dataInicio">Data
						início</label> <input class="form-control" id="dataInicio"
						name="dataInicio" type="date" />
				</div>
				<div class="form-group col-md-3">
					<label class="text-primary pr-2 font-weight-bold" for="dataFIM">Data
						final</label> <input class="form-control" id="dataFim" name="dataFim"
						type="date" />
				</div>
				<div class="form-group col-md-3">
					<label class="text-primary pr-2 font-weight-bold" for="dataInicio">Selecione
						o lote</label> <select class="custom-select" id="selectLote" name="lote">
						<option selected>Selecione</option>
						<option value="loteEntrada">Lote de Entrada</option>
						<option value="loteSaida">Lote de Saida</option>
					</select>
				</div>
				<div class="form-group col-md-3">
					<input id="btnInput" class="btn btn-info" type="submit" />
				</div>
			</div>
		</form>
		<c:if test="${exibir}">

			<hr>
			<c:out value="${action}"></c:out>

			<table class="table table-hover">
				<c:if test="${lote eq 'loteEntrada'}">
					<thead class="table-info">
						<tr class="table-active">
							<th>Data</th>
							<th>Código</th>
							<th>Quantidade Total</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${listaLoteEntrada}">

							<tr>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${item.data}" type="date" /></td>
								<td>${item.codigo}</td>
								<td>${item.quantidadeTotal}</td>
							</tr>
						</c:forEach>
				</c:if>
				<c:if test="${lote eq 'loteSaida'}">
					<thead class="table-info">
						<tr class="table-active">
							<th>Data</th>
							<th>Código</th>
							<th>Funcionário</th>
							<th>Quantidade Total</th>
							<th>Visualizar</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${listaLoteSaida}">
							<tr>
								<td><fmt:formatDate pattern="dd/MM/yyyy"
										value="${item.data}" type="date" /></td>
								<td>${item.codigo}</td>
								<td>${item.responsavel.nome}</td>
								<td>${item.quantidadeTotal}</td>
							</tr>
						</c:forEach>
				</c:if>
				</tbody>
			</table>
		</c:if>
	</div>

	<!-- Modal Erro-->
	<div class="modal fade" id="modalErro" tabindex="-1" role="dialog"
		aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div id="modalHeader">
					<h5 class="modal-title" id="modalLabel"></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" id="modalBody"></div>
				<div class="modal-footer">
					<button type="button" id="modalButton" data-dismiss="modal">Voltar</button>
				</div>
			</div>
		</div>
	</div>	
	
	<script>
		document.getElementById("formRelatorio").addEventListener("submit", myValidation);
		
		function myValidation() {
			event.preventDefault();
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			
		   if(dataFim < dataInicio)
		   {
			   document.getElementById("modalLabel").innerHTML = "Erro na consulta";
		       document.getElementById("modalBody").innerHTML = "Data inicial não pode ser maior que a data final!";
		       document.getElementById("modalHeader").className = "modal-header text-danger";
		       document.getElementById("modalButton").className = "btn btn-danger"
		       $("#modalErro").modal("show");
		   } else{
			   document.getElementById("formRelatorio").submit();
		   }
		   
		}		
	</script>

</body>
</html>