package com.cesur.examenaddicc22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Ejercicio1 {

    /**
     * Enunciado:
     *
     * Completar el método estadísticasDeArchivo de manera que lea el archivo de
     * texto que se le pasa como parámetro, lo analice y muestre por consola el
     * número de caracteres, palabras y líneas total.
     *
     * Modificar solo el código del método.
     *
     */
    static void solucion() {

        estadísticasDeArchivo("pom.xml");
    }

    private static void estadísticasDeArchivo(String archivo) {

        /* añadir código */
        try ( var fr = new BufferedReader(new FileReader(archivo))) {

            String s = "";
            int contCaracteres = 0;
            int contLineas = 0;
            int contPalabras = 0;
            String[] palabras;

            ArrayList<String> totalPalabras = new ArrayList<String>();

            while ((s = fr.readLine()) != null) {
                System.out.println(s);
                palabras = s.split(" ");
                contCaracteres += s.length();
                contLineas++;

                for (String palabra : palabras) {
                    totalPalabras.add(palabra);
                }
                
            }
            System.out.println(totalPalabras);
            contPalabras = totalPalabras.size();
            
            System.out.println("Numero total de caracteres: " + contCaracteres);
            System.out.println("Numero total de lineas: " + contLineas);
            System.out.println("Numero total de palabras: " + contPalabras);

        } catch (IOException ex) {
            Logger.getLogger(Ejercicio1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
