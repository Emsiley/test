<%@page import="suiviDesRepas.bo.Aliment"%>
<%@page import="suiviDesRepas.bo.Repas"%>
<%@page import="suiviDesRepas.messages.LecteurMessage"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Consultation</title>
</head>
<body>
	<h1>HISTORIQUE</h1>
	<div class="contenu">
		<%
		String dateFiltre="";
		if(request.getParameter("dateFiltre")!=null)
		{
			dateFiltre=request.getParameter("dateFiltre");
		}
		%>
		
		<form action="<%=request.getContextPath()%>/repas" method="post">
			<input type="date" name="dateFiltre" value="<%=dateFiltre%>"/>
			<input type="submit" value="Filtrer"/>
			<a href="<%=request.getContextPath()%>/repas"><input type="button" value="Réinitialiser"/></a>
		</form>
	
		<%
			List<Integer> listeCodesErreur = (List<Integer>)request.getAttribute("listeCodesErreur");
			if(listeCodesErreur!=null)
			{
		%>
				<p style="color:red;">Erreur :</p>
		<%
				for(int codeErreur:listeCodesErreur)
				{
		%>
					<p><%=LecteurMessage.getMessageErreur(codeErreur)%></p>
		<%	
				}
			}
		%>
	
		<table align="center">
			<thead>
				<tr>
					<td>Date</td>
					<td>Heure</td>
					<td>Action</td>
				</tr>
			</thead>
				<%
					List<Repas> listeRepas = (List<Repas>) request.getAttribute("listeRepas");
					if(listeRepas!=null && listeRepas.size()>0)
					{
				%>
						<tbody>
							<%
							for(Repas repas : listeRepas)
							{
							%>
								<tr>
									<td><%=repas.getDate_repas()%></td>
									<td><%=repas.getHeure_repas()%></td>
									
									<td><a href="<%=request.getContextPath()%>/repas?detail=<%=repas.getId()%>&<%=dateFiltre%>">détail</a></td>
								</tr>
							<%
								if(String.valueOf(repas.getId()).equals(request.getParameter("detail")))
								{
							%>
									<tr>
										<td colspan="3">
											<ul>
												<%
												for(Aliment aliment:repas.getListeAliments())
												{
												%>
													<li><%=aliment.getNom()%></li>
												<%
												}
												%>
											</ul>
										</td>
									</tr>
							<%
								}
							}
							%>
						</tbody>
				<%
					}
					else
					{
				%>
					<p>Il n'y a aucun repas à afficher<P>
				<%
					}
				%>
	
			
			
		</table>
	
		<a href="<%=request.getContextPath()%>/ajoutrepas"><input type="button" value="Ajouter un nouveau repas"/></a>
		<a href="<%=request.getContextPath()%>"><input type="button" value="Retour à l'accueil"/></a>
		
	</div>
	
</body>
</html>