import java.util.*;
import java.io.File;

public class AnalizadorLexico {
	private String input;
	private int index = 0;
	private int state = 0;

	public AnalizadorLexico(String input) {
		this.input = input;
	}

	public Token getNextToken() {
		while(Character.isWhitespace(input.charAt(index))){
			char c = input.charAt(index);
			switch(c){
				case '+':
					break;
				case '-':
					break;
				case '*':
					break;
				case '/':
					break;
				case '<':
					break;
				case '>':
					break;
				case '=':
					break;
				case '_':
					break;
				case '!':
					break;
				case '&':
					break;
				case '|':
					break;
				case ';':
					break;
				default:
					if(Character.isAlphabetic(c)){

					}
					else if(Character.isDigit(c)){

					}

			}
		}
		return null;
	}

	private void changeState(int state){
		this.state = state;
	}

}