package me.andrew.bookdupe.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ClientPlayerEntity.class })
public abstract class ClientPlayerEntityMixin {
    private static final MinecraftClient mc;
    
    @Inject(at = { @At("HEAD") }, method = { "sendChatMessage" }, cancellable = true)
    public void onChatMessage(final String message, final CallbackInfo ci) {
        if (message.equals("kekdupe")) {
            final ItemStack itemStack = new ItemStack((ItemConvertible)Items.WRITABLE_BOOK, 1);
            final ListTag pages = new ListTag();
            pages.addTag(0, (Tag)StringTag.of("DUPE"));
            itemStack.putSubTag("pages", (Tag)pages);
            itemStack.putSubTag("title", (Tag)StringTag.of("a"));
            ClientPlayerEntityMixin.mc.getNetworkHandler().sendPacket((Packet)new BookUpdateC2SPacket(itemStack, true, ClientPlayerEntityMixin.mc.player.inventory.selectedSlot));
            ci.cancel();
        }
    }
    
    static {
        mc = MinecraftClient.getInstance();
    }
}
