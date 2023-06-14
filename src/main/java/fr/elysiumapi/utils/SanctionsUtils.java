package fr.elysiumapi.utils;

import fr.elysiumapi.sanctions.infos.BanInfo;
import fr.elysiumapi.sanctions.infos.MuteInfo;

import java.util.HashMap;
import java.util.UUID;

public class SanctionsUtils {

    private static HashMap<UUID, BanInfo> banList;
    private static HashMap<UUID, MuteInfo> mutedList;

    public SanctionsUtils(){
        banList = new HashMap<>();
        mutedList = new HashMap<>();
    }

    public static HashMap<UUID, BanInfo> getBanList() {
        return banList;
    }

    public static HashMap<UUID, MuteInfo> getMutedList() {
        return mutedList;
    }
}