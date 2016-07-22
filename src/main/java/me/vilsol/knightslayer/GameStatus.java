package me.vilsol.knightslayer;

public enum GameStatus {

    VICTORY, DEFEAT;

    public static GameStatus getStatus(String message){
        if(message.equalsIgnoreCase("victory")){
            return VICTORY;
        }

        return DEFEAT;
    }

}
