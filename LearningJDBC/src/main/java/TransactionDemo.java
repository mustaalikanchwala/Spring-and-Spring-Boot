import java.sql.*;

public class TransactionDemo {
    private static final String URL = "jdbc:mysql://localhost:3306/mustaali";
    private static final String USER = "mustaali";
    private static final String PASS = "mustaali";

    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(URL,USER,PASS)){
            System.out.println("Connected To DB");

//            turing off the Auto Commit, it will prevent from system crash or failure if one operation succed or one fail;
                conn.setAutoCommit(false);

                try{
                    //            Order , OrderItems
//            INSERT Order
                    int orderId = insertOrder(conn,101,"Mustaali",999.987);
//            INSERT OrderItems
                    insertOrderItems(conn,orderId,"Lenovo Laptop",2,999.987);

//            After all operation success , we will cmmit the transaction
                    conn.commit();
                    System.out.println("Transaction Completed Successfully");
                }catch (Exception e){
                    e.printStackTrace();
                    conn.rollback();
                    System.out.println("Transaction Fail, RollBack all changes");
                }finally {
                    conn.setAutoCommit(true);
                    conn.close();
                }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void insertOrderItems(Connection conn, int orderId, String productName, int quantity, double price) {
        String insertSql = "INSERT INTO orderitems (order_id,product_name,quantity,price) VALUES (?,?,?,?)";
        try(PreparedStatement pstmt = conn.prepareStatement(insertSql)){
            pstmt.setInt(1,orderId);
            pstmt.setString(2,productName);
            pstmt.setInt(3,quantity);
            pstmt.setDouble(4,price);
//            int x = 10/0;
            int rows = pstmt.executeUpdate();
            System.out.println("INSERTED ROW : "+rows);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static int insertOrder(Connection conn, int userId, String userName, double orderAmt) {
        String insertSql = "INSERT INTO orders (user_id,user_name,total_amt) VALUES ( ? , ? , ? ) ";

        try(PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)){
            pstmt.setInt(1,userId);
            pstmt.setString(2,userName);
            pstmt.setDouble(3,orderAmt);
            int rows = pstmt.executeUpdate();
            System.out.println("INSERTED ROWS : "+rows);

            try(ResultSet result = pstmt.getGeneratedKeys()){
                if(result.next()){
                    int orderId = result.getInt(1);
                    System.out.println("ORDER ID : "+orderId);
                    return orderId;
                }else{
                    throw new SQLException("Order Id not generated");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
