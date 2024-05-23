import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sendEmail")
public class EmailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String messageContent = request.getParameter("message");

        // Check if the parameters are null
        if (name == null || email == null || messageContent == null) {
            response.getWriter().write("Error: Missing form data.");
            return;
        }

        // Use your email and app-specific password directly here for testing
        final String from = "rutikjagtap89@gmail.com";
        final String password = "zqnxlkjymfspzpvk";
        final String recipient = "rutikjagtap89@gmail.com"; // Fixed recipient email address

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.debug", "true"); // Enable debugging

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Contact Form Submission from " + name);
            message.setText("Name: " + name + "\nEmail: " + email + "\nMessage: " + messageContent);

            Transport.send(message);
            response.getWriter().write("Email sent successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            response.getWriter().write("Error: unable to send email. " + mex.getMessage());
        }
    }

    @Override
    public String getServletInfo() {
        return "EmailServlet handles sending emails from a contact form";
    }
}
