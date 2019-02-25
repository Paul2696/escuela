public class Token {
    String token;
    int type;
    static final int DATA_TYPE = 0;
    static final int IDENTIFIER = 1;
    static final int ASSIGNATION = 2;
    static final int ARITHMETIC_OPERATOR = 3;
    static final int LOGICAL_OPERATOR = 4;
    static final int NUMBER = 5;
    static final int CHAR = 6;
    static final int BOOLEAN = 7;
}
