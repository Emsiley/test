package suiviDesRepas.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suiviDesRepas.BusinessException;
import suiviDesRepas.bll.RepasManager;
import suiviDesRepas.bo.Repas;

/**
 * Servlet implementation class ServletAfficherRepas
 */
@WebServlet("/repas")
public class ServletAfficherRepas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAfficherRepas() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Je lis les paramètres
		LocalDate dateFiltre=null;
		List<Integer> listeCodesErreur=new ArrayList<>();
		//lecture date
		if(request.getParameter("dateFiltre")!=null && !request.getParameter("dateFiltre").equals(""))
		{
			try
			{
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				dateFiltre = LocalDate.parse(request.getParameter("dateFiltre"),dtf);
			}
			catch(DateTimeParseException e)
			{
				e.printStackTrace();
				listeCodesErreur.add(CodesResultatServlets.FORMAT_REPAS_DATE_ERREUR);
			}
		}
		
		if(listeCodesErreur.size()>0)
		{
			request.setAttribute("listeCodesErreur",listeCodesErreur);
		}
		else
		{
			try {
				//Recherche des repas
				RepasManager repasManager = new RepasManager();
				List<Repas> listeRepas=null;
				if(dateFiltre==null)
				{
					listeRepas = repasManager.selectionnerTousLesRepas();
				}
				else
				{
					listeRepas = repasManager.selectionnerTousLesRepasDUnJour(dateFiltre);
				}
				request.setAttribute("listeRepas", listeRepas);
			} catch (BusinessException e) {
				e.printStackTrace();
				request.setAttribute("listeCodesErreur",e.getListeCodesErreur());
			}
		}
		//Transfert de l'affichage à la JSP
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/consultation.jsp");
		rd.forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
