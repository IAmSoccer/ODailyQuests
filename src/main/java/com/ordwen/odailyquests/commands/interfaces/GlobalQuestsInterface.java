package com.ordwen.odailyquests.commands.interfaces;

import com.ordwen.odailyquests.commands.interfaces.pagination.Items;
import com.ordwen.odailyquests.files.ConfigurationFiles;
import com.ordwen.odailyquests.quests.LoadQuests;
import com.ordwen.odailyquests.quests.Quest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GlobalQuestsInterface {

    /**
     * Getting instance of classes.
     */
    private final ConfigurationFiles configurationFiles;

    /**
     * Class instance constructor.
     *
     * @param configurationFiles configuration files class.
     */
    public GlobalQuestsInterface(ConfigurationFiles configurationFiles) {
        this.configurationFiles = configurationFiles;
    }

    /* Logger for stacktrace */
    private final Logger logger = PluginLogger.getLogger("O'DailyQuests");

    /* init items */
    //private Inventory globalQuestsInventory;

    List<Inventory> inventories = new ArrayList<>();
    float invSize = 45;

    /**
     * Load global quests interface.
     */
    public void loadGlobalQuestsInterface() {

        int neededInventories = (int) Math.ceil(LoadQuests.getGlobalQuests().size() / invSize);
        for (int i = 0; i < neededInventories; i++) {
            Inventory inv = Bukkit.createInventory(null, 54, InterfacesManager.getGlobalQuestsInventoryName() + " - " + (i + 1));

            if (i > 0) {
                inv.setItem(45, Items.getPreviousButton());
            }
            if (i < neededInventories - 1) {
                inv.setItem(53, Items.getNextButton());
            }

            inventories.add(inv);
        }

        for (Inventory inv : inventories) {
            int i = 0;
            boolean allQuestsLoaded = false;
            int currentQuestIndex = 0;

            while (i < invSize && !allQuestsLoaded) {

                if (currentQuestIndex < LoadQuests.getGlobalQuests().size()) {
                    Quest quest = LoadQuests.getGlobalQuests().get(currentQuestIndex);

                    ItemStack itemStack = quest.getMenuItem();
                    ItemMeta itemMeta = itemStack.getItemMeta();

                    assert itemMeta != null;
                    itemMeta.setDisplayName(quest.getQuestName());
                    itemMeta.setLore(quest.getQuestDesc());
                    itemStack.setItemMeta(itemMeta);

                    inv.setItem(i, itemStack);
                    i++;
                    currentQuestIndex++;
                } else {
                    allQuestsLoaded = true;
                }
            }
        }
        logger.info(ChatColor.GREEN + "Global quests interface successfully loaded.");
    }

    /**
     * Get global quests inventory first page.
     * @return global quests inventory first page.
     */
    public Inventory getGlobalQuestsInterfaceFirstPage() {
        return inventories.get(0);
    }

    /**
     * Get global quests inventory next page.
     * @return global quests inventory next page.
     */
    public Inventory getGlobalQuestsNextPage(int page) {
        return inventories.get(page);
    }

    /**
     * Get global quests inventory previous page.
     * @return global quests inventory previous page.
     */
    public Inventory getGlobalQuestsPreviousPage(int page) {
        return inventories.get(page-2);
    }
}
