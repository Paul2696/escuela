import java.util.Scanner;

public class AnalizadorSintactico {
    AnalizadorLexico lexico = new AnalizadorLexico();

    public boolean validarTipoDato(){
        String reservada = lexico.comparaCadena();
        Scanner sc = new Scanner(System.in);
        String sentencia = sc.nextLine();
        sentencia.split("(); ");
        String identificador;
        if(reservada != null){
            switch(reservada){
                case "int":
                    identificador = lexico.comparaCadena();
                    if(identificador != null){
                        System.out.println(reservada += " " + identificador);
                        return true;
                    }
                    break;

                case "string":
                    identificador = lexico.comparaCadena();
                    if(identificador != null){
                        System.out.println(reservada += " " + identificador);
                        return true;
                    }
                    break;

                case "bool":
                    identificador = lexico.comparaCadena();
                    if(identificador != null){
                        System.out.println(reservada += " " + identificador);
                        return true;
                    }
                    break;

                case "for":
                    identificador = lexico.comparaCadena();
                    if(!identificador.equals("(")){
                        return false;
                    }
                    else{
                        reservada +=  identificador;
                        identificador = lexico.comparaCadena();
                        if()
                    }
            }
        }
        return false;
    }



    public static void main(String[] args){
        AnalizadorSintactico sintactico = new AnalizadorSintactico();
        System.out.println(sintactico.validarTipoDato());
    }
}
