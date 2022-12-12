package dao;

import java.awt.desktop.QuitStrategy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import models.Ejemplar;
import models.Libro;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author FranciscoRomeroGuill
 */
public class BibliotecaDAO {

    private static SessionFactory sessionFactory;

    static {
        try {

            sessionFactory = new Configuration().configure().buildSessionFactory();
            try ( Session s = sessionFactory.openSession()) {
                System.out.println("Conexión realizada con exito");
            }

        } catch (Exception ex) {
            System.out.println("Error iniciando Hibernate");
            System.out.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void saveLibro(Libro e) {

        /* Guarda un libro con todos sus ejemplares en la base de datos */
        try ( var s = sessionFactory.openSession()) {
            Transaction t = s.beginTransaction();
            s.save(e);
            t.commit();
        }

    }

    public List<Libro> findByEstado(String estado) {

        List<Libro> salida = new ArrayList<Libro>();
        List<Ejemplar> ejemplares = new ArrayList();
        /* 
         Devuelve el conjunto de libros que tenga el estado indicado      
         */
        try ( var s = sessionFactory.openSession()) {
            var q = s.createQuery("FROM Ejemplar\n" +
                                    "WHERE estado =: param");
            q.setParameter("param", estado);
            
            ejemplares = q.list();
            
        }
        for (int i = 0; i < ejemplares.size(); i++) {
            salida.add(ejemplares.get(i).getLibro());
        }
        
        return salida;

    }

    public void printInfo() {

        /* 
          Muestra por consola todos los libros de la biblioteca y el número
          de ejemplares disponibles de cada uno.
          
          Debe imprimirlo de esta manera:
        
          Biblioteca
          ----------
          Como aprender java en 24h. (3)
          Como ser buena persona (1)
          Aprobando exámenes fácilmente (5)
          ...
        
         */
        List<Libro> libros = new ArrayList();
        System.out.println("Biblioteca");
        System.out.println("----------");

        try ( var s = sessionFactory.openSession()) {
            var q = s.createQuery("FROM Libro l");

            libros = q.list();
            int cantidadEjemplares = 0;
            for (int i = 0; i < libros.size(); i++) {
                cantidadEjemplares = libros.get(i).getEjemplares().size();
                System.out.println(libros.get(i).getTitulo()+ " (" + cantidadEjemplares+")");
                
            }
            System.out.println("...");
        }
    }
}
