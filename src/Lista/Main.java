package Lista;


public class Main {
    
    public static void main(String[] args) {    
       Parser parser = new Parser();
       
       int r = parser.evaluar("[1+5-1,0,1,5,[1,2-5+6],18-20,2]");
       //int r = parser.evaluar(",5+9-5+1-9,4+4-4]");
       
        if (parser.getError() != 0)
           System.out.println("Error de sintaxis: " + parser.getErrorMessage());
        else
            System.out.println("El valor de la Lista es " + r);    
 
    }
    
}
