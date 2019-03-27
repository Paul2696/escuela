import java.util.HashMap;
import java.util.Map;

public class AnalizadorSintactico {
    AnalizadorLexico lexico;
    Token token;
    Map<String, String> identifiers = new HashMap<>();


    public AnalizadorSintactico(String input){
        lexico = new AnalizadorLexico(input);
        token = lexico.getNextToken();

    }


    private boolean checkVariable(){
        if(token.type == Token.DATA_TYPE){
            Token dataType = token;
            token = lexico.getNextToken();
            if(token.type == Token.IDENTIFIER){
                identifiers.put(token.token, dataType.token);
                Token identifierName = token;
                token = lexico.getNextToken();
                if(checkAssignation(dataType, identifierName)){
                    token = lexico.getNextToken();
                    return true;
                }
                if(token.type == Token.SEMICOLON){
                    token = lexico.getNextToken();
                    return true;
                }
            }
        }
        return false;
    }


    private boolean checkAssignation(Token dataType, Token identifierName){
        if(token.type == Token.EQUALS){
            token = lexico.getNextToken();
            if(token.type == Token.IDENTIFIER){
                for(String identifier : identifiers.keySet()){
                    if(identifier.equals(token.token) && identifiers.get(identifier).equals(dataType.token)){
                        token = lexico.getNextToken();
                        if(token.type == Token.SEMICOLON){
                            return true;
                        }
                    }
                }
            }
            else if(token.type == Token.CHAR && identifiers.get(identifierName.token).equals("char")){
                token = lexico.getNextToken();
                if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
            else if(token.type == Token.NUMBER && identifiers.get(identifierName.token).equals("int")){
                token = lexico.getNextToken();
                if(token.type == Token.ARITHMETIC_OPERATOR){
                    token = lexico.getNextToken();
                    if(token.type == Token.NUMBER){
                        token = lexico.getNextToken();
                        if(token.type == Token.SEMICOLON){
                            return true;
                        }
                    }
                }
                else if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFunction(){
        if(token.type == Token.DATA_TYPE){
            String dataType = token.token;
            token = lexico.getNextToken();
            if(token.type == Token.IDENTIFIER){
                identifiers.put(token.token, dataType);
                token = lexico.getNextToken();
                if(token.type == Token.OPENPARENTHESES){
                    token = lexico.getNextToken();
                    while(token.type != Token.CLOSEDPARENTHESES){
                        if(token.type == Token.DATA_TYPE){
                            dataType = token.token;
                            token = lexico.getNextToken();
                            if(token.type == Token.IDENTIFIER){
                                identifiers.put(token.token, dataType);
                                token = lexico.getNextToken();
                                if(token.type == Token.COMMA){
                                    token = lexico.getNextToken();
                                }
                                else if(token.type == Token.DATA_TYPE){
                                    return false;
                                }
                            }
                        }
                        else{
                            return false;
                        }
                    }
                    token = lexico.getNextToken();
                    if(token.type == Token.OPENBRACES){
                        token = lexico.getNextToken();
                        if(checkCode()){
                            token = lexico.getNextToken();
                            if(token == null){
                                return false;
                            }
                            if(token.type == Token.CLOSEDBRACES){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkCode() {
        while (token.type != Token.RETURN) {
            if (!checkVariable()) {
                return false;
            }
        }
        if (token.type == Token.RETURN) {
            token = lexico.getNextToken();
            if (token.type == Token.NUMBER && identifiers.get("funcion").equals("int")) {
                token = lexico.getNextToken();
                if (token.type == Token.SEMICOLON) {
                    return true;
                }
            } else if (token.type == Token.BOOLEAN && identifiers.get("funcion").equals("bool")) {
                token = lexico.getNextToken();
                if (token.type == Token.SEMICOLON) {
                    return true;
                }
            } else if (token.type == Token.CHAR && identifiers.get("funcion").equals(("char"))) {
                token = lexico.getNextToken();
                if (token.type == Token.SEMICOLON) {
                    return true;
                }
            } else if (token.type == Token.IDENTIFIER) {
                for (String identifier : identifiers.keySet()) {
                    if (identifier.equals(token.token) && identifiers.get("funcion").equals(identifiers.get(token.token))) {
                        token = lexico.getNextToken();
                        if (token.type == Token.SEMICOLON) {
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args){
        AnalizadorSintactico sintactico = new AnalizadorSintactico("char funcion(int numero, bool verdad) {bool cosa = verdad; int otroNumero = numero; char caracter = 'F'; return 'F';}");
        System.out.println(sintactico.checkFunction());
    }
}
