import java.util.*;
import java.lang.Math;
import java.io.*; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Operaciones
{

    private int[][] E; //matriz de encriptacion
    private int[][] E_1; //matriz de Desencriptacion
    //constructor de la clase
    public Operaciones()
    {
        // establecimiento de matriz para encriptar y desencriptar preestablecida
        int[] numeros = {1,12,19,20,21,4,6,7,8,5,9,0,1,3,12,25};
        E = new int[4][4];
        for (int i=0; i< 4; i++)
        {
            for (int j =0; j<4; j++)
            {
                E[i][j] = numeros[i+j];
            }
        }
        E_1 = matrizInversa(E);
    }
    /**
     * 
     * @param opcion: se utiliza para saber si se va a encriptar con la matriz ingresada o si se va a desencriptar. Es un entero que puede ser 0(encriptar) o 1 (desencriptar)
     * @param matriz: los valores de esta matriz seran ingresados a la matriz de encriptacion/desencriptacion
     */
    public void llenarMatriz(int opcion, int[][] matriz)
    {
        if(opcion == 0)
        {
            E = new int[matriz.length][matriz.length];
            E = matriz;
            E_1 = matrizInversa(E);
        }
        else
        {
            E_1 = new int[matriz.length][matriz.length];
            E_1 = matriz;
            E = matrizInversa(E_1);
        }
    }

    public String encriptar(String texto)
    {
        int tamanoMatriz = E.length;
        int x = tamanoMatriz;
        if((texto.length()%x) !=0)
        {
            for(int i =0; i< (x-(texto.length()%x)); i++)
            {
                texto += " "; //se le concatena espacios vacios
            }
        }
        System.out.println(texto);
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

    String desencriptar(String ciphertext)
    {
        int x = E_1.length; //tamano de la matriz
        if((ciphertext.length()%x) !=0)
        {
            for(int i =0; i< (x-(ciphertext.length()%x)); i++)
            {
                ciphertext += " "; //se le concatena espacios vacios
            }
        }
        int y =ciphertext.length()/x;
        //traslacion de texto a una matriz
        int[][] matrizMensajeC = new int[x][y];
        for(int i=0; i< x;i++)
        {
            for(int j=0; j<y; j++)
            {
                char let = ciphertext.charAt(i + x*j);
                int c = (int)let;
                if (c == 95)
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

    /**
     * Metodo para hallar inverso multiplicativo
     * obtenido de https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
     * @param a numero
     * @param m modulo
     */
     public static int modInverse(int a, int m)
    {
        int m0 = m;
        int y = 0, x = 1;

        if (m == 1)
            return 0;

        while (a > 1)
        {
            // q es el cociente 
            int q = a / m;

            int t = m;

            // m es el residuo
            // igual que para el algoritmo de Euclides
            m = a % m;
            a = t;
            t = y;

            // Update x, y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }

    /**
    *   Metodo para imprimir una matriz en pantalla
    * @param matriz
    */

    public String imprimirMat(int opc){
        int tamano = this.E_1.length;
        int[][] matriz = new int[tamano][tamano];
        if (opc == 0)
            matriz = E;
        else 
            matriz = E_1;
        String show = "[";
        for (int i=0; i< matriz.length; i++)
        {
            for (int j =0; j<matriz[0].length; j++)
            {
                show += matriz[i][j];
                if (j != (matriz.length-1))
                    show += " , ";
            }
            if (i !=matriz.length-1)
                show += "\n ";
        }
        show += "]\n";
        return show;
    }

    // -------------------------     METODOS PARA OBTENER LA INVERSA DE UNA MATRIZ DE CUALQUIER TAMAÑO -------------------------
    /**
     * 
     * @param mensaje: matriz del mensaje a encriptad
     * @param encr: matriz de encriptacion
     * @param resultado: matriz del mensaje encriptado
     * @return: matriz resultado
     */
    public int[][] multiplicarMatrices(int[][] mensaje, int[][] encr, int[][] resultado)
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

    /**
    *Metodo para calcular el determinante de una matriz
    * @param matriz
    * @param i
    */

    public int Determinante(int i, int [][]matriz){
      int deter=0;
      if (matriz.length==2)
      {
        deter=matriz[0][0]*matriz[1][1]-matriz[0][1]*matriz[1][0];
      }
      else
      {
          for (int j = 0; j < matriz.length; j++)
          {
              int[][]temp = this.SubMatriz(i,j,matriz);
              deter=(int) deter+(int) Math.pow(-1, i+j)*matriz[i][j]*this.Determinante(0, temp);
          }
      }
      deter=Math.floorMod((int)deter, 29);
      return deter;
    }

    /**
    *Metodo para calcular una submatriz que ayudara a calcular el determinante de la matriz principal
    * @param matriz
    * @param j
    * @param i
    */

    private int[][]SubMatriz(int i,int j,int [][]matriz)
    {
        int[][]temp=new int[matriz.length-1][matriz.length-1];
        int count1=0;
        int count2=0;

        for (int k = 0; k < matriz.length; k++)
        {
            if (k!=i)
            {
                count2=0;
                for (int l = 0; l < matriz.length; l++)
                {
                    if (l!=j)
                    {
                        temp[count1][count2]=matriz[k][l];

                        count2++;
                    }
                }
                count1++;
            }
        }
       return temp;

    }

     /**
     * Metodo para calcular la adjunta de una matriz
     * @param matriz
     * @return tempAdjunta
     */

    public int [][]AdjuntaMatriz(int [][]matriz)
    {
        int[][]tempAdjunta=new int[matriz.length][matriz.length];
        for (int i = 0; i < tempAdjunta.length; i++)
        {
            for (int j = 0; j < tempAdjunta.length; j++)
            {
                int[][]temp  = this.SubMatriz(i, j, matriz) ;
                int elementoAdjunto=(int) Math.pow(-1, i+j)*this.Determinante(0, temp);
                tempAdjunta[i][j]=elementoAdjunto;
            }
        }
         return tempAdjunta;
    }

    /**
    * Metodo para calcular la transpuesta de una matriz
    * @param matriz
    * @return tempTranspuesta
    */
    public int [][]TranspuestaMatriz(int [][]matriz)
    {
        int[][]tempTranspuesta=new int[matriz.length][matriz.length];
        for (int i = 0; i < tempTranspuesta.length; i++)
        {
            for (int j = 0; j < tempTranspuesta.length; j++)
            {
                tempTranspuesta[i][j]=matriz[j][i];
            }
        }
        return tempTranspuesta;
    }
    /**
    * Metodo para calcular la inversa de una matriz
    * @param matriz
    * @return matInver
    */
    public int[][] matrizInversa(int[][] matriz){
        int determinate= Determinante(0, matriz); //calculando la determinate
        int[][]MatAdjunta= AdjuntaMatriz(matriz);
        int[][]MatTrans= TranspuestaMatriz(MatAdjunta);
        int[][] matinver = new int[matriz.length][matriz.length];
            int inversodet = modInverse((int)determinate,29);
            if (determinate==0)
            {
                System.out.println("No existe inversa de la matriz");
            }
            else
            {
              if(matriz.length==2){
                matinver[0][0]=(matriz[1][1]*inversodet)%29;
                matinver[1][1]=(matriz[0][0]*inversodet)%29;
                matinver[0][1]=Math.floorMod((-1*matriz[0][1])*inversodet,29);
                matinver[1][0]=Math.floorMod((-1*matriz[1][0])*inversodet,29);
              }
              else{
                for (int i = 0; i < MatTrans.length; i++)
                {
                    for (int j = 0; j < MatTrans.length; j++)
                    {
                        matinver[i][j] = Math.floorMod((int)(MatTrans[i][j]*inversodet),29);
                    }
                }
              }
    }
    return matinver;
}

/*
    public String getCharForNumber(int i) {
        String ans="";
        if(i>0&&i<27){
            ans=String.valueOf((char)(i + 'A' - 1));
        }
        else if(i==0){
            ans=" ";
        }
        else if(i==27){
            ans="!";
        }
        else if(i==28){
            ans="?";
        }
        return ans;
    }*/

}
