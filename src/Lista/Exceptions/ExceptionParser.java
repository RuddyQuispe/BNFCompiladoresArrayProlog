/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lista.Exceptions;

/**
 *
 * @author Ruddy
 */
public class ExceptionParser extends Exception {
    
    private int IDERROR;
    /**
     * Creates a new instance of <code>ExceptionPareser</code> without detail
     * message.
     */
    public ExceptionParser(int idError) {
        super();
        this.IDERROR = idError;
    }
    
    public String getError(){
        switch(this.IDERROR){
            case 2: return "Falta Signo { + , - }";
            default: return "Error Desconocido";
        }
    }
    /**
     * Constructs an instance of <code>ExceptionPareser</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExceptionParser(String msg) {
        super(msg);
    }
}
