package fr.andoriaapi.commons.server;

public enum ServerType {

    LOBBY,
    FACTION,
    MINAGE,
    FARM,
    MINIGAME;

    private static ServerType serverType;

    public static void setServerType(ServerType serverType){
        ServerType.serverType = serverType;
    }

    public static ServerType getServerType(){
        return ServerType.serverType;
    }
}
