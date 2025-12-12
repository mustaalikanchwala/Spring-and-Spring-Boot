import java.sql.*;

public class JDBCLearning {
    private static final String URL = "jdbc:mysql://localhost:3306/mustaali";
    private static final String USERNAME = "mustaali";
    private static final String PASS = "mustaali";
    public static void main(String [] args){
//        Try with Resources
//        no need of finally block , it will automatically close the resource which it used
        try(Connection conn = DriverManager.getConnection(URL,USERNAME,PASS)){
            System.out.println("DB CONNECTED");
//            insertstd(conn, "naman", 90);
//            insertstd(conn, "abhisek", 80);
//            insertstd(conn, "rohan", 75);
//            insertstd(conn, "halde", 00);
            updateStd(conn,5,"sooooooo",8);
//            deletestd(conn , 8);
            selectStd(conn);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

//    INSERT VALUES
    public static void insertstd(Connection conn , String name , int marks){
        String insertSql = "INSERT INTO students (name,marks) VALUES ('"+name+"','"+marks+"')";
        try (Statement stmt = conn.createStatement()){
            int rows = stmt.executeUpdate(insertSql);
            System.out.println("INSERTED ROWS : "+rows);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

//    Select All Students
    public static void selectStd(Connection conn){
        String selectSql = "SELECT * FROM students";
        try(Statement stmt = conn.createStatement()){
           ResultSet result = stmt.executeQuery(selectSql);
            System.out.println("ALL Students : ");
            while(result.next()){
                int id = result.getInt("id");
                String name = result.getString("name");
                int marks = result.getInt("marks");
                System.out.println("ID : "+id+" Name : "+name+" Marks : "+marks);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

//    get user by id
    private static void getStdById(Connection conn , int id){
        String getOneStd = "SELECT * FROM students WHERE id = '"+id+"'";
        try(Statement stmt = conn.createStatement()){
           ResultSet result =  stmt.executeQuery(getOneStd);
            System.out.println("Student with ID :");
            if(result.next()){
                int stdid = result.getInt("id");
                String name = result.getString("name");
                int marks = result.getInt("marks");
                System.out.println("ID : "+stdid+" Name : "+name+" Marks : "+marks);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

//    Update User
    private static void updateStd(Connection conn,int id,String name,int marks){
//        Normal Statement (Unsafe sql injection attack);
//        String updateSql = "UPDATE students SET name = '"+name+"' , marks = '"+marks+"' WHERE id = '"+id+"' ";
//        Prepared Statement
        String updateSql = "UPDATE students SET name = ? , marks = ? WHERE id = ? ";
        try(PreparedStatement pstmt = conn.prepareStatement(updateSql)){
            pstmt.setString(1,name);
            pstmt.setInt(2,marks);
            pstmt.setInt(3,id);
            int rows = pstmt.executeUpdate();
            System.out.println("Updated Rows : "+rows);
            System.out.println("Updated data : ");
            getStdById(conn,id);
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

//    Delete User
    private static void deletestd(Connection conn , int id){
        String deleteSql = "DELETE FROM students WHERE id = '"+id+"' ";
        try (Statement stmt = conn.createStatement()){
            int rows = stmt.executeUpdate(deleteSql);
            System.out.println("DELETED ROWS : "+rows);
            System.out.println("DELETED USER ID : "+id);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}


/*
  Normal Try Catch
 Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USERNAME,PASS);
            System.out.println("DB CONNECTED");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conn.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

*/
