package io.github.xpeteliu.match;

import java.util.HashMap;
import java.util.Map;

public class MatchServiceFactory {

    private static final Map<MatchStrategy, MatchService> matchServiceMap = new HashMap<>();

    public static void addMatchService(MatchStrategy name, MatchService matchService) {
        matchServiceMap.put(name, matchService);
    }

    public static MatchService getMatchService(MatchStrategy name) {
        return matchServiceMap.get(name);
    }
}
