package me.lordethan.cryton.module.modules;

import org.lwjgl.input.Keyboard;

import me.lordethan.cryton.Cryton;
import me.lordethan.cryton.module.Category;
import me.lordethan.cryton.module.Module;
import me.lordethan.cryton.utils.Wrapper;

public class Gui extends Module {

	public Gui() {
		super("Gui", Keyboard.KEY_UP, Category.GUI);
	}
	
	public void onToggle(){
		Wrapper.mc.displayGuiScreen(Cryton.theClient.getGui());
	}

}
