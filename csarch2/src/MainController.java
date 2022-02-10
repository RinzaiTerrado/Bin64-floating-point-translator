import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class MainController implements ActionListener
{
    private MainGUI GUI;
    //private String hexadecimal;
    // inputs
    private static String sign; //first text box
    private static String exponent; //second text box
    private static String binary; //third text box

    private String hexadecimal;

    public MainController() {
        GUI = new MainGUI();
        GUI.getConvertButton().addActionListener(this);
        GUI.getClearButton().addActionListener(this);
        GUI.getPasteButton().addActionListener(this);
    }

    public static String normalize(){
    int ep = Integer.parseInt(exponent,2);
    int e = ep-1023;
    String binTrim = removezeros(binary);
    return "+"+"1."+binTrim+"x2^"+e;
}
    //remove zeros of binary
    private static String removeZeros(String binary) {
        boolean rzeros = false;
        int i = binary.length();
        int ctr = 0;
        while (!rzeros){
            if(binary.substring((binary.length() - ctr - 1),(binary.length() - ctr)).equals("0")){
                ctr++;
            } else {
                rzeros = true;
            }
        }
        return binary.substring(0,binary.length() - ctr);
    }

    public static String hexToBinary(String hex) {
        String bin = "";

        hex = hex.toUpperCase();

        HashMap<Character, String> hashMap
                = new HashMap<Character, String>();

        hashMap.put('0', "0000");
        hashMap.put('1', "0001");
        hashMap.put('2', "0010");
        hashMap.put('3', "0011");
        hashMap.put('4', "0100");
        hashMap.put('5', "0101");
        hashMap.put('6', "0110");
        hashMap.put('7', "0111");
        hashMap.put('8', "1000");
        hashMap.put('9', "1001");
        hashMap.put('A', "1010");
        hashMap.put('B', "1011");
        hashMap.put('C', "1100");
        hashMap.put('D', "1101");
        hashMap.put('E', "1110");
        hashMap.put('F', "1111");

        int i;
        char ch;
        for (i = 0; i < hex.length(); i++) {
            ch = hex.charAt(i);

            if (hashMap.containsKey(ch)) {
                bin += hashMap.get(ch);
            }
            else {
                bin = "Invalid Hexadecimal String";
                return bin;
            }
        }
        sign = bin.substring(0,1);
        exponent = bin.substring(1,12);
        binary = bin.substring(12,bin.length());
        return bin;
    }

    private static String convertFloatToFixed(String flop) {
        String[] arr = new String[]{"",""};
        arr = flop.split("x");
        BigDecimal bd1 = new BigDecimal(arr[0]);
        String expFix = arr[1].substring(3,arr[1].length());

        int expint =Integer.parseInt(expFix);

        double expdoub = Math.pow(10, expint);
        BigDecimal bd2 = new BigDecimal(expdoub);
        BigDecimal fixed = bd1.multiply(bd2);
        String fixedStr = fixed.toString();
        return fixedStr;
    }

    private static float getFinalFloat(float decimal, int exp){
        float finaldecimal = 0;

        int multiplier = 1;

        if (exp > 0) {
            for(int i = 1; i <= exp; i++) {
                multiplier *= 2;
            }

            finaldecimal = decimal * multiplier;
        }
        else if (exp < 0) {
            for(int i = -1; i >= exp; i--) {
                multiplier *= 2;
            }

            finaldecimal = decimal * multiplier;
        }

        return finaldecimal;
    }

    private static String convertNormToFloat(String norm){
        String[] arr = norm.split("x");

        String sign = arr[0].substring(0, 0);
        BigDecimal bd = new BigDecimal(arr[0]);
        BigDecimal bd1 = bd.movePointRight(4);
        int binary = bd1.intValue();
        int exp = Integer.parseInt(arr[1].substring(2));
        int[] fractional = new int[5];
        float decimal = 0;

        for (int i = 0; i < 5; i++) {
            fractional[4 - i] = binary % 10;
            binary /= 10;
        }

        for (int i = 0; i < 5; i++) {
            double divisor = Math.pow(2, i);
            decimal += fractional[i]/divisor;
        }

        if (Objects.equals(sign, "-")) {
            return Float.toString(getFinalFloat(decimal, exp));
        }
        else {
            return "+" + Float.toString(getFinalFloat(decimal, exp));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == GUI.getConvertButton())
        {
            //Get input
            hexadecimal = GUI.getInputHexadecimal_textField().getText(); //for Hex input

            sign = GUI.getsign_textField().getText(); 
            exponent = GUI.getexponent_representation_textField().getText();
            binary = GUI.getinputBinary_textField().getText(); 
            
            System.out.printf("Sign: %s\n", sign);
            System.out.printf("Binary: %s\n", binary);
            System.out.printf("Exponent: %s\n", exponent);
            
            
            if (hexadecimal.length() == 0)//HEX FIELD NULL
            {
                if (!(sign.equals("1")) && !(sign.equals("0")))//SIGN FIELD NULL
                {
                    JOptionPane.showMessageDialog(null, "Invalid sign input! 1 or 0 only.");
				    GUI.getsign_textField().setText("");
                }
                
                if (binary.length() == 0)//EXPONENT FIELD NULL
                {
                    JOptionPane.showMessageDialog(null, "Binary representation field is empty. Try again.");

                }
                
                if (exponent.length() == 0)//EXPONENT FIELD NULL
                {
                    JOptionPane.showMessageDialog(null, "Exponent field is empty. Try again.");

                }
                if(!(sign.equals("1")) && !(sign.equals("0")) && binary.length() == 0 && exponent.length() == 0)
                JOptionPane.showMessageDialog(null, "Both Binary and Hexadecimal fields are empty. Try again.");

            }
            
            if (hexadecimal.length() != 0) //HEX CHECK FOR INVALID INPUT
            {
                hexadecimal = hexadecimal.toUpperCase();

                HashMap<Character, String> hashMap
                        = new HashMap<Character, String>();

                hashMap.put('0', "0000");
                hashMap.put('1', "0001");
                hashMap.put('2', "0010");
                hashMap.put('3', "0011");
                hashMap.put('4', "0100");
                hashMap.put('5', "0101");
                hashMap.put('6', "0110");
                hashMap.put('7', "0111");
                hashMap.put('8', "1000");
                hashMap.put('9', "1001");
                hashMap.put('A', "1010");
                hashMap.put('B', "1011");
                hashMap.put('C', "1100");
                hashMap.put('D', "1101");
                hashMap.put('E', "1110");
                hashMap.put('F', "1111");

                int i;
                char ch;
                for (i = 0; i < hexadecimal.length(); i++) {
                        ch = hexadecimal.charAt(i);

                        if (hashMap.containsKey(ch)) {
                        }
                        else {
                            JOptionPane.showMessageDialog(null, "Invalid Hexadecimal input. Try again.");
                         }
                }
            }   
            
            

            // BEFORE the normalize code below, put a function for converting hexadecimal to binary(?)
            // https://www.geeksforgeeks.org/java-program-to-convert-hexadecimal-to-binary/

            //normalize if not within range 1.0 to 2.0, if input is 0 then immediately convert (if input is binary)
            int ctr = 0;
            double float_binary = Double.parseDouble(binary);
            System.out.printf("float_binary: %f\n", float_binary);
            if(float_binary != 0)
            {
                if(float_binary < 1.0)
                {
                    while(float_binary < 1.0)
                    {
                        float_binary *= 10.0;
                        ctr--;
                    }
                }

                if(float_binary > 2.0)
                {
                    while(float_binary > 2.0)
                    {
                        float_binary /= 10.0;
                        ctr++;
                    }
                }
            }

            //Dunno whats happening here
            System.out.printf("counter(add to exponent): %d\n", ctr);
            String binary = String.format("%.15f", float_binary);
            int j = binary.length();

            // In our case, we should extract the first character then store it sa signbit variable. Then I guess we just swap
            // The + with 0 and - with 1 in this part below
            //get the sign bit
            String signbit="";
            if(sign.equals("+"))
            {
                signbit = "0";
            }
            else
                signbit = "1";
            System.out.printf("signbit: %s\n", signbit);

            //REMOVE(?)
            //I guess in our case we should get the 2nd-11th digit of the binary input
            //Then convert to decimal, then do the subtraction e'-1023
            //Answer will be stored in 'e' or 'exponent' variable
            // for(i=1;i<11;i++)
            //		e_prime = binary.charAt(i)
            //
            // e = Integer.parseInt(e_prime,2)) - 1023 (Ex. 5 = 1028-1023)
            int int_exponent = Integer.parseInt(exponent);
            //add ctr to exponent after normalizing
            int_exponent += ctr;
            int e_prime = int_exponent + 127;


            //Special Case 4
            //negative infinity
            if(int_exponent > 127 && signbit.equals("1"))
            {
                e_prime = 255;
                JOptionPane.showMessageDialog(null, "Special Case: Negative Infinity");
            }

            //Special Case 5
            //positive infinity
            if(int_exponent > 127 && signbit.equals("0"))
            {
                e_prime = 255;
                JOptionPane.showMessageDialog(null, "Special Case: Positive Infinity");
            }

            String e_binary = Integer.toBinaryString(e_prime);
            if(binary.equals("0.000000000000000"))
            {
                e_binary = "00000000";
            }
            else
            {
                while(e_binary.length() < 8)
                {
                    e_binary = "0" + e_binary;
                }
            }
            System.out.printf("e_binary: %s\n", e_binary);

            System.out.printf("normalized mantissa: %s\n", binary);
            int binaryLength = binary.length();
            char digit;

            boolean normalizable = true;
            //check coefficient if 1
            digit = binary.charAt(0);
            if(digit != '1')
            {
                normalizable = false;
            }

            //check if decimal digits are 0 or 1
            for(int l=2;l<binaryLength;l++)
            {
                digit = binary.charAt(l);
                if(digit != '0' && digit != '1')
                {
                    normalizable = false;
                }
            }
            if(binary.equals("0.000000000000000"))
            {
                normalizable = true;
            }
            if(normalizable == false)
            {
                JOptionPane.showMessageDialog(null, "Could not normalize");
            }

            //Special Case 3
            if(int_exponent < -126)
            {
                JOptionPane.showMessageDialog(null, "Special Case: Denormalized");
                while(int_exponent < -126)
                {
                    float_binary /= 10.0;
                    int_exponent++;
                }
                e_binary = "00000000";
                binary = String.format("%.15f", float_binary);
                j = binary.length();
            }

            //In our case, we need to get the remaining 52 bits
            //get the remaining 23 bits
            String bit23 = "";
            int k;
            char temp;
            String strtemp;
            k = 2;
            while(k < j)
            {
                temp = binary.charAt(k);
                strtemp = Character.toString(temp);
                bit23 = bit23 + strtemp;
                k++;
            }

            int l = bit23.length();
            if(int_exponent > 127)
                bit23 = "00000000000000000000000";
            else
                while(l < 23 )
                {
                    bit23 = bit23 + "0";
                    l++;
                }
            System.out.printf("bit23: %s\n", bit23);

            String binaryOutput = "";
            binaryOutput = signbit + e_binary + bit23;
            System.out.printf("binaryOutput: %s\n", binaryOutput);

            String str4bit_1 = "";
            String str4bit_2 = "";
            String str4bit_3 = "";
            String str4bit_4 = "";
            String str4bit_5 = "";
            String str4bit_6 = "";
            String str4bit_7 = "";
            String str4bit_8 = "";
            k=0;

            //separate into 4 bits
            while(k < 4)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_1 = str4bit_1 + strtemp;
                k++;
            }
            while(k < 8)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_2= str4bit_2 + strtemp;
                k++;
            }
            while(k < 12)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_3= str4bit_3 + strtemp;
                k++;
            }

            while(k < 16)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_4= str4bit_4 + strtemp;
                k++;
            }

            while(k < 20)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_5= str4bit_5 + strtemp;
                k++;
            }

            while(k < 24)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_6= str4bit_6 + strtemp;
                k++;
            }

            while(k < 28)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_7= str4bit_7 + strtemp;
                k++;
            }

            while(k < 32)
            {
                temp = binaryOutput.charAt(k);
                strtemp = Character.toString(temp);
                str4bit_8= str4bit_8 + strtemp;
                k++;
            }
            //add space after 4 bits
            String finalBinary = str4bit_1 + " " + str4bit_2 + " "+ str4bit_3 + " " + str4bit_4 +" "+ str4bit_5 + " " + str4bit_6 +" "+ str4bit_7 + " " + str4bit_8;

            System.out.printf("1: %s\n", str4bit_1);
            System.out.printf("2: %s\n", str4bit_2);
            System.out.printf("3: %s\n", str4bit_3);
            System.out.printf("4: %s\n", str4bit_4);
            System.out.printf("5: %s\n", str4bit_5);
            System.out.printf("6: %s\n", str4bit_6);
            System.out.printf("7: %s\n", str4bit_7);
            System.out.printf("8: %s\n", str4bit_8);


            int  int4bit_1 = Integer.parseInt(str4bit_1,2);
            int  int4bit_2 = Integer.parseInt(str4bit_2,2);
            int  int4bit_3 = Integer.parseInt(str4bit_3,2);
            int  int4bit_4 = Integer.parseInt(str4bit_4,2);
            int  int4bit_5 = Integer.parseInt(str4bit_5,2);
            int  int4bit_6 = Integer.parseInt(str4bit_6,2);
            int  int4bit_7 = Integer.parseInt(str4bit_7,2);
            int  int4bit_8 = Integer.parseInt(str4bit_8,2);

            System.out.printf("1: %s\n", int4bit_1);
            System.out.printf("2: %s\n", int4bit_2);
            System.out.printf("3: %s\n", int4bit_3);
            System.out.printf("4: %s\n", int4bit_4);
            System.out.printf("5: %s\n", int4bit_5);
            System.out.printf("6: %s\n", int4bit_6);
            System.out.printf("7: %s\n", int4bit_7);
            System.out.printf("8: %s\n", int4bit_8);

            String hex1 = Integer.toHexString(int4bit_1);
            String hex2 = Integer.toHexString(int4bit_2);
            String hex3 = Integer.toHexString(int4bit_3);
            String hex4 = Integer.toHexString(int4bit_4);
            String hex5 = Integer.toHexString(int4bit_5);
            String hex6 = Integer.toHexString(int4bit_6);
            String hex7 = Integer.toHexString(int4bit_7);
            String hex8 = Integer.toHexString(int4bit_8);

            String finalHex = hex1 + hex2 + hex3+ hex4 + hex5 + hex6 + hex7 + hex8;
            finalHex = finalHex.toUpperCase();
            System.out.printf("hex: %s\n", finalHex);
            //GUI.getDecimalOutput_textField().setText(finalBinary); // this might not be working as intended anymore
            GUI.setHexOutput_textField().setText(finalHex);

        }

        if(e.getSource() == GUI.getPasteButton())
        {
            String output = GUI.getDecimalOutput_textField().getText();
            StringSelection stringSelection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }

        if(e.getSource() == GUI.getClearButton())
        {
            //clear all text fields
            GUI.getInputHexadecimal_textField().setText("");
            GUI.getinputBinary_textField().setText("");
            GUI.getDecimalOutput_textField().setText("");
        }
    }


}
