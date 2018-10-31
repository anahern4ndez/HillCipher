import java.util.*;
import java.lang.Math;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HillCipher {
    static int[][] E; //matriz de encriptacion
    static int[][] E_1; //matriz de Desencriptacion
    static int[][] inverse;
    static int[][] temp;
    static Operaciones oper = new Operaciones();

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Que desea hacer? \n\t1. Encriptar al ingresar E. \n\t2. Desencriptar al ingresar E^-1. \n\t3. Desencriptar a partir de una matriz C y una matriz M. \n\t4. Desencriptar a partir de la matriz original E.");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch(opcion)
        {
            case 1:
                System.out.println("Ingrese el texto que va a encriptar: ");
                String textoAEnc = (sc.nextLine()).toUpperCase();
                System.out.println("Ingrese el tamaño de la matriz de encriptacion: \n\t1. 2 filas x 2 columnas. \n\t2. 3 filas x 3 columnas.");
                int tamano = sc.nextInt();
                tamano +=1;
                E = new int[tamano][tamano];
                //ingreso de matriz de encriptacion
                for(int i=0; i<tamano;i++){
                    for(int j=0; j<tamano; j++){
                        System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                        int entrada = sc.nextInt();
                        if (entrada > 29 || entrada <0)
                            System.out.println("Ingrese un numero valido (modulo 29)");
                        else
                            E[i][j] = entrada;
                    }
                }
                System.out.println("El texto encriptado es: \n\t->"+encriptar(E, textoAEnc, tamano));
                break;
            case 2:
                System.out.println("Ingrese el texto que va a desencriptar: ");
                String textoADesenc = (sc.nextLine()).toUpperCase();
                System.out.println("Ingrese el tamaño de la matriz de desencriptacion: \n\t1. 2 filas x 2 columnas. \n\t2. 3 filas x 3 columnas.");
                int tamanio = sc.nextInt();
                tamanio +=1;
                E_1 = new int[tamanio][tamanio];
                //ingreso de matriz de encriptacion
                for(int i=0; i<tamanio;i++){
                    for(int j=0; j<tamanio; j++){
                        System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                        int entrada = sc.nextInt();
                        if (entrada > 29 || entrada <0)
                            System.out.println("Ingrese un numero valido (modulo 29)");
                        else
                            E_1[i][j] = entrada;
                    }
                }
                System.out.println("El texto desencriptado es: \n\t->"+desencriptarConInversa(E_1, textoADesenc, tamanio));
                break;
            case 3:
                break;
            case 4:
                System.out.println("Ingrese el texto que va a desencriptar: ");
                textoADesenc = (sc.nextLine()).toUpperCase();
                System.out.println("Ingrese el tamaño de la matriz de encriptacion: \n\t1. 2 filas x 2 columnas. \n\t2. 3 filas x 3 columnas.");
                tamano = sc.nextInt();
                tamano +=1;
                E = new int[tamano][tamano];
                //ingreso de matriz de encriptacion
                for(int i=0; i<tamano;i++){
                    for(int j=0; j<tamano; j++){
                        System.out.println("Introduzca el elemento [" + i + "," + j + "]");
                        int entrada = sc.nextInt();
                        if (entrada > 29 || entrada <0)
                            System.out.println("Ingrese un numero valido (modulo 29)");
                        else
                            E[i][j] = entrada;
                    }
                }

                //conversion de matriz original a inversa
                E_1=oper.matrizInversa(E);
                System.out.println("El texto desencriptado es: \n\t->"+desencriptarConInversa(E_1, textoADesenc, tamano));
                break;
        }
    }

    static String encriptar(int[][] matrizEnc, String texto, int tamanoMatriz)
    {
        int x = tamanoMatriz;
        if((texto.length()%x) !=0)
        {
            for(int i =0; i< (x-(texto.length()%x)); i++)
            {
                texto += " "; //se le concatena espacios vacios
            }
        }
        int y =texto.length()/tamanoMatriz;
        //traslacion de texto a una matriz
        int[][] matrizMensaje = new int[x][y];
        int letra = 0;
        for(int i=0; i< x;i++)
        {
            for(int j=0; j<y; j++)
            {
                char let = texto.charAt(i + x*j);
                int c = (int)let;
                if (c == 32)
                {
                    c = 0; //si es un espacio se le asigna el valor 0
                }
                if (texto.charAt(i + x*j) == 33)
                {
                    c = 27;
                }
                if (texto.charAt(i + x*j) == 63)
                {
                    c = 28;
                }
                if (c != 0 && c!=27 && c != 28)
                {
                    c -= 64; //de lo contrario, se le resta 64 a su valor ascii para que quede mod29
                }
                matrizMensaje[i][j] = c;
            }
        }
        int[][] matrizCodif = new int[x][y]; //matriz de mensaje codificado
        matrizCodif = multiplicarMatrices(matrizMensaje, E, matrizCodif);
        //regreso de ints a letras
        String textoFinal ="";
        for(int i=0; i<matrizCodif.length;i++){
            for(int j=0; j<matrizCodif[0].length; j++)
            {
                int d = matrizCodif[i][j];
                char lettr =0;
                if (d == 0)
                    lettr = 32; //si es un espacio se le asigna el valor 0
                if (d == 27)
                    lettr = 33;
                if (d == 28)
                    lettr = 63;
                else if (d != 0 && d!=28 && d != 27)
                    lettr = (char)(d + 64); //de lo contrario, se le resta 64 a su valor ascii para que quede mod29
                matrizCodif[i][j] = (int)lettr;
            }
        }
        for(int i=0; i<matrizCodif[0].length;i++)
        {
            for(int j=0; j<matrizCodif.length; j++)
            {
                String character = Character.toString((char)matrizCodif[j][i]);
                if(character.equals(" "))
                    textoFinal += "_";
                else
                    textoFinal += character;
            }
        }
        return textoFinal;

    }


    static String desencriptarConInversa(int[][] matrizDesenc, String ciphertext, int tamanoMatriz)
    {
        int x = tamanoMatriz;
        if((ciphertext.length()%x) !=0)
        {
            for(int i =0; i< (x-(ciphertext.length()%x)); i++)
            {
                ciphertext += " "; //se le concatena espacios vacios
            }
        }
        int y =ciphertext.length()/tamanoMatriz;
        //traslacion de texto a una matriz
        int[][] matrizMensajeC = new int[x][y];
        for(int i=0; i< x;i++)
        {
            for(int j=0; j<y; j++)
            {
                char let = ciphertext.charAt(i + x*j);
                int c = (int)let;
                if (c == 32)
                {
                    c = 0; //si es un espacio se le asigna el valor 0
                }
                if (ciphertext.charAt(i + x*j) == 33)
                {
                    c = 27;
                }
                if (ciphertext.charAt(i + x*j) == 63)
                {
                    c = 28;
                }
                if (c != 0 && c!=27 && c != 28)
                {
                    c -= 64; //de lo contrario, se le resta 64 a su valor ascii para que quede mod29
                }
                matrizMensajeC[i][j] = c;
            }
        }
        int[][] matrizDesCodif = new int[x][y]; //matriz de mensaje descodificado
        matrizDesCodif = multiplicarMatrices(matrizMensajeC, E_1, matrizDesCodif);
        //regreso de ints a letras
        String textoFinal ="";
        for(int i=0; i<matrizDesCodif.length;i++){
            for(int j=0; j<matrizDesCodif[0].length; j++)
            {
                int d = matrizDesCodif[i][j];
                char lettr =0;
                if (d == 0)
                    lettr = 32; //si es un espacio se le asigna el valor 0
                if (d == 27)
                    lettr = 33;
                if (d == 28)
                    lettr = 63;
                else if (d != 0 && d!=28 && d != 27)
                    lettr = (char)(d + 64); //de lo contrario, se le resta 64 a su valor ascii para que quede mod29
                    matrizDesCodif[i][j] = (int)lettr;
            }
        }
        for(int i=0; i<matrizDesCodif[0].length;i++)
        {
            for(int j=0; j<matrizDesCodif.length; j++)
            {
                String character = Character.toString((char)matrizDesCodif[j][i]);
                if(character.equals("_"))
                    textoFinal += " ";
                else
                    textoFinal += character;
            }
        }
        return textoFinal;

    }


    static int[][] multiplicarMatrices(int[][] mensaje, int[][] encr, int[][] resultado)
    {
        int i, j, k;
        for (i = 0; i < resultado.length; i++)
        {
            for (j = 0; j < resultado[0].length; j++)
            {
                resultado[i][j] = 0;
                for (k = 0; k < encr[0].length; k++)
                {
                    resultado[i][j] += encr[i][k] * mensaje[k][j];
                    resultado[i][j] %= 29;
                }
            }
        }
        return resultado;
    }

    /*static int[][] inversa(int[][] matriz)
    {
        int filas = matriz.length;
        int columnas = matriz[0].length;
        int[][] inversa = new int[filas][columnas];
        if(matriz.length == 2)
        {
            inversa[0][0] = matriz[1][1];
            inversa[1][1] = matriz[0][0];
            inversa[0][1] = -1*matriz[0][1] + 29;
            inversa[1][0] = -1*matriz[1][0] + 29;
        }
        else
        {

        }
    }*/
    static int modInverse(int a)
    {
        a = a % 29;
        for (int x = 1; x < 29; x++)
            if ((a * x) % 29 == 1)
                return x;
        return 1;
    }

    static int determinant(int A[][], int n)
    {

        int N = A.length;
        int D = 0; // Initialize result

        //  Base case : if matrix contains single element
        if (n == 1)
            return A[0][0];

        int[][] temp = new int[N][N]; // To store cofactors

        int sign = 1;  // To store sign multiplier

        // Iterate for each element of first row
        for (int f = 0; f < n; f++)
        {
            // Getting Cofactor of A[0][f]
            getCofactor(A, temp, 0, f, n);
            D += sign * A[0][f] * determinant(temp, n - 1);

            // terms are to be added with alternate sign
            sign = -sign;
        }
        return D;
    }

    static void adjoint(int A[][],int adj[][])
    {
        int N = A.length;
        if (N == 1)
        {
            adj[0][0] = 1;
            return;
        }
        temp = new int[N][N];
        // temp is used to store cofactors of A[][]
        int sign = temp[N][N];

        for (int i=0; i<N; i++)
        {
            for (int j=0; j<N; j++)
            {
                // Get cofactor of A[i][j]
                getCofactor(A, temp, i, j, N);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i+j)%2==0)? 1: -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj[j][i] = (sign)*(determinant(temp, N-1));
            }
        }
    }

    // Function to calculate and store inverse, returns false if
    // matrix is singular
    static boolean inverse(int tamano)
    {
        int[][] A = new int[tamano][tamano];
        A =E_1;
        int N = A.length;
        // Find determinant of A[][]
        int det = determinant(A, N);
        if (det == 0)
        {
            return false;
        }

        // Find adjoint
        int adj[][] = new int[N][N];
        adjoint(A, adj);

        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        for (int i=0; i<N; i++)
        {
            for (int j=0; j<N; j++)
            {
                adj[i][j] %= 29; // pasarlo todo a mod 29
                inverse[i][j] = adj[i][j]*modInverse(det);
            }
        }

        return true;
    }

    static void getCofactor(int A[][], int temp[][], int p, int q, int n)
    {
        int i = 0, j = 0;
        int N = A.length;
        // Looping for each element of the matrix
        for (int row = 0; row < n; row++)
        {
            for (int col = 0; col < n; col++)
            {
                //  Copying into temporary matrix only those element
                //  which are not in given row and column
                if (row != p && col != q)
                {
                    temp[i][j++] = A[row][col];

                    // Row is filled, so increase row index and
                    // reset col index
                    if (j == n - 1)
                    {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

}
