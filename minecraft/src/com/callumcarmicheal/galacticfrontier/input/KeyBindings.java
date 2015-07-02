package com.callumcarmicheal.galacticfrontier.input;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings {

    public static KeyBinding keyRefreashCapes;
    public static KeyBinding keyRemoveCape;

    public static void initKeyBinds() {
        keyRefreashCapes = new KeyBinding("refreash Capes", Keyboard.KEY_C, "GalacicFrontier Capes");
        keyRemoveCape = new KeyBinding("remove Cape", Keyboard.KEY_V, "GalacicFrontier Capes");
        ClientRegistry.registerKeyBinding(keyRefreashCapes);
        ClientRegistry.registerKeyBinding(keyRemoveCape);
    }

}