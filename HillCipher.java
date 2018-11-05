import java.util.*;
import java.lang.Math;
import java.io.*; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HillCipher {
    static int[][] inverse;
    static int[][] temp;

    public static void main(String[] args) throws FileNotFoundException
    {
         
        Operaciones oper = new Operaciones();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo .txt (debe estar en la misma carpeta que este programa)");
        String nombreArchivo = sc.nextLine();
        //se lee el archivo ingresado
        String textoOriginal = "";
        try{
            File file = new File(nombreArchivo + ".txt");
            sc = new Scanner(file); 
            while (sc.hasNextLine()) 
                textoOriginal += sc.nextLine(); 
            
            sc.close();
        }
        catch (FileNotFoundException ex) 
        {
            System.out.println("El archivo no existe.");
        }
        textoOrigonal = textoOriginal.toUpperCase(); // pasarlo todo a mayusculas para que no haya ningun problema
        System.out.println("Texto original: \n"+ textoOriginal);
        sc = new Scanner(System.in);
        System.out.println("¿Que desea hacer? \n\t1. Encriptar \n\t2. Desencriptar");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        switch(opcion)
        {
            case 1:
                System.out.println("¿Cómo quiere encriptar el texto?\n\t1. Con una matriz generada aleatoriamente.\n\t2. Con una matriz ingresada personalmente.");
                int opEnc = sc.nextInt();
                switch(opEnc)
                {
                    case 1:
                        oper.generacionMatriz(0);
                        System.out.println("El texto encriptado es: \n\t->"+oper.encriptar(textoOriginal));
                        break;
                    case 2:     
                        System.out.println("Ingrese el tamaño de la matriz de encriptacion: \n\t1. 2 filas x 2 columnas. \n\t2. 3 filas x 3 columnas.");
                        int tamano = sc.nextInt();
                        tamano +=1;
                        int[][] mat = new int[tamano][tamano];
                        //ingreso de matriz de encriptacion
                        for(int i=0; i<tamano;i++){
                            for(int j=0; j<tamano; j++){
                                System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                                int entrada = sc.nextInt();
                                if (entrada > 29 || entrada <0)
                                    System.out.println("Ingrese un numero valido (modulo 29)");
                                else
                                    mat[i][j] = entrada;
                            }
                        }
                        oper.llenarMatriz(0, mat);
                        System.out.println("El texto encriptado es: \n\t->"+oper.encriptar(textoOriginal));
                        break;
                }
                break;
                
            case 2:
                System.out.println("¿Cómo quiere desencriptar el texto?\n\t1. Con una matriz generada aleatoriamente.\n\t2. Con una matriz ingresada personalmente.");
                int opDesenc = sc.nextInt();
                switch(opDesenc)
                {
                    case 1:
                        oper.generacionMatriz(1);
                        System.out.println("El texto desencriptado es: \n\t->"+oper.desencriptar(textoOriginal));
                        break;

                    case 2:     
                        System.out.println("Ingrese el tamaño de la matriz que se uso para encriptar: \n\t1. 2 filas x 2 columnas. \n\t2. 3 filas x 3 columnas.");
                        int size = sc.nextInt();
                        size +=1;
                        System.out.println("A continuación, ingrese la matriz usada para encriptar: ");
                        int[][] mat_1 = new int[size][size];
                        //ingreso de matriz de encriptacion
                        for(int i=0; i<size;i++){
                            for(int j=0; j<size; j++){
                                System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                                int enter = sc.nextInt();
                                if (enter > 29 || enter <0)
                                    System.out.println("Ingrese un numero valido (modulo 29)");
                                else
                                    mat_1[i][j] = enter;
                            }
                        }
                        oper.llenarMatriz(1, mat_1);
                        System.out.println("El texto encriptado es: \n\t->"+oper.desencriptar(textoOriginal));
                        break;
                }
                break;
        }
    }

}
