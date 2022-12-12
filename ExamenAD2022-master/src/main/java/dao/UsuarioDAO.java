package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Usuario;

public class UsuarioDAO {
    
    private Connection connection;
    public static final String URL = 
            //MySQL SWAMP. Properties
            "jdbc:mysql://localhost:3306/examenantoniogarcia2022"
            + "?zeroDateTimeBehavior=CONVERT_TO_NULL";
    public static final String USER = "root";
    public static final String PASSWORD = "toorDam2";
    
    /* Completar consultas */
    static final String INSERT_QUERY ="""
            INSERT INTO `examenantoniogarcia2022`.`usuario`
            (`nombre`, `apellidos`, `dni`) 
            VALUES ( ?, ?, ?); """;
            
    static final String LIST_QUERY="SELECT * FROM examenantoniogarcia2022.usuario;";
    static final String GET_BY_DNI="SELECT * FROM usuario WHERE dni = ?;";
    
    
    public void connect(){
        try {
            
            /* completar código de conexión */
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conectando...");
            
        }catch(Exception ex){
            System.out.println("Error al conectar a la base de datos");
            System.out.println("ex");
        }     
    }
    
    public void close(){
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error al cerrar la base de datos");     
        }
    }
    
    public void save(Usuario user){
        
        /**
         * Completar código para guardar un usuario 
         * Este método no debe generar el id del usuario, ya que la base
         * de datos es la que lo genera.
         * Una vez obtenido el id del usuario tras la consulta sql,
         * hay que modificarlo en el objeto que se pasa como parámetro 
         * de forma que pase a tener el id correcto.
         */
        try( var pst = connection.prepareStatement(INSERT_QUERY, RETURN_GENERATED_KEYS)){
                    
            pst.setString(1, user.getNombre() );
            pst.setString(2, user.getApellidos() );
            pst.setString(3, user.getDni() );
            
            if (pst.executeUpdate() > 0){

                var keys = pst.getGeneratedKeys();
                keys.next();
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Usuario> list(){

        var salida = new ArrayList<Usuario>(0);
        
        /* Completar código para devolver un arraylist con todos los usuarios */
        try( var pst = connection.prepareStatement(LIST_QUERY)){
            
            ResultSet resultado = pst.executeQuery();
            
            while(resultado.next()){
                var usuario = new Usuario();
                        
                usuario.setId(resultado.getLong("id") );
                usuario.setNombre(resultado.getString("nombre") );
                usuario.setApellidos(resultado.getString("apellidos") );
                usuario.setDni(resultado.getString("dni") );
                
                salida.add(usuario);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return salida;
    }    
    
    public Usuario getByDNI(String dni){
        
        Usuario salida = new Usuario();
        
        /**
         * Completar código para devolver el usuario que tenga ese dni.
         * Si no existe, se debe devolver null
         */
        try(var pst = connection.prepareStatement(GET_BY_DNI)){
            
            pst.setString(1, dni);
            
            ResultSet resultado = pst.executeQuery();
            
            if(resultado.next()){
                        
                salida.setId(resultado.getLong("id") );
                salida.setNombre(resultado.getString("nombre") );
                salida.setApellidos(resultado.getString("apellidos") );
                salida.setDni(resultado.getString("dni") );
                
                return salida;
            }else{
                return null;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return salida;
    }    
}
