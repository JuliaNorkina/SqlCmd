package ua.com.juja.sqlCmd.model;

import java.sql.*;
import java.util.Random;

/**
 * Created by Юлия on 10.04.2016.
 */
public class Main {

    public static void main(String[] argv) throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/sqlcmd", "postgres",
                    "12345678");

        //insert
        String sql = "INSERT INTO public.user " +
                "VALUES ('Stiven', 'Pupkina')";
        update(connection, sql);

        //select
        String sql1 = "SELECT * FROM  public.user WHERE id >= 5";
        insert(connection, sql1);

        //delete
        String delete = "DELETE FROM public.user " +
                "WHERE id > 7 AND id < 9";
        update(connection, delete);

        //update
        PreparedStatement ps = connection.prepareStatement(
                "UPDATE public.user SET password = ? WHERE id > 7 ");
        ps.setString(1,"password_"+new Random().nextInt());
        ps.executeUpdate();
        ps.close();

        connection.close();
    }


    private static void insert(Connection connection, String sql1) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql1);
        while (rs.next())
        {
            System.out.println("id: " + rs.getString("id"));
            System.out.println("name :" + rs.getString("name"));
            System.out.println("password: " + rs.getString("password"));
            System.out.println("-------------");
        }
        rs.close();
        stmt.close();
    }

    private static void update(Connection connection, String sql) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
    }

}