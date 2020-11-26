import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class ForManyFSM {
    private static Map<String, ManyFSM> fsmFamilies = new HashMap<>();

    private static Properties priorities = new Properties();

    public static void loadFsmFamilies(List<String> paths) throws Exception {
        for(String path: paths) {
            String familyName = getFamilyName(path);
            FSM finiteStateMachine = loadFsm(path);
            if(fsmFamilies.containsKey(familyName)) {
                fsmFamilies.get(familyName).addFiniteStateMachine(finiteStateMachine);
            }
            else {
                List<FSM> finiteStateMachines = new ArrayList<>();
                finiteStateMachines.add(finiteStateMachine);
                fsmFamilies.put(familyName, new ManyFSM(finiteStateMachines, familyName));
            }
        }
    }

    public static ManyFSM findByName(String name) {
        return fsmFamilies.get(name);
    }

    public static List<ManyFSM> findByPriority(int priority) {
        List<ManyFSM> families = new ArrayList<>();
        for(Map.Entry<String, ManyFSM> familyEntry : fsmFamilies.entrySet()) {
            if(familyEntry.getValue().getPriority() == priority) {
                families.add(familyEntry.getValue());
            }
        }
        return families;
    }

    public static List<Lexeme> max(String input, int skip) {
        List<Lexeme> lexemes = new ArrayList<>();
        for(Map.Entry<String, ManyFSM> fsmFamilyEntry: fsmFamilies.entrySet()) {
            for(FSM fsm : fsmFamilyEntry.getValue().getFiniteStateMachines()) {
                Map.Entry<Boolean, Integer> mx = fsm.max(input, skip);
                if(mx.getKey()) {
                    lexemes.add(new Lexeme(skip, skip + mx.getValue(), fsmFamilyEntry.getKey()));
                }
            }
        }
        return lexemes;
    }

    private static int getPriorityByFamilyName(String familyName) {
        if(priorities.isEmpty()) initPriorities();
        return Integer.parseInt((String) priorities.getOrDefault(familyName, 0));
    }

    private static void initPriorities() {
        try {
            priorities = new Properties();
            priorities.load(
                    Thread
                            .currentThread()
                            .getContextClassLoader()
                            .getResourceAsStream("priorities.properties"));
        }
        catch(IOException e) {
            System.err.println("Fail to load lexeme priorities!");
        }
    }

    private static FSM loadFsm(String dir) throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(dir);
        assert is != null;
        String json = new BufferedReader(new InputStreamReader(is)).lines().collect(Collectors.joining("\n"));
        return JsonConverter.fromJson(json);
    }

    private static String getFamilyName(String path) {
        String tmp = path.substring(0, path.lastIndexOf("\\"));
        return tmp;
    }
}