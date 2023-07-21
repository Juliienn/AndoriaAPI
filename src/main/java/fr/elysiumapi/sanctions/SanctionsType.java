package fr.elysiumapi.sanctions;

public enum SanctionsType {

    MUTE("mute"),
    BAN("ban");

    private final String tableName;

    SanctionsType(String tableName) {
        this.tableName = tableName;
    }

    public static SanctionsType fromName(String name){
        return name == "mute" ? MUTE : BAN;
    }

    public String getTableName() {
        return tableName;
    }
}
