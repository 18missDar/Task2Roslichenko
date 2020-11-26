import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class MainApp {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private static final List<String> lexerPaths = Arrays.asList(
            "lexems\\assignment\\assignment.json",
            "lexems\\boolean\\false.json",
            "lexems\\keyword\\break.json",
            "lexems\\keyword\\let.json",
            "lexems\\keyword\\var.json",
            "lexems\\keyword\\while.json",
            "lexems\\keyword\\for.json",
            "lexems\\number\\number.json",
            "lexems\\operator\\and.json",
            "lexems\\operator\\closecurlybracket.json",
            "lexems\\operator\\closeroundbracket.json",
            "lexems\\operator\\div.json",
            "lexems\\operator\\g.json",
            "lexems\\operator\\le.json",
            "lexems\\operator\\minus.json",
            "lexems\\operator\\not.json",
            "lexems\\operator\\opencurlybracket.json",
            "lexems\\operator\\openroundbracket.json",
            "lexems\\operator\\semicolon.json",
            "lexems\\id\\id.json",
            "lexems\\whitespace\\n.json",
            "lexems\\whitespace\\r.json",
            "lexems\\whitespace\\t.json",
            "lexems\\whitespace\\whitespace.json"
    );

    public static void main(String[] args) throws IOException {
        try {
            ForManyFSM.loadFsmFamilies(lexerPaths);
            System.out.println("Введите путь к файлу:");
            String analyzePath = br.readLine();
            System.out.println("Файл:");
            String analyzeText = new String(Files.readAllBytes(Paths.get(analyzePath)));
            System.out.println(analyzeText);
            LexemeFinder lexemeFinder = new LexemeFinder(analyzeText);
            List<Lexeme> lexemes = lexemeFinder.findAllLexemes(0);
            lexemeFinder.printLexemes(lexemes);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}

//C:\RoslichenkoTask2\src\main\resources\lexemtest1.js
//C:\RoslichenkoTask2\src\main\resources\lexemtest2.js
//C:\RoslichenkoTask2\src\main\resources\lexemtest3.js