package uk.to.and1558.Mods;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import uk.to.and1558.Gui.HUD.ScreenPosition;
import uk.to.and1558.Mods.ModLoader.ModDraggable;

public class ModComboCounter extends ModDraggable {
    public static boolean attacked = false;
    public static int combo = 0;

    public void onAttack(Entity e) {
        if (this.isEnabled()) {
            attacked = true;
        }
    }

    public void onEntityHit(S19PacketEntityStatus event) {
        if (this.isEnabled() && attacked && event.getOpCode() == 2) {
            combo++;
            attacked = false;
            lastHitTime = System.currentTimeMillis();
        }
    }

    @Override
    public int getWidth() {
        return font.getStringWidth("[NUL Combo]");
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }
    private long lastHitTime = 0L;
    private int discardTime = 3;
    @Override
    public void render(ScreenPosition pos) {
        if(mc.thePlayer.hurtTime > 3 && this.isEnabled){
            combo = 0;
        }
        if (System.currentTimeMillis() - lastHitTime >= discardTime * 1000L) {
            combo = 0;
        }
        font.drawString("["+combo+" Combo]", pos.getAbsoluteX(), pos.getAbsoluteY(), -1,true);
    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        font.drawString("[NUL Combo]", pos.getAbsoluteX(), pos.getAbsoluteY(), -1,true);
        super.renderDummy(pos);
    }
}
