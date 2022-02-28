package com.unbosque.edu.co.files;

import java.io.*;
import java.util.ArrayList;

public class LectorArchivos {

    public static void leerPersonas() {
        ArrayList<String[]> lineas = new ArrayList<>(); // add lines into list
        int countReg = 0;
        boolean header = true;
        try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Persona.dat")))) {
            Persona persona;
            while ((persona = (Persona) reader.readObject()) != null) {
                // ignore header line
                if (header) {
                    header = false;
                    continue;
                }
                // add data in columns
                String[] columnas = {
                        persona.getDni(),
                        persona.getNombres(),
                        persona.getApellidos(),
                        String.valueOf(persona.getEdad()),
                        String.valueOf(persona.getSalario())
                };
                lineas.add(columnas);
                countReg++; // count lines/registries
            }
        } catch (EOFException e) {
            System.out.println("Data deserialized \n");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("File error.");
        }
        System.out.println("Numero de registros: " + countReg);
        System.out.println("Suma de salarios: " + sumarSalarios(lineas));
        System.out.println("Numero de personas menores de 50: " + personasMenoresDe50(lineas));
        System.out.println("Promedio de edad de personas entre 45 y 70: " + promedioEdad(lineas));
    }

    public static int sumarSalarios(ArrayList<String[]> lineas) {
        int suma = 0;
        for (String[] columna: lineas) suma += Integer.parseInt(columna[4]);
        return suma;
    }

    public static int personasMenoresDe50(ArrayList<String[]> lineas) {
        int count = 0;
        for (String[] columna: lineas) if (Integer.parseInt(columna[3]) < 50) count++;
        return count;
    }

    public static double promedioEdad(ArrayList<String[]> lineas) {
        double suma = 0.0;
        int count = 0;
        for (String[] columna: lineas)
            if (Integer.parseInt(columna[3]) > 45 && Integer.parseInt(columna[3]) < 70) {
                suma += Integer.parseInt(columna[3]);
                count++;
            }
        return suma / count;
    }

    public static void main(String[] args) {
        leerPersonas();
    }
}
