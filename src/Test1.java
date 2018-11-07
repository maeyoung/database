import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test1 {
    private String username="database";
    private String password="database";
    private static Connection dbTest;

    Test1() {
        connectDB();
    }

    private void connectDB() {
        try {
            // JDBC Driver Loading
            Class.forName("oracle.jdbc.OracleDriver");
            dbTest = DriverManager.getConnection("jdbc:oracle:thin:" + "@localhost:1521:XE", "database", "database");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException:"+e);
        } catch (Exception e) {
            System.out.println("Exception : "+e);
        }
    }

    public void execute_query() throws SQLException {
        String sqlStr = "SELECT avg(speed) FROM pc";
        PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("avg(speed): "+rs.getString("avg(speed)"));
        }

        sqlStr = "SELECT price FROM pc" + " WHERE price>=2000";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("price : "+rs.getString("price"));
        }

        sqlStr = "SELECT model,speed,hd FROM pc" + " WHERE (cd = '6x' or cd = '8x') and price < 2000";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("model : "+rs.getString("model")+" | speed : "+rs.getString("speed")+" | hd : "+rs.getString("hd"));
        }

        rs.close();
        stmt.close();
    }

    public static void main(String[] args) {
        Test1 t1 = new Test1();
        try {
            t1.execute_query();
            dbTest.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException:"+e);
        }
    }

}
