import com.google.gson.*;

import java.util.*;

public class JsonConverter {

    private static Gson gson = new GsonBuilder().create();

    public static FSM fromJson(String json) {
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonArray start = jsonObject.get("start").getAsJsonArray();
        Set<String> startStates = new HashSet<>();
        for(JsonElement s : start) {
            startStates.add(s.getAsString());
        }
        JsonArray finish = jsonObject.get("finish").getAsJsonArray();
        boolean isRegExp;
        try {
            isRegExp = jsonObject.get("isRegExp").getAsBoolean();
        }
        catch(Exception e) {
            isRegExp = false;
        }
        Set<String> finishStates = new HashSet<>();
        for(JsonElement f : finish) {
            finishStates.add(f.getAsString());
        }
        Map<PairforFSM, String> states = new HashMap<>();
        JsonObject matrix = jsonObject.getAsJsonObject("matrix");
        for(Map.Entry<String, JsonElement> entry : matrix.entrySet()) {
            JsonObject step = entry.getValue().getAsJsonObject();
            for(Map.Entry<String, JsonElement> stepEntry : step.entrySet()) {
                JsonArray st = stepEntry.getValue().getAsJsonArray();
                for(JsonElement el : st) {
                    states.put(new PairforFSM(stepEntry.getKey(), entry.getKey()), el.getAsString());
                }
            }
        }

        return new FSM(states, startStates, finishStates, isRegExp);
    }
}