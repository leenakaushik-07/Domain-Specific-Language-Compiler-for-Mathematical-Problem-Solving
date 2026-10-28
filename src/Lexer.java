import java.util.*;

public class Lexer {

    private static final Set<String> KEYWORDS = Set.of("solve", "where", "find");
    private static final Set<String> OPERATORS = Set.of("=");
    private static final Set<String> VALID_SYMBOLS = Set.of(",");

    private String text;

    public Lexer(String text) {
        this.text = text;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        String[] lines = text.split("\n");

        for (int i = 0; i < lines.length; i++) {
            int lineNumber = i + 1;
            String line = lines[i];

            String[] words = line.replace(",", " , ").split("\\s+");

            for (String word : words) {
                if (word.isEmpty()) continue;

                if (KEYWORDS.contains(word)) {
                    tokens.add(new Token("KEYWORD", word, lineNumber));

                } else if (OPERATORS.contains(word)) {
                    tokens.add(new Token("OPERATOR", word, lineNumber));

                } else if (VALID_SYMBOLS.contains(word)) {
                    tokens.add(new Token("COMMA", word, lineNumber));

                } else if (word.matches("\\d+")) {
                    tokens.add(new Token("NUMBER", word, lineNumber));

                } else if (word.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                    tokens.add(new Token("IDENTIFIER", word, lineNumber));

                } else {
                    throw new RuntimeException(
                        "Lexical Error: Invalid token '" + word + "' at line " + lineNumber
                    );
                }
            }
        }

        return tokens;
    }
}