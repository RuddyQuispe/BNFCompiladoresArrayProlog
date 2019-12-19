package Lista;

import java.util.Arrays;
import java.util.List;


public class Parser {
    private Cinta cinta;
    private Analex analex;
    private int Resultado;
    private int ResultadoParcial;
    
    private int error;          //Si error=0, la expresión es correcta
    
    public Parser(){
        cinta  = new Cinta();
        analex = new Analex(cinta);
    }
    
    public int evaluar(String list){
        cinta.init(list);
        analex.init();
        
        error = 0;          //No hay error aún
        return lista();     //Llamar al símbolo inicial.
    }
    
    public int getError(){
        return error;
    }
    
    public String getErrorMessage(){
        switch(this.error){
            case 2: return "Falta Signo { + , - }.";
            case 3: return "Espero un valor Numerico Amigo.";
            case 4: return "Caracter no Valido(no puedo hacer operaciones con ellos.)";
            case 5: return "Espero una Coma Amigo.";
            case 6: return "Espero un corchete cerrado ']' Amigo.";
            case 7: return "No me gustan los numeros negativos al inicio Amigo.";
            case 8: return "Al parecer continua el array pero hace falta un ']'.";
            default: return "Error Desconocido";
        }
    }
    
    private void setError(int e){
        if (error == 0)
            error = e;      //Solo aceptar el 1er error.
    }
    
    private int lista(){  //Lista ->... Símbolo inicial. Devuelve el resultado de la Lista.
       if (this.analex.Preanalisis().getNom() == Token.CA) {
            match(Token.CA);
            int valorInicial = opciones();
            int valorAcumulativo = masOpciones(0);
            match(Token.CC);
            return valorInicial + valorAcumulativo;
        }else{
            setError(4);    //se esperaba CA
            return -1;
        }
    }
    
    private int masOpciones( int valor ){
        if (this.analex.Preanalisis().getNom() == Token.COMA) {
            match(Token.COMA);
            int valorAdelante = opciones();
            int valorMasOpciones = masOpciones(0);
            return valorAdelante + valorMasOpciones;
        }else if (this.analex.Preanalisis().getNom() == Token.CC) {
            return 0;
        }else{
            setError(5);
            return -100;
        }
        
    }
    
    private int opciones(){
        if (this.analex.Preanalisis().getNom()==Token.NUM) {
            match(this.analex.Preanalisis().getNom());
            int valorActual = this.analex.Preanalisis().getAtr();
            int valorAdelante = masOperaciones(0);
            return valorActual + valorAdelante;
        }else if (this.analex.Preanalisis().getNom() == Token.CA) {
            return (-1)*lista();
        }else if (this.analex.Preanalisis().getNom() == Token.COMA) {
            return 0;
        }else if (this.analex.Preanalisis().getNom() == Token.MAS || this.analex.Preanalisis().getNom() == Token.MENOS){
            setError(7);
            return -100;
        }
        setError(4);
        return -100;
    }
    
    private int masOperaciones(int valor){
        List<Integer> sign = Arrays.asList(Token.MAS, Token.MENOS);
        if (sign.contains(this.analex.Preanalisis().getNom())) {
            int signo = signo();
            if (signo > 1) {
                setError(2);
                return -100;    //Error
            }
            if (this.analex.Preanalisis().getNom() == Token.NUM) {
                match(Token.NUM);
                int valorNumerico = this.analex.Preanalisis().getAtr();
                if (signo == 1){
                    valor = valor + valorNumerico;
                }else{
                    valor = valor - valorNumerico;
                }
                valor = valor + masOperaciones( 0 );
            }else{
                setError(3);
            }
            return valor;
        }else if (this.analex.Preanalisis().getNom() == Token.COMA  || this.analex.Preanalisis().getNom() == Token.CC) {
            return valor;
        }else{
            setError(2);
            return valor;
        }
    }
    
    private int signo(){
        Token actualToken = this.analex.Preanalisis();
        if (actualToken.getNom() == Token.MAS) {
            match(actualToken.getNom());
            return 1;
        }else if (actualToken.getNom() == Token.MENOS) {
            match(actualToken.getNom());
            return 0;
        }else{
            setError(2);
            return -1;
        }
    }
    
    //Escribir las demás producciones....
    //private algo(){  //Algo ->...
    /**ErrorLists
    *   400 = signo incorrecto
    *   1   = inconsistente valor numerico
    *   2   = se espera signo
    */
    private void match(int nomToken){
        int valor = analex.Preanalisis().getNom();
        if ( valor != nomToken ){
            if (nomToken == Token.MAS)
                setError(2);
            else if( valor == Token.MENOS )
                setError(2);
            else if (valor == Token.NUM)
                setError(3);
            else if(valor == Token.CA)
                setError(4);
            else if(valor == Token.CC)
                setError(6);
        }else{
            analex.avanzar();
        }
    }
}
