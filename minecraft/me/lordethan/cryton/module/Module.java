package me.lordethan.cryton.module;

import net.minecraft.client.Minecraft;

public class Module {

	private String name;
	private int bind;
	private Category category;
	private boolean isEnabled;

	public static Minecraft mc = Minecraft.getMinecraft();

	public Module(String name, int bind, Category category) {
		this.name = name;
		this.bind = bind;
		this.category = category;
	}
	
	public Module(String name ,Category category) {
		this.name = name;
		this.bind = 0;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public int getBind() {
		return bind;
	}

	public Category getCategory() {
		return category;
	}

	public boolean getState() {
		return isEnabled;
	}

	public void setState(boolean state) {
		this.onToggle();
		if (state) {
			this.onEnable();
			this.isEnabled = true;
		} else {
			this.onDisable();
			this.isEnabled = false;
		}
	}

	public void toggleModule() {
		this.setState(!this.getState());
	}

	public void onToggle() {
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public void onUpdate() {
	}

	public void onRender() {
	}
	

	public final boolean isCategory(Category s) {
		if (s == category)
			return true;
		return false;
	}

}
