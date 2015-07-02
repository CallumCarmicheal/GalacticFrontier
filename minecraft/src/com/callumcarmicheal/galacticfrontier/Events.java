package com.callumcarmicheal.galacticfrontier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Events {

	private int clientTick;
	
	private Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	public Events() {
		clientTick = 0;
	}
	
	// Called whenever the player is updated or ticked. 
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		
		
	}
	
	//Called when the client ticks.
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		
		/* REPLACE MAIN MENU */
		if(getMC().currentScreen instanceof GuiMainMenu) {
			getMC().currentScreen = new com.callumcarmicheal.galacticfrontier.gui.MainMenu();
		}
		
		/* AUTO UPDATE CAPES */
		
		boolean autoUpdateCapes = false;
		
		if(autoUpdateCapes){
			int capeUpdate = ((5 * 60) * 1000);
			
			//if((clientTick < 10000 && clientTick > 1000) || (clientTick > 11000 && clientTick < 11900) )
			gf().getLog().INFO("UPDATING TICK : " + clientTick + " : " + capeUpdate);
			
			if(clientTick > capeUpdate)
				clientTick = 0;
			 
			if(clientTick == 0) {
				gf().getLog().INFO("Refreashed Capes");
				Chat("Refreashed capes from server");
				gf().getCapeLInstance().refreashCapes();
			}
			
			clientTick++;
		}
	}
	 
	// Called when the server ticks. Usually 20 ticks a second. 
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {}
	 
	// Called when a new frame is displayed (See fps) 
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {}
	 
	// Called when the world ticks
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {}
	
	private GalacticFrontierMOD gf() {
		return GalacticFrontierMOD.getInstance();
	}
	
    private void Chat(String Message) {
    	if(Minecraft.getMinecraft().inGameHasFocus) {
	    	Minecraft.getMinecraft().
	    		thePlayer.addChatMessage(
	    			new ChatComponentText(
	    				EnumChatFormatting.GOLD + "[" + EnumChatFormatting.AQUA + "GF" + EnumChatFormatting.GOLD + "] " + Message
	    			)	
	    		);
    	}
    }
    
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
    	gf().getLog().INFO("GUI OPEN");
        if (event.gui instanceof GuiMainMenu) {
            event.gui = new com.callumcarmicheal.galacticfrontier.gui.MainMenu();
        }
    }
}
