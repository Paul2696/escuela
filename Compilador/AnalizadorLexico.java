import java.util.*;
import java.io.File;

public class AnalizadorLexico{
	String[] cadenas = new String[85];

	public String[] lecturaReservadas() throws Exception{
		File file = new File("/home/paul/workspace2/Compilador/reservadas.txt");
		Scanner sc = new Scanner(file);
		int i = 0;
		while(sc.hasNextLine()){
			cadenas[i] = sc.nextLine();
			i++;
		}
		return cadenas;
	}

	public boolean lecturaCadena(String cadena) throws Exception{
		cadenas = lecturaReservadas();
		String letter = Character.toString(cadena.charAt(0));
		for(int i = 0; i < cadenas.length; i++) {
			if (cadena.equals(cadenas[i])) {
				return true;
			}
			else if(letter.equals(cadenas[i]) && i < 75){
				return true;
			}
		}
		return false;
	}

	public String comparaCadena(){
		Scanner sc = new Scanner(System.in);
		String cadena = sc.nextLine();
		try{
			if(lecturaCadena(cadena)) {
				return cadena;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return null;
	}



	public static void main(String[] args) throws Exception{
		AnalizadorLexico lexico = new AnalizadorLexico();
		Scanner sc = new Scanner(System.in);
		String[] prueba = lexico.lecturaReservadas();
		for(int i = 0; i < 85; i++){
			System.out.println(prueba[i]);
		}
		lexico.comparaCadena();
	}
}	