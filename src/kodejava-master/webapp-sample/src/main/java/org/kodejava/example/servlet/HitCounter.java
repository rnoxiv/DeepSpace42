package org.kodejava.example.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;

public class HitCounter extends HttpServlet {
    public HitCounter() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        updateHitCounter();
        getHitCounterImage(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        updateHitCounter();
        getHitCounterImage(request, response);
    }

    private void updateHitCounter() {
        Connection connection = getConnection();
        try {
            // 
            // Update the hits counter table by incrementing the 
            // counter every time a user hits our page.
            //
            String sql = "UPDATE hits SET counter = counter + 1";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }

    private void getHitCounterImage(HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        Connection connection = getConnection();
        String hits = "";
        try {
            // 
            // Get the current hits counter from database.
            //
            String sql = "SELECT counter FROM hits";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hits = rs.getString("counter");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }

        // 
        // Create an image of our counter to be sent to the browser.
        //
        BufferedImage buffer = new BufferedImage(50, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffer.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 50, 20);
        g.setColor(Color.BLACK);
        g.drawString(hits, 0, 20);

        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(buffer, "png", os);
        os.close();
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/sampledb", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
