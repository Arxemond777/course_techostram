package courses.EightLearning;

import java.sql.*;
import java.util.*;

public class SimlpeExampleQuery
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost/EMP";
    static final String DB_URL = "jdbc:mysql://localhost/test?useSSL=false";

    static final String USER = "root";
    static final String PASS = "123";

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        SimlpeExampleQuery.simpleExample();
    }

    static void simpleExample() {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER).newInstance();

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name FROM test.test";
            ResultSet rs = stmt.executeQuery(sql);

            Map<String, String> map = new HashMap<>();
            Set<Map<String, String> > item = new HashSet<>();
            while(rs.next()){
                map.put("id", rs.getString("id"));
                map.put("name", rs.getString("name"));

                item.add(map);
            }
            System.out.println(item);

            //STEP 5: Extract data from result set
//            while(rs.next()){
//                //Retrieve by column name
//                int id  = rs.getInt("id");
//                String name = rs.getString("name");
//
//                //Display values
//                System.out.println("ID: " + id + ", Name: " + name);
//            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }
}
