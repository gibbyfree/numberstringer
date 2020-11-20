import java.util.HashMap;
import java.util.Scanner;

public class NumberStringer {
    private HashMap<Integer, String> ones;
    private HashMap<Integer, String> tens;
    private int toTranslate;

    public NumberStringer(int toTranslate) {
        initializeOnes();
        initializeTens();
        this.toTranslate = toTranslate;
    }

    /**
     * Initializes the NumberStringer's ones place lookup table.
     */
    private void initializeOnes() {
        this.ones = new HashMap<>();
        this.ones.put(0, "");
        this.ones.put(1, "one");
        this.ones.put(2, "two");
        this.ones.put(3, "three");
        this.ones.put(4, "four");
        this.ones.put(5, "five");
        this.ones.put(6, "six");
        this.ones.put(7, "seven");
        this.ones.put(8, "eight");
        this.ones.put(9, "nine");
    }

    /**
     * Initializes the NumberStringer's tens place lookup table.
     */
    private void initializeTens() {
        // TODO: Some of these can be removed.
        this.tens = new HashMap<>();
        this.tens.put(1, "one");
        this.tens.put(2, "twenty");
        this.tens.put(3, "thirty");
        this.tens.put(4, "forty");
        this.tens.put(5, "fifty");
        this.tens.put(6, "sixty");
        this.tens.put(7, "seventy");
        this.tens.put(8, "eighty");
        this.tens.put(9, "ninety");
        this.tens.put(0, "and");
    }

    /**
     * Creates an array of digits from a number in String format. 
     * 
     * @param number The number being split into digits, as a String
     * @return The digit array representation of the number
     */
    private static int[] createDigitArray(String number) {
        int[] digitArray = new int[number.length()];
        for(int i = 0; i < number.length(); i++) {
            digitArray[i] = Character.getNumericValue((number.charAt(i)));
        }
        return digitArray;
    }

    /**
     * Determines place of an index in the digit array.
     * 
     * @param index The index into the digit array
     * @param arrayLength The length of the digit array
     * @return The place of the index, as a String
     */
    private static String determinePlace(int index, int arrayLength) {
        int place = arrayLength - index; 
        if(place == 4) {
            return "thousand";
        }
        else if(place == 3) {
            return "hundred";
        }
        else if(place == 2) {
            return "tens";
        }
        else {
            return "ones";
        }
    }

    // 100 -> One hundred
    // 3 -> Three
    // 55 -> Fifty five
    // 1743 -> One thousand seven hundred forty three
    // 4304 -> Four thousand three hundred and four
    public String translateInt() {
        StringBuilder sb = new StringBuilder();
        String temp = Integer.toString(this.toTranslate);
        int[] digits = createDigitArray(temp);
        
        for(int i = 0; i < digits.length - 1; i++) {
            // What's the number and what's the place?
            String place = determinePlace(i, digits.length);
            if(place.equals("ones")) {
                sb.append(this.ones.get(digits[i]));
            }
            else if(place.equals("tens")) {
                // We're at the last two digits of the number
                String placeValue = this.tens.get(digits[i]);
                sb.append(" " + placeValue +  " " + this.ones.get(digits[i + 1]));
                break;
            }
            else if(place.equals("hundred")) {
                sb.append(" " + this.ones.get(digits[i]) + " " + place);
            }
            else if(place.equals("thousand")) {
                sb.append(this.ones.get(digits[i]) + " " + place);
            }
        }
        return sb.toString();
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int toTranslate = in.nextInt();
            NumberStringer n = new NumberStringer(toTranslate);
            String output = n.translateInt();
            System.out.println(output);
        }
    }

}