package suiviDesRepas.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suiviDesRepas.BusinessException;
import suiviDesRepas.bll.RepasManager;


/**
 * Servlet implementation class ServletAjoutRepas
 */
@WebServlet("/ajoutrepas")
public class ServletAjoutRepas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAjoutRepas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajout.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Je lis les paramètres
		LocalDate date=null;
		LocalTime heure=null;
		String repas=null;
		request.setCharacterEncoding("UTF-8");
		List<Integer> listeCodesErreur=new ArrayList<>();
		//lecture date
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			date = LocalDate.parse(request.getParameter("date"),dtf);
		}
		catch(DateTimeParseException e)
		{
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_REPAS_DATE_ERREUR);
		}
		//lecture heure
		try
		{
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm");
			heure= LocalTime.parse(request.getParameter("heure"),dtf1);
		}
		catch(DateTimeParseException e)
		{
			e.printStackTrace();
			listeCodesErreur.add(CodesResultatServlets.FORMAT_REPAS_HEURE_ERREUR);
		}
		//lecture repas
		repas = request.getParameter("repas");
		if(repas==null || repas.trim().isEmpty())
		{
			listeCodesErreur.add(CodesResultatServlets.FORMAT_REPAS_ALIMENT_ERREUR);
		}
		
		//Réalisation du traitement
		if(listeCodesErreur.size()>0)
		{
			//Je renvoie les codes d'erreurs
			request.setAttribute("listeCodesErreur",listeCodesErreur);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajout.jsp");
			rd.forward(request, response);
		}
		else
		{
			//J'ajoute le repas
			RepasManager repasManager = new RepasManager();
			try {
				repasManager.ajouterRepas(date, heure, Arrays.asList(repas.split(",")));
				//Si tout se passe bien, je vais vers la page de consultation:
				RequestDispatcher rd = request.getRequestDispatcher("/repas");
				rd.forward(request, response);
			} catch (BusinessException e) {
				//Sinon je retourne à la page d'ajout pour indiquer les problèmes:
				e.printStackTrace();
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/ajout.jsp");
				rd.forward(request, response);
			}
			
		}
	}

}








