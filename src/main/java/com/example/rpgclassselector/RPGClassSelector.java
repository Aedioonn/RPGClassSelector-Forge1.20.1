package com.example.rpgclassselector;

import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(RPGClassSelector.MODID)
public class RPGClassSelector {
    public static final String MODID = "rpgclassselector";

    public RPGClassSelector() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        var player = event.getPlayer();
        var nbt = player.getPersistentData();
        if (!nbt.contains("rpg_role")) {
            player.sendSystemMessage(Component.literal("Vyber si roli pomocí /class <role> (např. /class Mag)."), player.getUUID());
        }
    }

    @SubscribeEvent
    public void onNameFormat(PlayerEvent.NameFormat event) {
        var player = event.getEntity();
        var nbt = player.getPersistentData();
        if (nbt.contains("rpg_role")) {
            String role = nbt.getString("rpg_role");
            String prefix = switch (role) {
                case "Mag" -> "[Mág] ";
                case "Zlodej" -> "[Zloděj] ";
                case "Hradobijec" -> "[Hradobijec] ";
                case "Drakobijec" -> "[Drakobijec] ";
                case "Kral" -> "[Král] ";
                case "Kralovna" -> "[Královna] ";
                default -> "";
            };
            event.setDisplayname(Component.literal(prefix + player.getName().getString()));
        }
    }
}
