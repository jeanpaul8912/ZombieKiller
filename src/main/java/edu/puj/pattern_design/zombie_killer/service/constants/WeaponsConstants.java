package edu.puj.pattern_design.zombie_killer.service.constants;

public final class WeaponsConstants {

    private WeaponsConstants() {

    }

    //Weapons Type
    public static final String WEAPON_GRENADE_TYPE = "granada";

    public static final String WEAPON_M1911_TYPE = "m1911";

    public static final String WEAPON_REMINGTON_TYPE = "remington";

    public static final String WEAPON_KNIFE_TYPE = "cuchillo";


    //Levels to improve guns
    public static final int LEVELS_TO_IMPROVE_GUNS = 4;

    // Meterials
    public static final String LEAD = "Plomo";

    public static final String SILVER = "Plata";

    public static final String GOLD = "Oro";

    public static final String PLATINUM = "Platino";

    //Grenade
    public static final byte GRENADE_DAMAGE = 6;

    public static final int GRENADE_INITIAL_BULLETS = 2;

    public static final int GREANDE_INITIAL_RECHARGE_TIME = 200;

    //M1911
    public static final byte M1911_DAMAGE = 10;

    public static final int M1911_INITIAL_BULLETS = 8;

    public static final int M1911_INITIAL_RECHARGE_TIME = 1300;

    public static final int M1911_INITIAL_BACKWARD = 100;

    //Remington
    public static final byte REMINGTON_DAMAGE = 3;

    public static final int REMINGTON_INITIAL_BULLETS = 3;

    public static final int REMINGTON_INITIAL_RECHARGE_TIME = 1400;

    public static final int REMINGTON_INITIAL_BACKWARD = 400;

    public static final int REMINGTON_RANGE = 100;

    //Knife
    public static final byte KNIFE_DAMAGE = 4;

    //Improves
    public static final int IMPROVE_RECHARGE_TIME = 10;

    public static final int MINIMUM_RECHARGE_TIME = 150;

    //Status
    public static final String CARGANDO = "carga";

    public static final String LISTA = "ready";

}
