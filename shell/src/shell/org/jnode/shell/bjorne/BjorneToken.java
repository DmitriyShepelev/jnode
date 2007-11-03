package org.jnode.shell.bjorne;

import org.jnode.shell.CommandLine;
import org.jnode.shell.ShellException;

public class BjorneToken extends CommandLine.Token {

    public static final int TOK_END_OF_STREAM = -1;

    public static final int TOK_END_OF_LINE = -2;

    // See The Open Group Base Specifications Issue 6
    // IEEE Std 1003.1, 2004 Edition
    // Chapter 2 Section 2.10.2
    public static final int TOK_SEMI = ';';

    public static final int TOK_AMP = '&';

    public static final int TOK_BAR = '|';

    public static final int TOK_LESS = '<';

    public static final int TOK_GREAT = '>';

    public static final int TOK_LPAREN = '(';

    public static final int TOK_RPAREN = ')';

    public static final int TOK_LBRACE = '{'; // reserved word

    public static final int TOK_RBRACE = '}'; // reserved word

    public static final int TOK_BANG = '!'; // reserved word

    public static final int TOK_AND_IF = 128; // &&

    public static final int TOK_OR_IF = 129; // ||

    public static final int TOK_DSEMI = 130; // ;;

    public static final int TOK_DLESS = 131; // <<

    public static final int TOK_DGREAT = 132; // >>

    public static final int TOK_LESSAND = 133; // <&

    public static final int TOK_GREATAND = 134; // >&

    public static final int TOK_LESSGREAT = 135; // <>

    public static final int TOK_DLESSDASH = 136; // <<-

    public static final int TOK_CLOBBER = 137; // >|

    public static final int TOK_WORD = 138;

    public static final int TOK_NAME = 139;

    public static final int TOK_ASSIGNMENT = 140;

    public static final int TOK_IO_NUMBER = 141;

    public static final int TOK_IF = 142;

    public static final int TOK_THEN = 143;

    public static final int TOK_ELSE = 144;

    public static final int TOK_ELIF = 145;

    public static final int TOK_FI = 146;

    public static final int TOK_DO = 147;

    public static final int TOK_DONE = 148;

    public static final int TOK_CASE = 149;

    public static final int TOK_ESAC = 150;

    public static final int TOK_WHILE = 151;

    public static final int TOK_UNTIL = 152;

    public static final int TOK_FOR = 153;

    public static final int TOK_IN = 154;

    public static final int BASE_CONTEXT = 0;

    public static final int RULE_1_CONTEXT = 1;

    public static final int RULE_5_CONTEXT = 5;

    public static final int RULE_6_CONTEXT = 6;

    public static final int RULE_7a_CONTEXT = 7;

    public static final int RULE_7b_CONTEXT = 8;

    public static final int RULE_8_CONTEXT = 9;

    /*
     * We don't need / use use rules 2, 3 or 9 in a call to next(..) or peek(..)
     * because the tokenizer doesn't perform expansion or assignment.
     */

    public BjorneToken(final int tokenType, final String text) {
        super(text == null ? "" : text, tokenType, 0, 0);
        validate();
    }

    private void validate() {
        switch (tokenType) {
        case TOK_WORD:
        case TOK_IO_NUMBER:
        case TOK_NAME:
        case TOK_ASSIGNMENT:
            if (token.length() == 0) {
                throw new IllegalArgumentException("null or empty text");
            }
            break;

        default:
            if (token.length() > 0) {
                throw new IllegalArgumentException("non-empty text");
            }
        }
    }

    public BjorneToken(final int tokenType) {
        this(tokenType, "");
    }

    public String getInterpolatedText(BjorneContext context)
            throws ShellException {
        switch (tokenType) {
        case TOK_WORD:
        default:
            return toString();
        }
    }

    public String getText() {
        return token;
    }

    public int getTokenType() {
        return tokenType;
    }

    public boolean isName() {
        return token != null && isName(token);
    }

    public static boolean isName(String str) {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            switch (ch) {
            case 'A':
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'H':
            case 'I':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'S':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            case 'y':
            case 'z':
            case '_':
                break;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                if (i == 0) {
                    return false;
                }
                break;
            default:
                return false;
            }
        }
        return true;
    }

    public String toString() {
        switch (tokenType) {
        case TOK_WORD:
            return "WORD{" + token + "}";
        case TOK_NAME:
            return "NAME{" + token + "}";
        case TOK_ASSIGNMENT:
            return "ASSIGNMENT{" + token + "}";
        case TOK_IO_NUMBER:
            return "IO_NUMBER{" + token + "}";
        case TOK_SEMI:
        case TOK_AMP:
        case TOK_BAR:
        case TOK_LESS:
        case TOK_GREAT:
        case TOK_LPAREN:
        case TOK_RPAREN:
        case TOK_LBRACE:
        case TOK_RBRACE:
        case TOK_BANG:
            return "" + ((char) tokenType);
        case TOK_AND_IF:
            return "&&";
        case TOK_OR_IF:
            return "||";
        case TOK_DSEMI:
            return ";;";
        case TOK_DLESS:
            return "<<";
        case TOK_DGREAT:
            return ">>";
        case TOK_LESSAND:
            return "<&";
        case TOK_GREATAND:
            return ">&";
        case TOK_LESSGREAT:
            return "<>";
        case TOK_DLESSDASH:
            return "<<-";
        case TOK_CLOBBER:
            return "|>";
        case TOK_IF:
            return "if";
        case TOK_THEN:
            return "then";
        case TOK_ELSE:
            return "else";
        case TOK_ELIF:
            return "elif";
        case TOK_FI:
            return "fi";
        case TOK_DO:
            return "do";
        case TOK_DONE:
            return "done";
        case TOK_CASE:
            return "case";
        case TOK_ESAC:
            return "esac";
        case TOK_WHILE:
            return "while";
        case TOK_UNTIL:
            return "until";
        case TOK_FOR:
            return "for";
        case TOK_IN:
            return "in";
        case TOK_END_OF_LINE:
            return "?EOL?";
        case TOK_END_OF_STREAM:
            return "?EOS?";
        default:
            return "?UNKNOWN?";
        }
    }
}
