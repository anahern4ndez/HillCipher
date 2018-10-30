public class Operaciones{

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
            // q is quotient
            int q = a / m;

            int t = m;

            // m is remainder now, process
            // same as Euclid's algo
            m = a % m;
            a = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }

    /**
    *Metodo para multiplicar matrices
    * @param matriz1
    * @param matriz2
    */

    public int[][] multiplicarMat(int[][]matriz1, int[][]matriz2){
        int [][]matrizresultante = new int[matriz1.length][matriz2[0].length];
        for (int i = 0; i < matriz1.length; i++)
            {
                for (int j = 0; j < matriz2[0].length; j++)
                {
                    int elemento=0;

                    for (int k = 0; k < matriz1[0].length; k++)
                    {
                         elemento=elemento+ Math.round(matriz1[i][k]*(matriz2[k][j]));
                        matrizresultante[i][j]=elemento%29; //aplicar mod29

                    }

                    //System.out.print(elemento+"\t");

                }

                //System.out.println("");

            }
        return matrizresultante;
    }

    /**
    *Metodo para imprimir una matriz en pantalla
    * @param matriz
    */

    public void imprimirMat(int[][]matriz){
        for (int i = 0; i < matriz.length; i++)
            {
                for (int j = 0; j < matriz[i].length; j++)
                {

                    System.out.print(matriz[i][j]+"\t");

                }

                System.out.println("");

            }


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

     private int[][]SubMatriz(int i,int j,int [][]matriz){
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

    public int [][]AdjuntaMatriz(int [][]matriz){

        int[][]tempAdjunta=new int[matriz.length][matriz.length];

        for (int i = 0; i < tempAdjunta.length; i++){

            for (int j = 0; j < tempAdjunta.length; j++){

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


    public int [][]TranspuestaMatriz(int [][]matriz){

        int[][]tempTranspuesta=new int[matriz.length][matriz.length];

        for (int i = 0; i < tempTranspuesta.length; i++){

            for (int j = 0; j < tempTranspuesta.length; j++){

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
            for (int i = 0; i < MatTrans.length; i++)
            {
                for (int j = 0; j < MatTrans.length; j++)
                {
                    matinver[i][j] = Math.floorMod((int)(MatTrans[i][j]*inversodet),29);
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
