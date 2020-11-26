import java.util.*;
import java.util.stream.Collectors;

public class LexemeFinder {

    private final String text;

    public LexemeFinder(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<Lexeme> findAllLexemes(int skip) {
        int i = skip;
        List<Lexeme> allLexemes = new ArrayList<>();
        while(i < text.length()) {
            List<Lexeme> lexemes = ForManyFSM.max(text, i);
            if(!lexemes.isEmpty()) {
                allLexemes.addAll(lexemes);
            }
            i++;
        }

        allLexemes = allLexemes
                .stream()
                .sorted(Comparator.comparingInt(Lexeme::getStartPos))
                .collect(Collectors.toList());

        return filterByPriority(allLexemes);
    }

    public void printLexemes(List<Lexeme> lexemes) {
        System.out.println("Найденные лексемы в файле");
        for(Lexeme lexeme : lexemes) {
            System.out.println("- " + text.substring(lexeme.getStartPos(), lexeme.getEndPos()) + " -");
            System.out.println(lexeme);
            System.out.println("_______________________________________________________________________________________________");
        }
    }

    private List<Lexeme> filterByPriority(List<Lexeme> lexemes) {
        List<Lexeme> filteredLexemes = new ArrayList<>(lexemes);

        for(int i = 0; i < lexemes.size(); i++) {
            for(int k = i + 1; k < lexemes.size(); k++) {
                if(i != k) {
                    Lexeme one = lexemes.get(i);
                    Lexeme sec = lexemes.get(k);
                    if(one.getStartPos() >= sec.getStartPos()
                            || one.getEndPos() == sec.getEndPos()) {
                        int firstLength = one.getEndPos() - one.getStartPos();
                        int secondLength = sec.getEndPos() - sec.getStartPos();
                        if(firstLength > secondLength) {
                            filteredLexemes.remove(sec);
                        }
                        else if(firstLength < secondLength) {
                            filteredLexemes.remove(one);
                        }
                        else {
                            int firstPriority = ForManyFSM.findByName(lexemes.get(i).getFamily()).getPriority();
                            int secondPriority = ForManyFSM.findByName(lexemes.get(k).getFamily()).getPriority();
                            if(firstPriority > secondPriority) {
                                filteredLexemes.remove(sec);
                            }
                            else {
                                filteredLexemes.remove(one);
                            }
                        }
                    }
                }
            }
        }
        return filteredLexemes;
    }
}