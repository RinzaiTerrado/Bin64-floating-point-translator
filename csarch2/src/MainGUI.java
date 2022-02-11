import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.Color;
import javax.swing.JTextArea;

public class MainGUI extends JFrame{


	private JTextField DecimalOutput_textField;// decimal output
	private JLabel lblNewLabel; //title ng program
	private JButton ConvertButton; //fixed
	private JButton ConvertButton2; //FP
	private JTextField inputBinary_textField; //input for binary
	private JTextField inputHexadecimal_textField; //input for hex
	private JTextField sign_textField;
	private JTextField exponent_representation_textField;
	private JButton ClearButton; //clear
	private JButton PasteButton; //paste to notepad
	private JLabel lblOutput; //output label
	private JLabel lblBinary; //binary label
	private JLabel lblHexadecimal; //hex label


	public MainGUI() {
		setBounds(100, 100, 878, 663);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		initialize();
		setVisible(true);
	}


	/**
	 * Create the application.
	 */


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		DecimalOutput_textField = new JTextField();
		DecimalOutput_textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		DecimalOutput_textField.setBounds(317, 446, 227, 32);
		getContentPane().add(DecimalOutput_textField);
		DecimalOutput_textField.setColumns(10);


		lblNewLabel = new JLabel("IEEE-754 Binary-64 floating point translator ");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 35));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(1, 10, 666, 62);
		getContentPane().add(lblNewLabel);

		ConvertButton = new JButton("Fixed");
		ConvertButton.setForeground(Color.BLACK);
		ConvertButton.setFont(new Font("Calibri", Font.PLAIN, 15));
		ConvertButton.setBounds(340, 320, 85, 32);
		getContentPane().add(ConvertButton);

		ConvertButton2 = new JButton("FP");
		ConvertButton2.setForeground(Color.BLACK);
		ConvertButton2.setFont(new Font("Calibri", Font.PLAIN, 15));
		ConvertButton2.setBounds(450, 320, 85, 32);
		getContentPane().add(ConvertButton2);

		lblOutput = new JLabel("Output");
		lblOutput.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblOutput.setBounds(410, 403, 85, 32);
		getContentPane().add(lblOutput);

		lblBinary = new JLabel("Binary");
		lblBinary.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblBinary.setBounds(409, 234, 85, 32);
		getContentPane().add(lblBinary);

		lblHexadecimal = new JLabel("Hexadecimal");
		lblHexadecimal.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblHexadecimal.setBounds(384, 153, 133, 32);
		getContentPane().add(lblHexadecimal);

		ClearButton = new JButton("Clear");
		ClearButton.setForeground(Color.BLACK);
		ClearButton.setFont(new Font("Calibri", Font.PLAIN, 15));
		ClearButton.setBounds(681, 553, 85, 32);
		getContentPane().add(ClearButton);

		PasteButton = new JButton("Paste");
		PasteButton.setForeground(Color.BLACK);
		PasteButton.setFont(new Font("Calibri", Font.PLAIN, 15));
		PasteButton.setBounds(591, 553, 85, 32);
		getContentPane().add(PasteButton);

		sign_textField = new JTextField();
		sign_textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		sign_textField.setColumns(10);
		sign_textField.setBounds(199, 264, 25, 32);
		getContentPane().add(sign_textField);

		exponent_representation_textField = new JTextField();
		exponent_representation_textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		exponent_representation_textField.setColumns(10);
		exponent_representation_textField.setBounds(235, 264, 150, 32);
		getContentPane().add(exponent_representation_textField);

		inputBinary_textField = new JTextField();
		inputBinary_textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputBinary_textField.setColumns(10);
		inputBinary_textField.setBounds(395, 264, 268, 32);
		getContentPane().add(inputBinary_textField);

	
		inputHexadecimal_textField = new JTextField();
		inputHexadecimal_textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		inputHexadecimal_textField.setColumns(10);
		inputHexadecimal_textField.setBounds(199, 184, 463, 32);
		getContentPane().add(inputHexadecimal_textField);
		

	}


	public JTextField getsign_textField()
	{
		return sign_textField;
	}

	public void setsign_textField(JTextField sign_textField)
	{
		this.sign_textField = sign_textField;
	}

	public JTextField getexponent_representation_textField()
	{
		return exponent_representation_textField;
	}

	public void setexponent_representation_textField(JTextField exponent_representation_textField)
	{
		this.exponent_representation_textField = exponent_representation_textField;
	}

	
	public void setinputBinary_textField(JTextField inputBinary_textField)
	{
		this.inputBinary_textField = inputBinary_textField;
	}

	
	public void setInputHexadecimal_textField(JTextField inputHexadecimal_textField)
	{
		this.inputHexadecimal_textField = inputHexadecimal_textField;
	}

	
	public JTextField getinputBinary_textField()
	{
		return inputBinary_textField;
	}
	public JTextField getInputHexadecimal_textField()
	{
		return inputHexadecimal_textField;
	}


	public JTextField setDecimalOutput_textField() 
	{
		return DecimalOutput_textField;
	}

	public JTextField getDecimalOutput_textField() 
	{
		return DecimalOutput_textField;
	}
	public JButton getConvertButton()
	{
		return ConvertButton;
	}

	public JButton getConvertButton2()
	{
		return ConvertButton2;
	}

	public JButton getClearButton()
	{
		return ClearButton;
	}

	public JButton getPasteButton()
	{
		return PasteButton;
	}
}
