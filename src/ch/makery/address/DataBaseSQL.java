package ch.makery.address;


import java.sql.*;

import com.mysql.jdbc.Statement;

import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;

public class DataBaseSQL implements Connection {
    private final String server;
    private final String user;
    private final String pass;
    private final String driver;
    private Connection connection;
    PreparedStatement pstmt;

    public DataBaseSQL(){
    	// Datos necesarios para la conexion con la base de datos
        this.driver = "com.mysql.jdbc.Driver";
        this.pass = "";
        this.user = "root";
        this.server = "jdbc:mysql://localhost/biblioteca";
            try{
                Class.forName(driver);
                connection = DriverManager.getConnection(server,user,pass); //Creamos la conexion
                //JOptionPane.showMessageDialog(null, "Conexion realizada a BD con exito.");
            }catch(ClassNotFoundException | SQLException e){
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Conexion no realizada a la base de datos.");
            }
    }

    public static void main(String[] args){
        DataBaseSQL db = new DataBaseSQL();
        String[] a = {"a", "b"};
        db.insert("sadasd", a);
    }
    //Metodo que ejecuta cualquier query, retorna booleano si se pudo realizar
    public boolean free(String q){
        try {
            Statement query = (Statement) connection.createStatement(); //Variable que ejecuta querys
            System.out.println("free: " + q);
            query.executeUpdate(q); //Ejecutamos el query
            System.out.println("Exito");
            query.close(); //Cerramos la conexion
            return true;
        } catch (SQLException e) {
            System.out.println("FAIL");
            return false;
        }
    }
    
    //Metodo que inserta valores en una tabla, NOTA: TODO se inserta en mayusculas
    public boolean insert(String tabla, String[] values){
        try {
            Statement query = (Statement) connection.createStatement();
            String q1;
            //Si la tabla es profesor, alumno o administrador, la contraseña la ingresamos tal cual
            if(tabla.equals("profesor") || tabla.equals("alumno") || tabla.equals("administrador")){
            	int i = 1;
            	q1 = "insert into " + tabla + " values("; //Vamos creando el query
                
            	for(String txt : values){ //Agregamos los valores
                    if(i < values.length)
                    	q1 += "'" + txt.toUpperCase() + "', "; //Pasamos todo a mayuscula
                    else
                    	q1 += "'" + txt + "', "; //SI es password lo mandamos tal cual
                    i++;
                }
            }else{
	            q1 = "insert into " + tabla + " values(";
	            
	            for(String txt : values){
	                if(txt.equals("default") || txt.equals("NULL")){
	                	q1 += txt + ", ";
	                }else
	                	q1 += "'" + txt.toUpperCase() + "', "; //Agregamos los valores
	            }
            }
            
            q1 = q1.substring(0, q1.length()-2); //Eliminamos la coma de mas
            q1 += ")"; 
            
            System.out.println("insert: " + q1);
            query.executeUpdate(q1); //Ejecutamos el query
            System.out.println("Exito");
            query.close();
        } catch (SQLException e) {
            System.out.println("FAIL \n error: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    //Metodo para obtener un registro de la base de datos
    public HashMap<String, String>fetchArray(String table, String delimiter, String index){
        //Todo lo almacenamos en un mapa
    	HashMap<String, String> mapa = new HashMap<String, String>();
        
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT * FROM " + table + " WHERE " + delimiter + " = " + index; //Creamos el query
            System.out.println("fetchArray: " + comando);
            ResultSet rs = query.executeQuery(comando); //Ejecutamos el query
            
            if( !rs.next() ){ 
                System.out.println("VACIO"); //Si no hay datos retornamos null
                return null;
            }
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.first();
            String valor, key;
 
            int cant = rsmd.getColumnCount(); //Obtenemos todas las columnas del resultado
            
            for(int i = 1; i <= cant; i++){ //Por cada una de las columnas
                key = rsmd.getColumnName(i); // Obtenemos la columna
                valor = rs.getString(key); //Obtenemos el valor
                //System.out.println(key + "-->" + valor);
                mapa.put(key, valor); //Almacenamos los valores en el mapa
            }  
            
        }catch(Exception e){
            System.out.print("Error " + e);
            return null;
        }
        return mapa;
    }
    
    //Metodo para obtener varios registros de una tabla
    public List<HashMap<String, String>> getAll(String table, String  delimiter, String index){
        //Todo lo almacenamos en una lista de mapas
    	List<HashMap<String,String>> Lista = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> mapa = new HashMap<String, String>();
        
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT * FROM " + table + " WHERE "+ delimiter + " = " + index; //Creamos el query
            System.out.println("GetAll: " + comando);
            ResultSet rs = query.executeQuery(comando); //Ejecutamos el query
            
            if( !rs.next() ){
                System.out.println("VACIO");
                return null;
            }
            
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.first();
            String valor, key;
 
            int cant = rsmd.getColumnCount(); //Obtenemos el numero de columnas
            do{
	            mapa = new HashMap<String, String>(); //Creamos un nuevo mapa
            	for(int i = 1; i <= cant; i++){ //Por cada una de las columnas
	                key = rsmd.getColumnName(i); //Almacenamos la columna
	                valor = rs.getString(key); //Almacenamos el valor
	                //System.out.println(key + "-->" + valor);
	                mapa.put(key, valor); //Guardamos los valores en el mapa
	            }
            	Lista.add(mapa); //Almacenamos el registro completo (el mapa) en la lista
            }while(rs.next()); //Mientras haya registros 
            return Lista;
        }catch(Exception e){
            System.out.print("Error " + e);
            return null;
        }
        
    }
    
    public List<HashMap<String, String>> getAll(String table){
        //Todo lo almacenamos en una lista de mapas
    	List<HashMap<String,String>> Lista = new ArrayList<HashMap<String, String>>();
    	HashMap<String, String> mapa = new HashMap<String, String>();
        
        try{
            Statement query = (Statement) connection.createStatement();
            String comando = "SELECT * FROM " + table; //Creamos el query
            System.out.println("GetAll complete: " + comando);
            ResultSet rs = query.executeQuery(comando); //Ejecutamos el query
            
            if( !rs.next() ){
                System.out.println("VACIO");
                return null;
            }
            
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.first();
            String valor, key;
 
            int cant = rsmd.getColumnCount(); //Obtenemos el numero de columnas
            do{
	            mapa = new HashMap<String, String>(); //Creamos un nuevo mapa
            	for(int i = 1; i <= cant; i++){ //Por cada una de las columnas
	                key = rsmd.getColumnName(i); //Almacenamos la columna
	                valor = rs.getString(key); //Almacenamos el valor
	                //System.out.println(key + "-->" + valor);
	                mapa.put(key, valor); //Guardamos los valores en el mapa
	            }
            	Lista.add(mapa); //Almacenamos el registro completo (el mapa) en la lista
            }while(rs.next()); //Mientras haya registros 
            return Lista;
        }catch(Exception e){
            System.out.print("Error " + e);
            return null;
        }
        
    }
   public void libroNODisponible(String claveLibro) {
	   try
	    {
	      	     
	      // create the java mysql update preparedstatement
	      String query = "update libro set disponible = ? where clave_libro = ?";
	      PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString(1, "NO");
	      preparedStmt.setString(2, claveLibro);
	 
	      // execute the java preparedstatement
	      preparedStmt.executeUpdate();
	       
	      connection.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	  }
     
   public void libroDisponible(String claveLibro) {
	   try
	    {
	      	     
	      // create the java mysql update preparedstatement
	      String query = "update libro set disponible = ? where clave_libro = ?";
	      PreparedStatement preparedStmt = connection.prepareStatement(query);
	      preparedStmt.setString(1, "SI");
	      preparedStmt.setString(2, claveLibro);
	 
	      // execute the java preparedstatement
	      preparedStmt.executeUpdate();
	       
	      connection.close();
	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	    }
	  }
    
    
    public Connection getConnection(){
        return connection;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public void abort(Executor executor) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void clearWarnings() throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void close() throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void commit() throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public Blob createBlob() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public Clob createClob() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public NClob createNClob() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public java.sql.Statement createStatement() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType,
                    int resultSetConcurrency) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public java.sql.Statement createStatement(int resultSetType,
                    int resultSetConcurrency, int resultSetHoldability)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public String getCatalog() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public Properties getClientInfo() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public int getHoldability() throws SQLException {
            // TODO Auto-generated method stub
            return 0;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
            // TODO Auto-generated method stub
            return 0;
    }

    @Override
    public String getSchema() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
            // TODO Auto-generated method stub
            return 0;
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public boolean isClosed() throws SQLException {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public boolean isReadOnly() throws SQLException {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
            // TODO Auto-generated method stub
            return false;
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
                    int resultSetConcurrency) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
                    int resultSetConcurrency, int resultSetHoldability)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
                    int resultSetConcurrency) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
                    int resultSetConcurrency, int resultSetHoldability)
                    throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void rollback() throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setClientInfo(Properties properties)
                    throws SQLClientInfoException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setClientInfo(String name, String value)
                    throws SQLClientInfoException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds)
                    throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
            // TODO Auto-generated method stub
            return null;
    }

    @Override
    public void setSchema(String schema) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
            // TODO Auto-generated method stub

    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            // TODO Auto-generated method stub

    }

}

