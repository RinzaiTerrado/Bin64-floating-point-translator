import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.*;

public class MainController implements ActionListener
{
	private MainGUI GUI;

	// inputs
	private static String sign; //first text box
	private static String exponent; //second text box
	private static String binary; //third text box\
	private static String result;

	private String hexadecimal;

	public MainController() {
		GUI = new MainGUI();
		GUI.getConvertButton().addActionListener(this);
		GUI.getClearButton().addActionListener(this);
		GUI.getPasteButton().addActionListener(this);
	}

	public static String normalize() {
		int ep = Integer.parseInt(exponent, 2);
		int e = ep - 1023;
		char finalsign = '+';
		String binTrim = removeZeros(binary);

		if(sign.equals("0"))
		    finalsign = '+';
		if(sign.equals("1"))
		    finalsign = '-';


		return finalsign + "1." + binTrim + "x2^" + e;
    	}
	//remove zeros of binary
	private static String removeZeros(String binary) {
		boolean rzeros = false;
		int i = binary.length();
		int ctr = 0;
		while (!rzeros) {
			if(binary.substring((binary.length() - ctr - 1),(binary.length() - ctr)).equals("0")) {
				ctr++;
			}
			else {
				rzeros = true;
			}
		}
		return binary.substring(0,binary.length() - ctr);
	}

	public static String hexToBinary(String hex) {
		String bin = "";

		hex = hex.toUpperCase();

		HashMap<Character, String> hashMap = new HashMap<Character, String>();

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

		for (int i = 0; i < 16; i++) {
			if (hashMap.containsKey(hex.charAt(i))) {
				bin += hashMap.get(hex.charAt(i));
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

	private static float getFinalFloat(float decimal, int exp) {
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

	private static String convertNormToFloat(String norm) {
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

	private static boolean checkBinary(String sign, String exponent, String binary)
	{
		boolean check = true;

		if (sign.length() != 1 || exponent.length() != 11 || binary.length() != 52)
		{
			check = false;
		}
		else
		{
			if (!(Objects.equals(sign, "0") || Objects.equals(sign, "1")))
			{
				check = false;
			}

			for (int i = 0; i < 11; i++)
			{
				if (!(Objects.equals(exponent.charAt(i), '0') || Objects.equals(exponent.charAt(i), '1')))
				{
					check = false;
				}
			}

			for (int i = 0; i < 52; i++)
			{
				if (!(Objects.equals(binary.charAt(i), '0') || Objects.equals(binary.charAt(i), '1')))
				{
					check = false;
				}
			}
		}

		return check;
	}

	private static boolean checkHexadecimal(String hexadecimal)
	{
		boolean check = true;

		if (hexadecimal.length() != 16)
		{
			check = false;
		}
		else
		{
			String hex = hexadecimal.toUpperCase();

			HashMap<Character, String> hashMap = new HashMap<Character, String>();

			hashMap.put('0', "01");
			hashMap.put('1', "02");
			hashMap.put('2', "03");
			hashMap.put('3', "04");
			hashMap.put('4', "05");
			hashMap.put('5', "06");
			hashMap.put('6', "07");
			hashMap.put('7', "08");
			hashMap.put('8', "09");
			hashMap.put('9', "10");
			hashMap.put('A', "11");
			hashMap.put('B', "12");
			hashMap.put('C', "13");
			hashMap.put('D', "14");
			hashMap.put('E', "15");
			hashMap.put('F', "16");

			for (int i = 0; i < 16; i++) {
				if (!(hashMap.containsKey(hex.charAt(i)))) {
					check = false;
				}
			}
		}

		return check;
	}

	private static boolean specialCase(){
		if(sign.equals("0") == true && exponent.equals("00000000000") == true && binary.equals("0000000000000000000000000000000000000000000000000000") == true){
			result = "+0";
			return true;
		}

		if(sign.equals("1") == true && exponent.equals("00000000000") == true && binary.equals("0000000000000000000000000000000000000000000000000000") == true){
			result = "-0";
			return true;
		}

		if((sign.equals("0") == true|| sign.equals("1") == true) && exponent.equals("00000000000") == true && !(binary.equals("0000000000000000000000000000000000000000000000000000") == true)){
			result = "Denormalized";
			return true;
		}

		if(sign.equals("0") == true && exponent.equals("11111111111") == true && binary.equals("0000000000000000000000000000000000000000000000000000") == true){
			result = "+infinity";
			return true;
		}

		if(sign.equals("1") == true && exponent.equals("11111111111") == true && binary.equals("0000000000000000000000000000000000000000000000000000") == true ){
			result = "-infinity";
			return true;
		}

		if((sign.equals("0") == true|| sign.equals("1") == true) && exponent.equals("11111111111") == true && binary.charAt(0) == '0'){
			result = "sNaN";
			return true;
		}

		if((sign.equals("0") == true || sign.equals("1") == true) && exponent.equals("11111111111") == true && binary.charAt(0) == '1'){
			result = "qNan";
			return true;
		}

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == GUI.getConvertButton()) {
			//Get input
			hexadecimal = GUI.getInputHexadecimal_textField().getText(); //for Hex input

			sign = GUI.getsign_textField().getText();
			exponent = GUI.getexponent_representation_textField().getText();
			binary = GUI.getinputBinary_textField().getText();

			System.out.printf("Sign: %s\n", sign);
			System.out.printf("Binary: %s\n", binary);
			System.out.printf("Exponent: %s\n", exponent);

			if(checkBinary(sign,exponent,binary) == false)
			{
				JOptionPane.showMessageDialog(null, "NULL or Invalid Binary input. Please try again.");
			}

			if(checkHexadecimal(hexadecimal)==false)
			{
				JOptionPane.showMessageDialog(null, "NULL or Invalid Hexadecimal input. Please try again.");
			}

			hexToBinary(hexadecimal);
			removeZeros(binary);

			if(specialCase()==true)
			{
				GUI.setDecimalOutput_textField().setText(result);
			}
			else
			{
				result = normalize();
				result = convertNormToFloat(result);
				GUI.setDecimalOutput_textField().setText(result);
			}


		}

		if(e.getSource() == GUI.getConvertButton2()) {
			//Get input
			hexadecimal = GUI.getInputHexadecimal_textField().getText(); //for Hex input

			sign = GUI.getsign_textField().getText();
			exponent = GUI.getexponent_representation_textField().getText();
			binary = GUI.getinputBinary_textField().getText();

			System.out.printf("Sign: %s\n", sign);
			System.out.printf("Binary: %s\n", binary);
			System.out.printf("Exponent: %s\n", exponent);

			if(checkBinary(sign,exponent,binary) == false)
			{
				JOptionPane.showMessageDialog(null, "NULL or Invalid Binary input. Please try again.");
			}

			if(checkHexadecimal(hexadecimal)==false)
			{
				JOptionPane.showMessageDialog(null, "NULL or Invalid Hexadecimal input. Please try again.");
			}

			hexToBinary(hexadecimal);
			removeZeros(binary);

			if(specialCase()==true)
			{
				GUI.setDecimalOutput_textField().setText(result);
			}
			else
			{
				result = normalize();
				result = convertNormToFloat(result);
				result = convertFloatToFixed(result);
				GUI.setDecimalOutput_textField().setText(result);

			}


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
