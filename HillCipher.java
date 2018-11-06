import java.util.*;
import java.lang.Math;
import java.io.*; 
import java.io.PrintWriter;
import java.io.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HillCipher {
    static String textoEncriptado = "";
    static String textoDesencriptado = "";
    static File file = new File("");

    public static void main(String[] args) throws FileNotFoundException, IOException
    {
         
        Operaciones oper = new Operaciones();
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el nombre del archivo .txt (debe estar en la misma carpeta que este programa)");
        String nombreArchivo = sc.nextLine();
        //se lee el archivo ingresado
        String textoOriginal = "";
        try{
            file = new File(nombreArchivo + ".txt");
            sc = new Scanner(file); 
            while (sc.hasNextLine()) 
                textoOriginal += sc.nextLine(); 
            
            sc.close();
        }
        catch (FileNotFoundException ex) 
        {
            System.out.println("El archivo no existe.");
        }
        textoOriginal = textoOriginal.toUpperCase(); // pasarlo todo a mayusculas para que no haya ningun problema
        textoEncriptado = textoOriginal;
        textoDesencriptado = textoOriginal;
        System.out.println("Texto original: \n"+ textoOriginal);
        sc = new Scanner(System.in);
        System.out.println("¿Que desea hacer? \n\t1. Encriptar \n\t2. Desencriptar");
        int opcion = sc.nextInt();
        sc.nextLine();
        
        switch(opcion)
        {
            case 1:
                System.out.println("¿Cómo quiere encriptar el texto?\n\t1. Con una matriz preestablecida.\n\t2. Con una matriz ingresada personalmente.");
                int opEnc = sc.nextInt();
                switch(opEnc)
                {
                    case 1:
                        System.out.println("\nMatriz de encriptacion utilizada: ");
                        System.out.println(oper.imprimirMat(0));
                        textoEncriptado = oper.encriptar(textoOriginal);
                        System.out.println("El texto desencriptado es: \n\t->"+textoEncriptado);
                        break;
                    case 2:     
                        System.out.println("Ingrese el tamaño de la matriz de encriptacion (numero maximo: 4): ");
                        int tamano = sc.nextInt();
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
                        System.out.println("\nMatriz de encriptacion utilizada: ");
                        oper.llenarMatriz(0, mat);
                        System.out.println(oper.imprimirMat(0));
                        textoEncriptado= oper.encriptar(textoOriginal);
                        System.out.println("El texto desencriptado es: \n\t->"+textoEncriptado);
                        break;
                }
                break;
                
            case 2:  
                System.out.println("¿Cómo quiere desencriptar el texto?\n\t1. Con una matriz preestablecida.\n\t2. Con una matriz ingresada personalmente.");
                int opDesenc = sc.nextInt();
                switch(opDesenc)
                {
                    case 1:
                        System.out.println("\nMatriz de desencriptacion utilizada: ");
                        System.out.println(oper.imprimirMat(1));
                        textoDesencriptado = oper.desencriptar(textoOriginal);
                        System.out.println("El texto desencriptado es: \n\t->"+textoDesencriptado);
                        break;
                    case 2:  
                        System.out.println("Ingrese el tamaño de la matriz de encriptacion (numero maximo: 4): ");
                        int size = sc.nextInt();
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
                        System.out.println("\nMatriz de desencriptacion utilizada: ");
                        System.out.println(oper.imprimirMat(1));
                        textoDesencriptado = oper.desencriptar(textoOriginal);
                        System.out.println("El texto desencriptado es: \n\t->"+textoDesencriptado);
                        break;
                }
                break;
        }
        try{
            PrintWriter writerE = new PrintWriter("ciphertext.txt", "UTF-8");
            writerE.println(textoEncriptado);
            writerE.close();
            PrintWriter writerD = new PrintWriter("plaintext.txt", "UTF-8");
            writerD.println(textoDesencriptado);
            writerD.close();
        }
        catch (IOException ex) 
        {
            System.out.println("El archivo no existe.");
        }



    }

}
