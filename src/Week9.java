import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Week9 {
    private String username="database";
    private String password="database";
    private static Connection dbTest;

    Week9() {
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
        // 1
        String sqlStr = "SELECT maker, model, price FROM product natural join pc" + " WHERE cd = '8x' and ram >= 24";
        PreparedStatement stmt = dbTest.prepareStatement(sqlStr);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("maker : "+rs.getString("maker")
                    +" | model : "+rs.getString("model")
                    +" | price : "+rs.getString("price"));
        }

        // 2
        sqlStr = "SELECT sum(price) FROM product natural join laptop" + " WHERE screen > 11 and (maker='D' or maker = 'G')";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("sum(price) : "+rs.getString("sum(price)"));
        }

        // 3
        sqlStr = "SELECT count(model) FROM ((SELECT model FROM pc natural join product WHERE hd>2.4) UNION (SELECT model FROM laptop natural join product WHERE speed > 130))";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("cound(model) : "+rs.getString("count(model)"));
        }

        // 4
        sqlStr = "SELECT model,price FROM pc" + " WHERE cd = '8x' and speed > some (SELECT speed from laptop)";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("model : "+rs.getString("model")
                    +" | price : "+rs.getString("price"));
        }

        // 5
        sqlStr = "SELECT maker,speed FROM laptop natural join product" + " WHERE hd >= 1";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("maker : "+rs.getString("maker")
                    +" | speed : "+rs.getString("speed"));
        }

        // 6
        sqlStr = "(SELECT model FROM pc WHERE speed > (SELECT speed FROM laptop WHERE model=2005)) " +
                "union (SELECT model FROM laptop WHERE speed > (SELECT speed FROM laptop WHERE model=2005)) ";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("model : "+rs.getString("model"));
        }

        // 7
        sqlStr = "SELECT printer.model,price FROM printer,product" + " WHERE printer.model = product.model and maker = 'D' and color = 'true'";
        stmt = dbTest.prepareStatement(sqlStr);
        rs = stmt.executeQuery();
        while(rs.next()){
            System.out.println("model : "+rs.getString("model")
                    +" | price : "+rs.getString("price"));
        }

        rs.close();
        stmt.close();
    }

    public static void main(String[] args) {
        Week9 w9 = new Week9();
        try {
            w9.execute_query();
            dbTest.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException:"+e);
        }
    }

}
