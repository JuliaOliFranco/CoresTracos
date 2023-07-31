package utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

//PlainDocument -> recursos para formatação
public class Validador extends PlainDocument {
	//variavel que ira armazenar o numero maximo de caracteres permitidos
	private int limite;

	//construtor personalizados -> sera usado pelas caixas de texto JTextField
	public Validador(int limite) {
		super();
		this.limite = limite;
	}
	//metodo interno para validar o limite de caracteres
	//BadLocation - tratamento de exceções
	public void insertString(int ofs, String str, AttributeSet a) throws BadLocationException {
		//se o limite não for ultrapassado permitir a digitação
		if ((getLength() + str.length()) <= limite) {
			super.insertString(ofs, str, a);
			
		}
	}
	
}
