package net.vexio.anvilheads.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import net.minecraft.text.Text.Serializer;

/* code credited to github.com/onebeastchrist/customplayerheads */

public class TextureUtils {
    public static NbtCompound getNbtFromProfile(GameProfile profile) {
        // apply player's skin to head
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), profile));

        // set name of item to player's name
        NbtCompound displayTag = new NbtCompound();
        displayTag.putString("Name", profile.getName());

        nbtCompound.put("display", displayTag);

        // add lore to head
        NbtList loreList = new NbtList();
        NbtCompound display = nbtCompound.getCompound("display");
        loreList.add(NbtString.of(getJsonText("Squashed by Anvil")));

        display.put("Lore", loreList);
        nbtCompound.put("display", display);

        return nbtCompound;
    }

    private static String getJsonText(String string) {
        return Text.Serializer.toJson(Text.literal(string));
    }
}
