package fr.andoriaapi.commons.server;

public enum ServerState {

    STARTING(false),
    REDIS_UPLOADING(false),
    WORKING(true);

    final boolean canJoin;
    private static ServerState serverState;

    ServerState(boolean canJoin){
        this.canJoin = canJoin;
    }

    public static ServerState getServerState(){
        return serverState;
    }

    public static void setServerState(ServerState serverState){
        ServerState.serverState = serverState;
    }

    public boolean canJoin() {
        return canJoin;
    }
}
