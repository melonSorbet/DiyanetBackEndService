package example.provider;

import java.sql.Connection;
import java.sql.DriverManager;

public class diyanetApiToDb {
   public static void main(String[] args){
       String url = "jdbc:mysql://localhost:3306/jdbcdemo";
       try{

           Class.forName("org.sqlite.JDBC");

           Connection connection = DriverManager.getConnection(url);
       }
       catch(Exception e){
           System.out.println(e);
       }
   }

}
