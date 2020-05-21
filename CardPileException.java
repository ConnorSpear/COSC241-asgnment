package week11;

/**
 * This class creates a cardPileException extending
 * RunTimeException for the class CP.java.
 *
 * @author Connor Spear & Alex Lake-Smith.
 */
public class CardPileException extends RuntimeException{
    
    /**Dafult serial version uid.*/
    private static final long serialVersionUID = 4L;
    
    /**This method gives the exception a string.
     *
     * @param message is the message to send the exception.
     */
    public CardPileException(String message){
        super(message);
    }
}
