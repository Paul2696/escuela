public class Token {
    String token;
    int type;
    static final int DATA_TYPE = 0;
    static final int IDENTIFIER = 1;
    static final int EQUALS = 2;
    static final int ARITHMETIC_OPERATOR = 3;
    static final int LOGICAL_OPERATOR = 4;
    static final int NUMBER = 5;
    static final int CHAR = 6;
    static final int BOOLEAN = 7;
    static final int SEMICOLON = 8;
    static final int OPENPARENTHESES = 9;
    static final int CLOSEDPARENTHESES = 10;
    static final int OPENBRACES = 11;
    static final int CLOSEDBRACES = 12;
    static final int COMMA = 13;
    static final int RETURN = 14;

    public Token(String token, int type){
        this.token = token;
        this.type = type;
    }

    public String toString(){
        return "{token: " + token + ", type: " + type + "}";
    }

}
