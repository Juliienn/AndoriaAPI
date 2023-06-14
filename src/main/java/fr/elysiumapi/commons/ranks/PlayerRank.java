package fr.elysiumapi.commons.ranks;

import java.sql.Timestamp;

public class PlayerRank {

    private ElysiumRanks rank;
    private Timestamp purchasedDate;
    private Timestamp expirationDate;

    public PlayerRank(ElysiumRanks rank, Timestamp purchasedDate, Timestamp expirationDate) {
        this.rank = rank;
        this.purchasedDate = purchasedDate;
        this.expirationDate = expirationDate;
    }

    public void setRank(ElysiumRanks rank) {
        this.rank = rank;
    }

    public void setPurchased_at(Timestamp purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ElysiumRanks getRank() {
        return rank;
    }

    public Timestamp getPurchasedDate() {
        return purchasedDate;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }
}
