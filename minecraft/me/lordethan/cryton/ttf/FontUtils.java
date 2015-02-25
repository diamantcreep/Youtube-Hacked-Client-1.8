package me.lordethan.cryton.ttf;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.StringUtils;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontUtils {
	private Minecraft mc = Minecraft.getMinecraft();

	private final UnicodeFont unicodeFont;
	private final int[] colorCodes = new int[32];

	public FontUtils(String fontName, int fontType, int size) {
		this.unicodeFont = new UnicodeFont(new Font(fontName, fontType, size));

		try {
			this.unicodeFont.addAsciiGlyphs();
			this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
			this.unicodeFont.loadGlyphs();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < 32; i++) {
			int shadow = (i >> 3 & 1) * 85;
			int red = (i >> 2 & 1) * 170 + shadow;
			int green = (i >> 1 & 1) * 170 + shadow;
			int blue = (i >> 0 & 1) * 170 + shadow;

			if (i == 6) {
				red += 85;
			}

			if (i >= 16) {
				red /= 4;
				green /= 4;
				blue /= 4;
			}

			this.colorCodes[i] = (red & 255) << 16 | (green & 255) << 8 | blue & 255;
		}

	}

	public void drawString(String text, float x, float y, int color) {
		x *= 2.0f;
		y *= 2.0f;
		float originalX = x;

		glPushMatrix();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glDisable(GL_TEXTURE_2D);
		glScalef(0.5f, 0.5f, 0.5f);

		int currentColor = color;
		char[] characters = text.toCharArray();

		int index = 0;
		for (char c : characters) {
			if (c == '\r') {
				x = originalX;
			}
			if (c == '\n') {
				y += getHeight(Character.toString(c)) * 2.0f;
			}
			if (c != '\247' && (index == 0 || index == characters.length - 1 || characters[index - 1] != '\247')) {
				unicodeFont.drawString(x, y, Character.toString(c), new org.newdawn.slick.Color(currentColor));
				x += getWidth(Character.toString(c)) * 2.0f;
			} else if (c == ' ') {
				x += unicodeFont.getSpaceWidth();
			} else if (c == '\247' && index != characters.length - 1) {
				int codeIndex = "0123456789abcdef".indexOf(text.charAt(index + 1));
				if (codeIndex < 0)
					continue;
				int col = this.colorCodes[codeIndex];
				currentColor = col;
			}
			index++;
		}

		glScalef(2.0f, 2.0f, 2.0f);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		glPopMatrix();

	}

	public void drawStringWidthShadow(String text, float x, float y, int color) {
		drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, 0x00);
		drawString(text, x, y, color);
	}

	public void drawCenteredString(String text, float x, float y, int color) {
		drawString(text, x / 2.0f - getWidth(text) / 2.0f, y, color);
	}
	
	public void drawCenterdStringWidthShadow(String text,float x, float y,int color){
		drawString(StringUtils.stripControlCodes(text), x + 0.5f, y + 0.5f, color);
		drawString(text, x, y, color);
	}

	public float getWidth(String s) {
		float width = 0.0f;

		String s1 = StringUtils.stripControlCodes(s);
		for (char c : s1.toCharArray()) {
			width += unicodeFont.getWidth(Character.toString(c));
		}

		return width / 2.0f;
	}

	public float getHeight(String s) {
		return unicodeFont.getHeight(s) / 2.0f;
	}

	public UnicodeFont getFont() {
		return this.unicodeFont;
	}
}
