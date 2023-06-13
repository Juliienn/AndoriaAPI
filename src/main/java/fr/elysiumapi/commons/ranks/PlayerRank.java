package fr.elysiumapi.commons.ranks;

import java.sql.Timestamp;

public class PlayerRank {

    private ElysiumRanks rank;
    private Timestamp purchased_at;
    private Timestamp expires_at;

    public PlayerRank(ElysiumRanks rank, Timestamp purchased_at, Timestamp expires_at) {
        this.rank = rank;
        this.purchased_at = purchased_at;
        this.expires_at = expires_at;
    }

    public void setRank(ElysiumRanks rank) {
        this.rank = rank;
    }

    public void setPurchased_at(Timestamp purchased_at) {
        this.purchased_at = purchased_at;
    }

    public void setExpires_at(Timestamp expires_at) {
        this.expires_at = expires_at;
    }

    public ElysiumRanks getRank() {
        return rank;
    }

    public Timestamp getPurchased_at() {
        return purchased_at;
    }

    public Timestamp getExpires_at() {
        return expires_at;
    }
}
