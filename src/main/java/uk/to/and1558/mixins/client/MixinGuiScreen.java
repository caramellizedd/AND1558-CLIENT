package uk.to.and1558.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.to.and1558.Gui.impl.GuiUtils;
import uk.to.and1558.Plugins.ClientAnimations.Animation;
import uk.to.and1558.Plugins.ClientAnimations.Easing;
import uk.to.and1558.Plugins.ShaderLoader;

// dev 1.82 ->Handles animation for Boxed GUI
@Mixin(GuiScreen.class)
public class MixinGuiScreen {
    Minecraft mc = Minecraft.getMinecraft();
    boolean playMediumBoxAnim = false;
    boolean playBoxAnim = false;
    @Inject(method = "initGui", at = @At("HEAD"))
    private void initGuiAnim(CallbackInfo ci){
        if(mc.currentScreen != null) {
            // Play the animation for the Medium Sized Box for GUI Background
            if (!playMediumBoxAnim) {
                GuiUtils.boxSizeXM = new Animation(500l, 0, 150, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeYM = new Animation(500l, 0, 50, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeX1M = new Animation(500l, 0, mc.currentScreen.width - 150, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeY1M = new Animation(500l, 0, mc.currentScreen.height - 50, Easing.EASE_OUT_QUINT);
                playMediumBoxAnim = true;
            } else {
                GuiUtils.boxSizeXM = new Animation(0, 0, 150, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeYM = new Animation(0, 0, 50, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeX1M = new Animation(0, 0, mc.currentScreen.width - 150, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeY1M = new Animation(0, 0, mc.currentScreen.height - 50, Easing.EASE_OUT_QUINT);
            }
            // Play the animation for the Normal Sized Box (the biggest) for GUI Background
            if (!playBoxAnim) {
                GuiUtils.boxSizeX = new Animation(500l, 0, 10, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeY = new Animation(500l, 0, 10, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeX1 = new Animation(500l, 0, mc.currentScreen.width - 10, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeY1 = new Animation(500l, 0, mc.currentScreen.height - 10, Easing.EASE_OUT_QUINT);
                playBoxAnim = true;
            } else {
                GuiUtils.boxSizeX = new Animation(0, 0, 10, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeY = new Animation(0, 0, 10, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeX1 = new Animation(0, 0, mc.currentScreen.width - 10, Easing.EASE_OUT_QUINT);
                GuiUtils.boxSizeY1 = new Animation(0, 0, mc.currentScreen.height - 10, Easing.EASE_OUT_QUINT);
            }
        }
    }
    @Inject(method = "onGuiClosed", at = @At("HEAD"))
    private void resetGuiAnim(CallbackInfo ci){
        playMediumBoxAnim = false;
        playBoxAnim = false;
    }
    @Inject(method = "drawDefaultBackground", at = @At("HEAD"))
    private void blurBG(CallbackInfo ci){
        ShaderLoader.blurBackground();
    }
}
