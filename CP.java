package week11;

import java.util.Arrays;
import java.util.Scanner;

/**
 * CardPile pickup program.
 * Designed to simulate dealing out cards and 
 * picking them up again in various different ways.
 *
 * @author Connor Spear & Alex Lake-Smith.
 */
public class CP implements CardPile{

    /**int[] pile data field used to represent an array of cards.*/
    private static int[] pile;
    /**int rowLength data field used to set length of rows in deck.*/  
    private static int rowLength;
    /**int[] transformArray data field used to transform the array of cards.*/ 
    private static int[] transformArray;
    /**int[] reverse data field used to reverse the pickup method.*/ 
    private static int[] reverse;
    /**int[] pileCopy data field used to represent
     * a copy of the array of cards.*/ 
    private static int[] pileCopy;
    /**String input data field used to read inputs from files/arguments.*/ 
    private static String input;
    /**String spec data field used to give pickup specification.*/   
    private static String spec;
    /**int index data field used as an index for iteration.*/   
    private static int index;
  
    /**
     * main method accepts input either from the command-line or stdin
     * and is used to output various functions defined in the class 
     * such as loading,transforming, counting or printing the deck
     * of cards.
     *
     * @param args represents arguments given to method.
     */ 
    public static void main(String[] args){
        CP myCP = new CP();
        if(args.length == 2){
            if (args[0].matches("[0-9]+") && args[1].matches("[0-9]+")){
                int length = Integer.parseInt(args[0]);
                rowLength = Integer.parseInt(args[1]);
                if (length % rowLength == 0){
                    myCP.load(length);
                    myCP.printCount(rowLength);
                } else {
                    throw new CardPileException("Check row length and length");
                }
            } else {
                throw new CardPileException("Not a positive number");
            }
            return;
        }   
        if(args.length > 2){
            int index = 0; 
            while(index < args.length){
                if(args[0].matches("[0-9]+") && args[1].matches("[0-9]+")){
                    int length = Integer.parseInt(args[index++]);
                    myCP.load(length);
                    rowLength = Integer.parseInt(args[index++]);
                    myCP.printPile(pile);
                    for (int i = 2; i < args.length; i++){
                        String spec = args[index++];
                        myCP.transform(rowLength, spec);
                        myCP.printPile(pile);
                    } 
                }
            }
            return;
        } else {
            Scanner scan = new Scanner(System.in);
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                Scanner scan2 = new Scanner(line);
                while (scan2.hasNext()){
                    input = scan2.next();
                    switch(input){
                        case "l":
                            myCP.load(scan2.nextInt());
                            break;
                        case "p":
                            myCP.printPile(pile);
                            break;
                        case "L":
                            myCP.stringToArray(line);
                            myCP.load(pile);
                            break;
                        case "P":
                            rowLength = scan2.nextInt();
                            myCP.array2d(pile, rowLength);
                            break;
                        case "t":
                            rowLength = scan2.nextInt();
                            spec = scan2.next();
                            myCP.transform(rowLength, spec);
                            break;
                        case "c":
                            rowLength = scan2.nextInt();
                            spec = scan2.next();
                            System.out.println(myCP.count(rowLength, spec));
                            break;
                    }
                }
            }
        }
    }
  
    /**
     * This load method is used to initialise the pile of cards 
     * to consist of the contents numbers provided with the
     * argument 'L'.
     *
     * @param cards represents the given array to load into the pile.
     */   
    public void load(int[] cards){
        pile = new int[cards.length];
        pile = Arrays.copyOfRange(cards, 0, cards.length);
    
    }
  
    /**
     * This load method used to initialise the pile of cards to have size 
     * n in order from 1 through n from top to bottom given by the
     * argument 'l'.
     *
     * @param n represents the size of the pile of cards.
     */  
    public void load(int n){
        if(n < 0){
            throw new CardPileException(n + " is a negative number.");
        }else{  
            int size = n;
            pile = new int[size]; 
            int cardNum = 1; 
            for (int i = 0; i < size; i++){ 
                pile[i] = cardNum;
                cardNum++;
            } 
        }
    }
  
    /**
     * The printPile method is used to create a string builder
     * and print it as a white space separated list.
     *
     * @param pile represents the pile of cards to be printed.
     */  
    public void printPile(int[] pile){
        StringBuilder string = new StringBuilder();
        for (int x: pile){
            string.append(x + " ");
        }
        System.out.println(string);
    }
  
    /**
     * The printCount method calculates and prints the minimum number of 
     * times the pile needs to be transformed for each specification in 
     * order for it to return to its original state.
     *
     * @param rowLength represents the length of 
     * the rows when laid out.
     */  
    public void printCount(int rowLength){
        System.out.println("TL " + count(rowLength, "TL"));
        System.out.println("BL " + count(rowLength, "BL"));
        System.out.println("TR " + count(rowLength, "TR"));
        System.out.println("BR " + count(rowLength, "BR"));
        System.out.println("LT " + count(rowLength, "LT"));
        System.out.println("LB " + count(rowLength, "LB"));
        System.out.println("RT " + count(rowLength, "RT"));
        System.out.println("RB " + count(rowLength, "RB"));
    }
  

    /**
     * The array2d method is used to print a 2D grid representation
     * of the pile of cards laid out.
     *
     * @param deck reprsents the pile of cards to print.
     * @param n represents the length of rows in grid.
     */ 
    public void array2d(int[] deck, int n){
        int colLength = deck.length/n;
        int rowIndex = 0;
        int rowLength = n;
        for (int i = 0; i <colLength; i++){
            for(int j = 0; j <rowLength; j++){
                System.out.print(deck[rowIndex] + " ");
                rowIndex++;
            }
            System.out.println();
        }
    }
  
    /**
     * The stringToArray method is used to split a string and pass the 
     * ints in that string to an array.
     *
     * @param input represents the string to be split into an array.
     */   
    public void stringToArray(String input){
        String[] token = input.split(" ");
        String[] yourArray = Arrays.copyOfRange(token, 1, token.length);
        pile = new int[yourArray.length];
        int i = 0;
        for (String number : yourArray){
            pile[i++] = Integer.parseInt(number);
        }
    
    }
   
    /**
     * The reverse method is used to pickup the current pile 
     * of cards in reverse, this reverse creates easy access
     * to the RB transformation as we call reverse on LT etc.
     *
     * @param deck reprsents the pile of cards to be reversed.
     */ 
    public static void reverse(int[] deck){
        reverse = new int[deck.length];
        for (int i = 0; i < deck.length; i++){
            reverse[i] = pile[(deck.length-1)-i];
        }
        pile = reverse;
    }
  
    /**
     * The getPile method is used to return a copy of the current pile
     * of cards.
     *
     * @return a copy of the current pile of cards.
     */   
    public int[] getPile(){
        pileCopy = new int[pile.length];
        pileCopy = Arrays.copyOf(pile, pile.length);
        return pileCopy;//return the pile   
    }
  
    /**
     * The BL method is used to transform the current pile by
     * picking up the bottom most left card and working
     * bottom to top, left to right.
     *
     * @param deck represents the pile being transformed.
     */   
    public static void bl(int[] deck){
        int colLength = deck.length/rowLength;
        int startNum = rowLength * colLength - rowLength;
        int p = 0;
        transformArray = new int[deck.length];
        while (startNum <= pile.length-1){
            int k = startNum;
            for (int i = 0; i < colLength; i++){
                transformArray[p] = deck[k];
                p++;
                k -= rowLength;
            }
            startNum++;
        }
        pile = transformArray;
    }
  
    /**
     * LB method is used to transform the current pile by picking
     * the cards up along the rows from the bottom left most card and 
     * working left to right, bottom to top.
     *
     * @param deck represents the pile being tranformed.
     */   
    public static void lb(int[] deck){
        int p = 0;
        int colLength = deck.length/rowLength;
        int startNum = rowLength * (colLength - 1);
        transformArray = new int[deck.length];
        while (startNum >= 0){
            int k = startNum;
            for(int i = 0; i < rowLength; i++){
                transformArray[p] = deck[k];
                p++;
                k++;
            }
            startNum -= rowLength;
        }
        pile = transformArray;
    }
  
    /**
     * TL method is used to transform the current pile by picking
     * the cards up from the top most left column and working top 
     * top bottom, left to top.
     *
     * @param deck represents the pile being tranformed.
     */    
    public static void tl(int[] deck){
        int index = 0;
        int colLength = deck.length/rowLength;
        transformArray = new int[deck.length];
        for(int i = 0; i < rowLength; i ++){
            for (int j = 0; j < colLength; j++){
                transformArray[index] = deck[j*rowLength+i];
                index++;
            }
        }
        pile = transformArray;
    }
  
    /**
     * RB method is used to transform the current pile by picking
     * the cards up along the rows from the bottom right most card and 
     * working right to left, bottom to top.
     *
     * @param deck represents the pile being tranformed.
     */  
    public static void rb(int[] deck){
        reverse(pile);
    }
  
    /**
     * RT method is used to transform the current pile by picking
     * the cards up along the rows from the top right most card and 
     * working right to left, top to bottom.
     *
     * @param deck represents the pile being tranformed.
     */  
    public static void rt(int[] deck){
        lb(pile);
        reverse(pile);
    }
  
    /**
     * BR method is used to transform the current pile by picking
     * the cards up from the bottom most right column and working
     * bottom to top, right to left.
     *
     * @param deck represents the pile being tranformed.
     */  
    public static void br(int[] deck){
        tl(pile);
        reverse(pile);
        
    }
  
    /**
     * TR method is used to transform the current pile by picking
     * the cards up from the top most right column and working 
     * top to bottom, right to left.
     *
     * @param deck represents the pile being tranformed.
     */  
    public static void tr(int[] deck){
        bl(pile);
        reverse(pile);
    }
  
    /**
     * The specCheck method is used to check the specification that is
     * passed as input matches a specification required.
     *
     * @param spec represents the string that is being checked.
     * @return output true or false is spec matches.
     */ 
    public boolean specCheck(String spec){
        boolean output;
        if(spec.equals("TL") || spec.equals("BL") || spec.equals("RB") ||
            spec.equals("LB") || spec.equals("RT") || spec.equals("BR") ||
            spec.equals("TR") || spec.equals("LT")){
            output = true;
        } else {
            output = false;
        }
        return output;
    }
     
    /**
     * The transform method is used to transform the current pile 
     * by passing it a given row length and a specification to
     * transform it by.
     *
     * @param rowLength represents the length of the rows in the deck.
     * @param spec represents which way the deck picked up/transformed.
     */    
    public void transform(int rowLength, String spec){
        if (pile.length % rowLength != 0 || (!specCheck(spec))){ //!= true){
            throw new CardPileException ("Check row length and spec inputs");
        }  else {
            switch(spec){
                case "TL":
                    tl(pile);
                    break;
                case "BL":
                    bl(pile);
                    break;
                case "RB": //Reverse of LT
                    rb(pile);
                    break;
                case "LB":
                    lb(pile);
                    break;
                case "RT": //Reverse of LB
                    rt(pile);
                    break;
                case "BR": //Reverse of TL
                    br(pile);
                    break;
                case "TR": //Reverse of BL
                    tr(pile);
                    break;
                case "LT":
                    pile = pile;
                    break;
                default:
                    System.out.println("Please check your spec is correct");
                    break;
            }
        }
    }
  
    /**
     * The count method used to return the minimum number of times 
     * the pile needs to be transformed under the given spec
     * in order for it to return to its original state.
     *
     * @param rowLength represents the length of the rows in the deck.
     * @param spec represents which way the deck picked up/transformed.
     * @return i the count of transformations to transform back to the
     * original pile.
     */    
    public int count(int rowLength, String spec){
        int i;
        if (pile.length % rowLength != 0 || (!specCheck(spec))){// != true){
            throw new CardPileException ("Check row length and spec inputs");
        } else {  
            i = 1;
            pileCopy = pile;
            switch (spec){
                case "TL":
                    tl(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        tl(pile);
                        i++;
                    }
                    break; 
                case "BL":
                    bl(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        bl(pile);
                        i++;
                    }
                    break;
                case "RB":
                    rb(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        rb(pile);
                        i++;
                    }
                    break;
                case "LB":
                    lb(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        lb(pile);
                        i++;
                    }
                    break;
                case "RT":
                    rt(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        rt(pile);
                        i++;
                    }
                    break;
                case "BR":
                    br(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        br(pile);
                        i++;
                    }
                    break;
                case "TR":
                    tr(pile);
                    while (!Arrays.equals(pile, pileCopy)){
                        tr(pile);
                        i++;
                    }
                    break;
                case "LT":
                    while (!Arrays.equals(pile, pileCopy)){
                        i++;
                    }
                    break;
                default:
                    System.out.println("Please check your spec is correct");
                    break;
            }      
        }
        return i;
    }

}
