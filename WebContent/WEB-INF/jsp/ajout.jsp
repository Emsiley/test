<%@page import="suiviDesRepas.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ajout</title>
</head>
<body>
	<h1>AJOUT : ${listeCodesErreur}</h1>
	
	<div class="contenu">
	
		
		
		<%
			List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodesErreur");
			if(listeCodesErreur!=null)
			{
		%>
				<p style="color:red;">Erreur, le repas n'a pas pu être ajouté :</p>
		<%
				for(int codeErreur:listeCodesErreur)
				{
		%>
					<p><%=LecteurMessage.getMessageErreur(codeErreur)%></p>
		<%	
				}
			}
		%>
	
		<form action="<%=request.getContextPath()%>/ajoutrepas" method="post">
			<div class="saisie">
				<label for="date">Date : </label>
				<input type="date" name="date" type="date" value="<%=request.getParameter("date")%>"/>
			</div>
			<div class="saisie">
				<label for="heure">Heure : </label>
				<input type="time" name="heure" value="<%=request.getParameter("heure")%>"/>
			</div>
			<div class="saisie">
				<label for="repas">Repas : </label>
				<textarea rows="5" cols="30" id="repas" name="repas" ><%=request.getParameter("repas")!=null?request.getParameter("repas"):""%></textarea>
			</div>
			
			<div>
				<input type="submit" value="Valider"/>
				<a href="<%=request.getContextPath()%>"><input type="button" value="Annuler"/></a>
			</div>
		</form>
	
	</div>
</body>
</html>