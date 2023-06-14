package fr.elysiumapi.sanctions.infos;

import java.sql.Timestamp;
import java.util.UUID;

public class MuteInfo{
    private final UUID uuid;
    private final String reason;
    private final String bannerName;
    private final Timestamp effectDate;
    private final Timestamp expireDate;

    public MuteInfo(UUID uuid, String reason, String bannerName, Timestamp effectDate, Timestamp expireDate) {
        this.uuid = uuid;
        this.reason = reason;
        this.bannerName = bannerName;
        this.effectDate = effectDate;
        this.expireDate = expireDate;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getReason() {
        return reason;
    }

    public String getBannerName() {
        return bannerName;
    }

    public Timestamp getEffectDate() {
        return effectDate;
    }

    public Timestamp getExpireDate() {
        return expireDate;
    }
}