package fr.elysiumapi.sanctions.infos;

import fr.elysiumapi.sanctions.SanctionsType;
import fr.elysiumapi.utils.DurationUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class SanctionsInfo {

    private final SanctionsType sanction;
    private final UUID uuid;
    private final String reason;
    private final String bannerName;
    private final Timestamp effectDate;
    private final Timestamp expireDate;

    public SanctionsInfo(SanctionsType sanction, UUID uuid, String reason, String bannerName, Timestamp effectDate, Timestamp expireDate) {
        this.sanction = sanction;
        this.uuid = uuid;
        this.reason = reason;
        this.bannerName = bannerName;
        this.effectDate = effectDate;
        this.expireDate = expireDate;
    }

    public boolean isPermanently(){
        return Objects.equals(this.expireDate, new Timestamp(DurationUtils.TIMESTAMP_LIMIT));
    }

    public SanctionsType getSanction() {
        return sanction;
    }

    public UUID getUuid() {
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
