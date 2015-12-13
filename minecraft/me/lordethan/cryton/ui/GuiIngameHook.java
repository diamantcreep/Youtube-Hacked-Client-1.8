package me.lordethan.cryton.ui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import me.lordethan.cryton.Cryton;
import me.lordethan.cryton.module.Category;
import me.lordethan.cryton.module.Module;
import me.lordethan.cryton.utils.RenderUtils;
import me.lordethan.cryton.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.MathHelper;

public class GuiIngameHook extends GuiIngame{

	public GuiIngameHook(Minecraft mcIn) {
		super(mcIn);
	}
	
	@Override
	public void func_175180_a(float p_175180_1_){
		super.func_175180_a(p_175180_1_);
		
		Wrapper.fu_default.drawString(Cryton.getClient_Name() + " Version:" + Cryton.getClient_Vesion(), 1, 1, 0xffffffff);
		renderArrayList();
		drawRadar();
		Cryton.theClient.getGuiManager().renderPinned();
	}
	
	private void renderArrayList(){
		int yCount = 15;
		for(Module m : Cryton.theClient.moduleManager.activeModules){
			m.onRender();
			
			if(m.getState() && !m.isCategory(Category.GUI)){
				Wrapper.fu_default.drawString(m.getName(), 5, yCount, 0x00ff7f);
				yCount +=10;
			}
		}
	}
	
	 int tick = 0;
	    public void drawRadar()
	    {
	        tick++ ;

	        if (tick >= 50)
	        {
	            tick = 0;
	        }
	       
	        GL11.glLineWidth(2.0F);
	        RenderUtils.drawFilledCircle(Minecraft.displayWidth - 60, 60, 50, 0x77007700);
	        RenderUtils.drawCircle(Minecraft.displayWidth - 60, 60, 50, 0xff000000);
	        RenderUtils.drawCircle(Minecraft.displayWidth - 60, 60, 38, 0xff000000);
	        RenderUtils.drawCircle(Minecraft.displayWidth - 60, 60, 25, 0xff000000);
	        RenderUtils.drawCircle(Minecraft.displayWidth - 60, 60, 13, 0xff000000);
	        RenderUtils.drawCircle(Minecraft.displayWidth - 60, 60, tick, 0xff00ffff);
	        RenderUtils.dr(Minecraft.displayWidth - 110, 59.5, Minecraft.displayWidth - 10, 60.5, 0xff00ffff);
	        RenderUtils.dr(Minecraft.displayWidth - 59.5, 10, Minecraft.displayWidth - 60.5, 110, 0xff00ffff);
	        RenderUtils.drawCircle(Minecraft.displayWidth - 60, 60, 1, 0xffffffff);    // Player
	        List list1 = Wrapper.mc.theWorld.loadedEntityList;
	        GL11.glLineWidth(1.0F);

	        for (int i = 0; i < list1.size(); i++)
	        {
	            Entity entity = (Entity) list1.get(i);
	            double xdis = Wrapper.mc.thePlayer.posX - entity.posX;
	            double zdis = Wrapper.mc.thePlayer.posZ - entity.posZ;
	            double tdis = Math.sqrt((xdis * xdis) + (zdis * zdis));
	            double difInAng = MathHelper.wrapAngleTo180_double(Wrapper.mc.thePlayer.rotationYaw - ((Math.atan2(zdis, xdis) * 180.0D) / Math.PI));
	            double finalX = Math.cos(Math.toRadians(difInAng)) * tdis;
	            double finalY = -Math.sin(Math.toRadians(difInAng)) * tdis;
	            GL11.glPushMatrix();
	            GL11.glTranslatef(Minecraft.displayWidth - 60, 60, 0);

	            if (tdis <= 100)
	            {
	                if (!(entity instanceof EntityPlayerMP))
	                {
	                    if (entity instanceof EntityPlayer)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xff0000ff);
	                        GL11.glScalef(0.5F, 0.5F, 0.5F);
	                        EntityPlayer p = (EntityPlayer) entity;
	                       // String u = p.username;
	                        //this.mc.fontRenderer.drawString(u, (int)(finalX) - (this.mc.fontRenderer.getStringWidth(u) / 2), (int) finalY - 10, 0xffffff);
	                        GL11.glScalef(1F, 0.5F, 1F);
	                    }

	                    if (entity instanceof EntityAnimal)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xff00ff00);
	                    }

	                    if (entity instanceof EntityMob)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xffff0000);
	                    }

	                    if (entity instanceof EntitySlime)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xffff88cc);
	                    }

	                    if (entity instanceof EntityVillager)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xff8b4513);
	                    }

	                    if (entity instanceof EntityBat)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xfff4a460);
	                    }

	                    if (entity instanceof EntitySquid)
	                    {
	                    	RenderUtils.drawCircle((int) finalX / 2, (int) finalY / 2, 1, 0xff003399);
	                    }
	                }
	            }

	            GL11.glPopMatrix();
	        }
	    }
	
	

}
