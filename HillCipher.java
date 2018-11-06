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
                        System.out.println("El texto encriptado es: \n\t->"+textoEncriptado);
                        System.out.println("\nSu texto encriptado se ha guardado en el archivo: ciphertext.txt");
                        break;
                    case 2:
                        int tamano;
                        do{
                          System.out.println("Ingrese el tamaño de la matriz de encriptacion (minimo: 2, maximo: 4): ");
                           tamano = sc.nextInt();
                        } while(tamano<2 || tamano>4);
                        int[][] mat = new int[tamano][tamano];
                        //ingreso de matriz de encriptacion
                        System.out.println("Recuerde ingresar unicamente numeros en mod 29. De lo contrario, se le pedira reingresar el numero.\n");
                        for(int i=0; i<tamano;i++){
                            for(int j=0; j<tamano; j++){
                              int entrada;
                              do{
                                System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                                entrada = sc.nextInt();
                              }while(entrada<0 || entrada >29);
                                    mat[i][j] = entrada;
                            }
                        }
                        if(oper.Determinante(0,mat)!=0){
                        System.out.println("\nMatriz de encriptacion utilizada: ");
                        oper.llenarMatriz(0, mat);
                        System.out.println(oper.imprimirMat(0));
                        textoEncriptado= oper.encriptar(textoOriginal);
                        System.out.println("El texto desencriptado es: \n\t->"+textoEncriptado);
                        System.out.println("\nSu texto encriptado se ha guardado en el archivo: ciphertext.txt");
                        break;
                      }else{System.out.println("No se puede calcular una matriz inversa. No es posible encriptar si se desea poder desencriptar mas adelante."); break;}
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
                        System.out.println("\nSu texto desencriptado se ha guardado en el archivo: plaintext.txt");
                        break;
                    case 2:
                        int size;
                        do{
                        System.out.println("Ingrese el tamaño de la matriz de encriptacion (minimo: 2, maximo: 4): ");
                        size = sc.nextInt();} while(size<2 || size>4);
                        System.out.println("A continuación, ingrese la matriz usada para encriptar: ");
                        int[][] mat_1 = new int[size][size];
                        //ingreso de matriz de encriptacion
                        System.out.println("Recuerde ingresar unicamente numeros en mod 29. De lo contrario, se le pedira reingresar el numero.\n");
                        for(int i=0; i<size;i++){
                            for(int j=0; j<size; j++){
                              int enter;
                              do{
                                System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                                enter = sc.nextInt();
                              }while(enter<0 || enter >29);
                                    mat_1[i][j] = enter;
                            }
                        }

                        if(oper.Determinante(0,mat_1)!=0){
                        oper.llenarMatriz(1, mat_1);
                        System.out.println("\nMatriz de encriptacion utilizada: ");
                        System.out.println(oper.imprimirMat(1));
                        textoDesencriptado = oper.desencriptar(textoOriginal);
                        System.out.println("El texto desencriptado es: \n\t->"+textoDesencriptado);
                        System.out.println("\nSu texto desencriptado se ha guardado en el archivo: plaintext.txt");
                        break;
                      }else{System.out.println("No se puede calcular una matriz inversa. No es posible desencriptar este mensaje."); break;}
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
      catch (FileNotFoundException ex)
      {
          System.out.println("El archivo no existe.");
      }


    }

}
