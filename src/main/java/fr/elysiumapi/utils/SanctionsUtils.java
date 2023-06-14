package fr.elysiumapi.utils;

import fr.elysiumapi.sanctions.BanManager;
import fr.elysiumapi.sanctions.MuteManager;

import java.util.HashMap;
import java.util.UUID;

public class SanctionsUtils {

    private static HashMap<UUID, BanManager.BanInfo> banList;
    private static HashMap<UUID, MuteManager.MuteInfo> mutedList;

    public SanctionsUtils(){
        banList = new HashMap<>();
        mutedList = new HashMap<>();
    }

    public static HashMap<UUID, BanManager.BanInfo> getBanList() {
        return banList;
    }

    public static HashMap<UUID, MuteManager.MuteInfo> getMutedList() {
        return mutedList;
    }
}