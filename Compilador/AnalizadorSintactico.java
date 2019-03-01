public class AnalizadorSintactico {
    AnalizadorLexico lexico;
    Token token;

    public AnalizadorSintactico(String input){
        lexico = new AnalizadorLexico(input);
        token = lexico.getNextToken();
    }

    public void validateSentence(){
        //if()
    }

    private boolean checkVariable(){
        if(token.type == Token.DATA_TYPE){
            token = lexico.getNextToken();
            if(token.type == Token.IDENTIFIER){
                token = lexico.getNextToken();
                if(checkAssignation()){
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

    private boolean checkAssignation(){
        if(token.type == Token.EQUALS){
            token = lexico.getNextToken();
            if(token.type == Token.IDENTIFIER){
                token = lexico.getNextToken();
                if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
            else if(token.type == Token.CHAR){
                token = lexico.getNextToken();
                if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
            else if(token.type == Token.NUMBER){
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
            token = lexico.getNextToken();
            if(token.type == Token.IDENTIFIER){
                token = lexico.getNextToken();
                if(token.type == Token.OPENPARENTHESES){
                    token = lexico.getNextToken();
                    while(token.type != Token.CLOSEDPARENTHESES){
                        if(token.type == Token.DATA_TYPE){
                            token = lexico.getNextToken();
                            if(token.type == Token.IDENTIFIER){
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

    private boolean checkCode(){
        while(token.type != Token.RETURN){
            if(!checkVariable()){
                return false;
            }
        }
        if(token.type == Token.RETURN){
            token = lexico.getNextToken();
            if(token.type == Token.NUMBER){
                token = lexico.getNextToken();
                if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
            else if(token.type == Token.BOOLEAN){
                token = lexico.getNextToken();
                if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
            else if(token.type == Token.CHAR){
                token = lexico.getNextToken();
                if (token.type == Token.SEMICOLON) {
                    return true;
                }
            }
            else if(token.type == Token.IDENTIFIER){
                token = lexico.getNextToken();
                if(token.type == Token.SEMICOLON){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args){
        AnalizadorSintactico sintactico = new AnalizadorSintactico("bool chingadera(int cola, bool puto) {int chingadera = 2+2; char chet = 'F'; return false;}");
        System.out.println(sintactico.checkFunction());
    }
}
