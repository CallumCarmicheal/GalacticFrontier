package com.callumcarmicheal.galacticfrontier;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.callumcarmicheal.capes.Capes;
import com.callumcarmicheal.capes.cape.CapeConfig;
import com.callumcarmicheal.capes.cape.CapeConfigManager;
import com.callumcarmicheal.galacticfrontier.input.KeyBindings;
import com.callumcarmicheal.galacticfrontier.input.KeyInputHandler;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = GalacticFrontierMOD.MODID, version = GalacticFrontierMOD.VERSION)

public class GalacticFrontierMOD {
    public static final String MODID = "GalacticFrontierMODPACK";
    public static final String VERSION = "0.5";
    private String capesJsonData;
    
    
    private static Logger logInstance;
    public static Logger getLog() { return logInstance; }
    private static GalacticFrontierMOD modInstance;
    private static com.callumcarmicheal.capes.load.Capes capeLInstance;
    private static Events eventsInstance;
    private static KeyInputHandler keyInstance;
    
    public GalacticFrontierMOD() {
    	
	}
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	logInstance = new Logger();
    	modInstance = new GalacticFrontierMOD();
    	capeLInstance = new com.callumcarmicheal.capes.load.Capes();
    	eventsInstance = new Events();
    	
    	KeyBindings.initKeyBinds();
    	keyInstance = new KeyInputHandler();
    	
    	if(event.getSide() == Side.CLIENT) {
    		getLog().INFO("Starting Clientside Capes");
    		getCapeLInstance().refreashCapes();
    		
    		FMLCommonHandler.instance().bus().register(eventsInstance);
    		FMLCommonHandler.instance().bus().register(keyInstance);
    	}
    		
    	//getLog().EXCEPTION(this.getClass().getName(), "init", e, "GF failed to grab capes list");
    }
    
    
    public String getCapesData() {
    	return this.capesJsonData;
    }
    
    public void setCapesData(String jsonData) {
    	this.capesJsonData = jsonData;
    }
    
    public static GalacticFrontierMOD getInstance() {
    	return modInstance;
    }
    
    public static com.callumcarmicheal.capes.load.Capes getCapeLInstance() {
    	return capeLInstance;
    }
    
}
