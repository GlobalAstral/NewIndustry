package me.globalastral.newindustry.items.custom;

public enum SteelPurityLevel {
    CRUDE(.0060f, 1.20f),
    REFINED(.0045f, 1.00f),
    INDUSTRIAL(.0035f, .85f)
    ;

    SteelPurityLevel(float resistence, float base_resistence) {
        this.resistence_coefficient = resistence;
        this.base_resistence = base_resistence;
    }

    public final float resistence_coefficient;
    public final float base_resistence;

    @Override
    public String toString() {
        String str = super.toString().toLowerCase();
        return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
    }
}
