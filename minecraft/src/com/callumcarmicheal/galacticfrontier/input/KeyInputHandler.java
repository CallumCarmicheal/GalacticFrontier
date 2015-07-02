package com.callumcarmicheal.galacticfrontier.input;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.callumcarmicheal.capes.user.User;
import com.callumcarmicheal.capes.user.UserManager;
import com.callumcarmicheal.galacticfrontier.GalacticFrontierMOD;
import com.callumcarmicheal.galacticfrontier.Logger;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
    	
    	if(Minecraft.getMinecraft().inGameHasFocus) { 
    		
	        if(KeyBindings.keyRefreashCapes.isPressed()) {
	        	GalacticFrontierMOD.getCapeLInstance().refreashCapes();
	        	Chat("Refreashed capes from server");
	        } 
	        if(KeyBindings.keyRemoveCape.isPressed()) {
	        	String Username = Minecraft.getMinecraft().thePlayer.getCommandSenderName();
	        	
	        	if(UserManager.INSTANCE.validUser(Username)) {
		        	UserManager.INSTANCE.removeUser(UserManager.INSTANCE.getUser(Username));
		        	getLog().INFO("Removed USER : " + Minecraft.getMinecraft().thePlayer.getCommandSenderName());
		        	Chat("Cape has been unloaded, please relog.");
	        	} else {
	        		getLog().INFO("Invalid User : No user found with name -> " + Username);
	        		Chat("Cape is already unloaded (no ref found)");
	        	}
	        }
        }
    }
    
    private void Chat(String Message) {
    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(
    			EnumChatFormatting.GOLD + "[" + EnumChatFormatting.AQUA + "GF" + EnumChatFormatting.GOLD + "] " + Message));
    }
    
	public Logger getLog() { return GalacticFrontierMOD.getLog(); }

}