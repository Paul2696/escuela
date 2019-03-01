import java.util.*;
import java.io.File;

public class AnalizadorLexico {
	private String input;
	private int index = 0;
	private int state = 0;
	private Token token = null;

	public AnalizadorLexico(String input) {
		this.input = input;
	}

	public Token getNextToken() {
		Token token = null;
		StringBuilder str = new StringBuilder();
		while(index < input.length()){
			char c = input.charAt(index);
			if((c == '+' || c == '-' || c == '*' || c == '/') && state == 0) {
				token = new Token(Character.toString(c), Token.ARITHMETIC_OPERATOR);
				index++;
				break;
			}
			else if(c == ';' && state == 0){
				token = new Token(Character.toString(c), Token.SEMICOLON);
				index++;
				break;
			}
			else if(((c == '<' || c == '>') && state == 0)){
				token = new Token(Character.toString(c), Token.LOGICAL_OPERATOR);
				if(!(index + 1 >= input.length()) && input.charAt(index + 1) == '='){
					token.token += Character.toString(input.charAt(index + 1));
					index ++;
				}
				index++;
				break;
			}
			else if(c == '!' && state == 0){
				token = new Token(Character.toString(c), Token.LOGICAL_OPERATOR);
				index++;
				break;
			}
			else if(c == '=' && state == 0){
				if(input.charAt(index + 1) == '='){
					token = new Token(Character.toString(c), Token.LOGICAL_OPERATOR);
					token.token += Character.toString(input.charAt(index + 1));
					index += 2;
					break;
				}
				token = new Token(Character.toString(c), Token.EQUALS);
				index++;
				break;
			}
			else if(c == '|' && state == 0){
				if(!(index + 1 >= input.length()) && input.charAt(index + 1) == '|'){
					token = new Token(Character.toString(c), Token.LOGICAL_OPERATOR);
					token.token += Character.toString(input.charAt(index + 1));
					index += 2;
					break;
				}
			}
			else if(c == '&' && state == 0){
				if(!(index + 1 >= input.length()) && input.charAt(index + 1) == '&'){
					token = new Token(Character.toString(c), Token.LOGICAL_OPERATOR);
					token.token += Character.toString(input.charAt(index + 1));
					index += 2;
					break;
				}
			}
			else if(c == '('){
				token = new Token(Character.toString(c), Token.OPENPARENTHESES);
				index++;
				break;
			}
			else if(c == ')'){
				token = new Token(Character.toString(c), Token.CLOSEDPARENTHESES);
				index++;
				break;
			}
			else if(c == '{'){
				token = new Token(Character.toString(c), Token.OPENBRACES);
				index++;
				break;
			}
			else if(c == '}'){
				token = new Token(Character.toString(c), Token.CLOSEDBRACES);
				index++;
				break;
			}
			else if(c == ','){
				token = new Token(Character.toString(c), Token.COMMA);
				index++;
				break;
			}
			else if((Character.isDigit(c)) && state == 0 || state == 10){
				changeState(10);
				str.append(c);
				if(!(index + 1 >= input.length()) && (!Character.isDigit(input.charAt(index + 1)))){
					index++;
					break;
				}
			}
			else if(c == '\'' && state == 0){
				changeState(30);
				str.append(c);
			}
			else if(Character.isAlphabetic(c) && state == 30){
				str.append(c);
				if(!(index + 1 >= input.length()) && input.charAt(index + 1) == '\''){
					str.append(input.charAt(index + 1));
					index += 2;
					break;
				}
			}
			else if(Character.isAlphabetic(c) || Character.isDigit(c)){
				if(Character.isAlphabetic(c)){
					if(state == 7){
						str.append(c);
						if(str.toString().equals("true") || str.toString().equals("false")){
							changeState(31);
							token = new Token(str.toString(), Token.BOOLEAN);
							index++;
							break;
						}
						if(str.toString().equals("return")){
							changeState(35);
							token = new Token(str.toString(), Token.RETURN);
							index++;
							break;
						}
						if(!(index + 1 >= input.length()) && !Character.isAlphabetic(input.charAt(index + 1))
						&& !Character.isDigit(input.charAt(index + 1))){
							index++;
							break;
						}
					}
					else if(c == 'b' && state == 0){
						changeState(23);
						str.append(c);
					}
					else if(c == 'o' && state == 23){
						changeState(24);
						str.append(c);
					}
					else if(c == 'o' && state == 24){
						changeState(25);
						str.append(c);
					}
					else if(c == 'l' && state == 25){
						changeState(13);
						str.append(c);
					}
					else if(c == 'c' && state == 0){
						changeState(14);
						str.append(c);
					}
					else if(c == 'h' && state == 14){
						changeState(15);
						str.append(c);
					}
					else if(c == 'a' && state == 15){
						changeState(16);
						str.append(c);
					}
					else if(c == 'r' && state == 16){
						changeState(13);
						str.append(c);
					}
					else if(c == 'i' && state == 0){
						changeState(11);
						str.append(c);
					}
					else if(c == 'n' && state == 11){
						changeState(12);
						str.append(c);
					}
					else if(c == 't' && state == 12){
						changeState(13);
						str.append(c);
					}
					else{
						changeState(7);
						str.append(c);
					}
					if(state == 13) {
						token = new Token(str.toString(), Token.DATA_TYPE);
						if(!(index + 1 >= input.length()) && Character.isWhitespace(input.charAt(index + 1))){
							index++;
							break;
						}
					}
				}
			}
			index++;
		}
		if(state == 7){
			token = new Token(str.toString(), Token.IDENTIFIER);
		}
		else if(state == 10){
			token = new Token(str.toString(), Token.NUMBER);
		}
		else if(state == 30){
			token = new Token(str.toString(), Token.CHAR);
		}
		state = 0;
		return token;
	}


	private void changeState(int state){
		this.state = state;
	}

	public static void main(String[] args){
		AnalizadorLexico lexico = new AnalizadorLexico("bool Oscar(int cola, bool puto) {return true;}");
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
		System.out.println(lexico.getNextToken());
	}

}