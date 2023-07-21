package fr.andoriaapi.commons.ranks;

import java.sql.Timestamp;

public class PlayerRank {

    private AndoriaRanks rank;
    private Timestamp purchasedDate;
    private Timestamp expirationDate;

    public PlayerRank(AndoriaRanks rank, Timestamp purchasedDate, Timestamp expirationDate) {
        this.rank = rank;
        this.purchasedDate = purchasedDate;
        this.expirationDate = expirationDate;
    }

    public void setRank(AndoriaRanks rank) {
        this.rank = rank;
    }

    public void setPurchased_at(Timestamp purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public AndoriaRanks getRank() {
        return rank;
    }

    public Timestamp getPurchasedDate() {
        return purchasedDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }
}
