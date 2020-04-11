package vaalikone;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persist.User;

/**
 * Servlet implementation class Login
 */

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");

		request.getSession().setAttribute("message", "");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EntityManagerFactory emf = null;
		EntityManager em = null;

		try {
			emf = Persistence.createEntityManagerFactory("vaalikones");
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
			return;
		}

		String username = request.getParameter("username");
		System.out.println("username: " + username);
		String password = request.getParameter("password");
		System.out.println("password: " + password);
		String message = "";
		try {
			
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username=\"" + username + "\"");
			User user = (User) q.getSingleResult();
			System.out.println("user was: " + user);
			if(crypt(password).equals(user.getPassword())){
				response.getWriter().println("Login was successful");
			}else { throw new Exception(); }
			

		} catch (Exception e) {
			
			e.printStackTrace(response.getWriter());
			message = "Username or password is incorrect!";
			request.getSession().setAttribute("message", message);
			response.sendRedirect("login.jsp");
			return;

		} finally {
			// Sulje tietokantayhteys
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
		
		

	}
	
	public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        MessageDigest digester;
        try {
            digester = MessageDigest.getInstance("MD5");

            digester.update(str.getBytes());
            byte[] hash = digester.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
