package com.ordwen.odailyquests.commands.interfaces;

import com.ordwen.odailyquests.commands.interfaces.pagination.Items;
import com.ordwen.odailyquests.files.ConfigurationFiles;
import com.ordwen.odailyquests.quests.LoadQuests;
import com.ordwen.odailyquests.quests.Quest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategorizedQuestsInterfaces {

    /**
     * Getting instance of classes.
     */
    private final ConfigurationFiles configurationFiles;

    /**
     * Class instance constructor.
     * @param configurationFiles configuration files class.
     */
    public CategorizedQuestsInterfaces(ConfigurationFiles configurationFiles) {
        this.configurationFiles = configurationFiles;
    }

    /* Logger for stacktrace */
    private final Logger logger = PluginLogger.getLogger("O'DailyQuests");

    /* init items */
    float invSize = 45;
    List<Inventory> easyQuestsInventories = new ArrayList<>();
    List<Inventory> mediumQuestsInventories = new ArrayList<>();
    List<Inventory> hardQuestsInventories = new ArrayList<>();

    /**
     * Load all categorized interfaces.
     */
    public void loadCategorizedInterfaces() {
        /* Easy quests inventory */
        ItemStack emptyCaseItem = new ItemStack(Material.valueOf(configurationFiles.getConfigFile().getConfigurationSection("interfaces.easy_quests").getString(".empty_item")));
        int neededInventories = (int) Math.ceil(LoadQuests.getEasyQuests().size() / invSize);
        loadSelectedInterface(InterfacesManager.getEasyQuestsInventoryName(), emptyCaseItem, neededInventories, easyQuestsInventories, LoadQuests.getEasyQuests());

        /* Medium quests inventory */
        emptyCaseItem = new ItemStack(Material.valueOf(configurationFiles.getConfigFile().getConfigurationSection("interfaces.medium_quests").getString(".empty_item")));
        neededInventories = (int) Math.ceil(LoadQuests.getMediumQuests().size() / invSize);
        loadSelectedInterface(InterfacesManager.getMediumQuestsInventoryName(), emptyCaseItem, neededInventories, mediumQuestsInventories, LoadQuests.getMediumQuests());

        /* Hard quests inventory */
        emptyCaseItem = new ItemStack(Material.valueOf(configurationFiles.getConfigFile().getConfigurationSection("interfaces.hard_quests").getString(".empty_item")));
        neededInventories = (int) Math.ceil(LoadQuests.getHardQuests().size() / invSize);
        loadSelectedInterface(InterfacesManager.getHardQuestsInventoryName(), emptyCaseItem, neededInventories, hardQuestsInventories, LoadQuests.getHardQuests());
    }

    /**
     * Load specified interface.
     * @param inventoryName name of interface.
     * @param emptyCaseItem item for empty-cases.
     * @param quests list of quests.
     */
    public void loadSelectedInterface(String inventoryName, ItemStack emptyCaseItem, int neededInventories, List<Inventory> questsInventories, ArrayList<Quest> quests) {

        boolean allQuestsLoaded = false;
        int currentQuestIndex = 0;

        for (int i = 0; i < neededInventories; i++) {
            Inventory inv = Bukkit.createInventory(null, 54, inventoryName + " - " + (i + 1));
            if (i > 0) {
                inv.setItem(45, Items.getPreviousButton());
            }
            if (i < neededInventories - 1) {
                inv.setItem(53, Items.getNextButton());
            }
            questsInventories.add(inv);
        }

        for (Inventory inv : questsInventories) {
            int i = 0;

            /* add quests items on slots */
            while (i < invSize && !allQuestsLoaded) {
                if (currentQuestIndex < quests.size()) {
                    Quest quest = quests.get(currentQuestIndex);

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

            /* fill empty slots */
            for (int j = 0; j < inv.getSize(); j++) {
                if (inv.getItem(j) == null) inv.setItem(j, emptyCaseItem);
            }
        }
        logger.info(ChatColor.GREEN + "Categorized quests interfaces successfully loaded.");
    }

    /**
     * Get easy quests inventories.
     * @return easy quests inventories.
     */
    public List<Inventory> getEasyQuestsInventories() {
        return easyQuestsInventories;
    }

    /**
     * Get medium quests inventories.
     * @return medium quests inventories.
     */
    public List<Inventory> getMediumQuestsInventories() {
        return mediumQuestsInventories;
    }

    /**
     * Get hard quests inventories.
     * @return hard quests inventories.
     */
    public List<Inventory> getHardQuestsInventories() {
        return hardQuestsInventories;
    }

    /**
     * Get easy quests inventory first page.
     * @return easy quests inventory first page.
     */
    public Inventory getEasyQuestsInterfaceFirstPage() {
        return easyQuestsInventories.get(0);
    }

    /**
     * Get medium quests inventory first page.
     * @return medium quests inventory first page.
     */
    public Inventory getMediumQuestsInterfaceFirstPage() {
        return mediumQuestsInventories.get(0);
    }

    /**
     * Get hard quests inventory first page.
     * @return hard quests inventory first page.
     */
    public Inventory getHardQuestsInterfaceFirstPage() {
        return hardQuestsInventories.get(0);
    }

    /**
     * Get quests inventory next page.
     * @return quests inventory next page.
     */
    public Inventory getInterfaceNextPage(List<Inventory> inventories, int page) {
        return inventories.get(page);
    }

    /**
     * Get quests inventory previous page.
     * @return quests inventory previous page.
     */
    public Inventory getInterfacePreviousPage(List<Inventory> inventories, int page) {
        return inventories.get(page - 2);
    }
}
