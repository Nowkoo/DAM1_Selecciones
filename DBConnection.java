public class DBConnection {
    private static DBConnection instance;
    private static java.sql.Connection connection;
    //Constructor privado
    private DBConnection(String url, String user, String password){
        setConnection(url, user, password);
    }
    public static java.sql.Connection getConnection(String url, String user, String password){
        if (instance == null){
            instance = new DBConnection(url, user, password);
        }else {
            try {
                if (connection == null || connection.isClosed()) {
                    setConnection(url, user, password);
                }
            } catch (java.sql.SQLException e) {
                System.out.println("Connecting error: isClosed() " + e.getMessage());
            }
        }
        return connection;
    }
    private static void setConnection(String url, String user, String password){
        try {
            connection = java.sql.DriverManager.getConnection(url, user, password);
            System.out.println("Conexión realizada");
        }catch(java.sql.SQLException e){
            System.out.println("Connecting error: getConnection() " + e.getMessage());
        }
    }
}