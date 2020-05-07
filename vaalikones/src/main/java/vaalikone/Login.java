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
		
		// Change back the message to "none" after getting error message caused by authentication failure
		request.getSession().setAttribute("message", "");
		request.getRequestDispatcher("/login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Establish connection with database
		EntityManagerFactory emf = null;
		EntityManager em = null;

		try {
			emf = Persistence.createEntityManagerFactory("vaalikones");
			em = emf.createEntityManager();
		} catch (Exception e) {
			e.printStackTrace(response.getWriter());
			return;
		}

		// Get the username and password given by the user
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Initialise a variable for error message
		String message = "";

		
		try {
			// Select the row from users table where the username given by the user can be found (if that's correct)
			Query q = em.createQuery("SELECT u FROM User u WHERE u.username='" + username + "'");

			// Get access to the setters getters from the Users class according to the row selected from the users table
			User user = (User) q.getSingleResult();
			// Apply the crypt function on password string (get the encrypted form of the password given by user) and compare it to the proper encrypted password from database
			if(crypt(password).equals(user.getPassword())){
				
				// If the password was correct the user will be navigated to the CRUD page
				//request.getRequestDispatcher("/testpack.ViewC").forward(request, response);
				response.sendRedirect("ViewC");
			}else { throw new Exception(); }
			

		} catch (Exception e) {

			e.printStackTrace();
			message = "Username or password is incorrect!";
			request.getSession().setAttribute("message", message);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;

		} finally {
			// Sulje tietokantayhteys
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
		
		

	}
	
	// Encryption function
	public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encrypt cannot be null or zero length");
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
