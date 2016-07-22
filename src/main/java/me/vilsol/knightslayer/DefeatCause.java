package me.vilsol.knightslayer;

public enum  DefeatCause {

    ATTACK, ARMOR, AGILITY, ENDURANCE, BOAT, STORM, BALANCED;

    public static DefeatCause getCause(String message) {
        for (DefeatCause cause : values()) {
            if(message.toLowerCase().contains(cause.name().toLowerCase())){
                return cause;
            }
        }

        return null;
    }

}
