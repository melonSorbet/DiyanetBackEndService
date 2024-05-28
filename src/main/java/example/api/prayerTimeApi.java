package example.api;

import java.sql.*;

public class prayerTimeApi {
    public static void initializeDaysTable(String url, String filename){
        try(Connection conn = DriverManager.getConnection(url + filename); Statement stmt = conn.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS Days (\n"
                    + "	id DATE PRIMARY KEY,\n"
                    + "	fajr TIME \n"
                    + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void initializeCountryTable(String url, String filename){
        try(Connection conn = DriverManager.getConnection(url + filename); Statement stmt = conn.createStatement()){
            String sql = "CREATE TABLE IF NOT EXISTS Days (\n"
                    + "	id DATE PRIMARY KEY,\n"
                    + "	fajr TIME \n"
                    + ")";
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createDatabase(String fileName, String url) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try(Connection conn = DriverManager.getConnection(url + fileName)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args){
        String url = "jdbc:sqlite:/home/user/Development/prayerTimeApi/src/main/java/org/prayertimes/java/com/example/database/";
    }
}
