package me.andrew.bookdupe.mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.ListTag;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BookUpdateC2SPacket.class })
public abstract class BookUpdateC2SPacketMixin {
    private static final String str1;
    private static final String str2 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    @Shadow
    private ItemStack book;
    
    @Inject(at = { @At("RETURN") }, method = { "<init>(Lnet/minecraft/item/ItemStack;ZI)V" })
    public void onInit(final ItemStack book, final boolean signed, final int slot, final CallbackInfo ci) {
        System.out.println("CALLED");
        if (signed && book.getTag().getList("pages", 8).getString(0).equals("DUPE")) {
            final ListTag listTag = new ListTag();
            listTag.addTag(0, (Tag)StringTag.of(BookUpdateC2SPacketMixin.str1));
            for (int i = 1; i < 38; ++i) {
                listTag.addTag(i, (Tag)StringTag.of("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
            }
            this.book.putSubTag("pages", (Tag)listTag);
        }
    }
    
    static {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 21845; ++i) {
            stringBuilder.append('ࠀ');
        }
        str1 = stringBuilder.toString();
    }
}
