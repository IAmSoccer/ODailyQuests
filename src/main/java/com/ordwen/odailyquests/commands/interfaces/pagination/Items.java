package com.ordwen.odailyquests.commands.interfaces.pagination;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.ordwen.odailyquests.files.ConfigurationFiles;
import com.ordwen.odailyquests.tools.ColorConvert;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class Items {

    /* instance */
    private static ConfigurationFiles configurationFiles;

    /**
     * Constructor.
     *
     * @param configurationFiles configuration files class.
     */
    public Items(ConfigurationFiles configurationFiles) {
        Items.configurationFiles = configurationFiles;
    }

    /* init items */
    private static ItemStack previous;
    private static ItemStack next;
    private static final HashSet<ItemStack> paginationItems = new HashSet<>();


    /**
     * Load all items.
     */
    public void initItems() {
        paginationItems.clear();

        initPreviousButton();
        initNextButton();
    }

    /**
     * Init previous button.
     */
    private void initPreviousButton() {
        previous = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta previousMeta = (SkullMeta) previous.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);

        String url = "http://textures.minecraft.net/texture/a2f0425d64fdc8992928d608109810c1251fe243d60d175bed427c651cbe";
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(data)));

        Field profileField;
        try {
            profileField = previousMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(previousMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        previousMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ColorConvert.convertColorCode(configurationFiles.getConfigFile().getConfigurationSection("interfaces").getString(".previous_item_name"))));
        previous.setItemMeta(previousMeta);

        paginationItems.add(previous);
    }

    /**
     * Init next button.
     */
    private void initNextButton() {
        next = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta nextMeta = (SkullMeta) next.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);

        String url = "http://textures.minecraft.net/texture/6d865aae2746a9b8e9a4fe629fb08d18d0a9251e5ccbe5fa7051f53eab9b94";
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());

        gameProfile.getProperties().put("textures", new Property("textures", new String(data)));

        Field profileField;
        try {
            profileField = nextMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(nextMeta, gameProfile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        nextMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', ColorConvert.convertColorCode(configurationFiles.getConfigFile().getConfigurationSection("interfaces").getString(".next_item_name"))));
        next.setItemMeta(nextMeta);

        paginationItems.add(next);
    }

    /**
     * Get previous button.
     *
     * @return previous button.
     */
    public static ItemStack getPreviousButton() {
        return previous;
    }

    /**
     * Get next button.
     *
     * @return next button.
     */
    public static ItemStack getNextButton() {
        return next;
    }

    /**
     * Get pagination items.
     * @return set of pagination items
     */
    public static HashSet<ItemStack> getPaginationItems() {
        return paginationItems;
    }
}
