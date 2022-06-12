package com.ordwen.odailyquests.quests.player;

import com.ordwen.odailyquests.quests.Quest;
import com.ordwen.odailyquests.quests.player.progression.Progression;
import com.ordwen.odailyquests.configuration.functionalities.GlobalReward;

import java.util.LinkedHashMap;

public class PlayerQuests {

    /* Timestamp of last quests renew */
    private final Long timestamp;

    private int achievedQuests;

    /* Quest active quest, Boolean quest status */
    private final LinkedHashMap<Quest, Progression> playerQuests;

    public PlayerQuests(Long timestamp, LinkedHashMap<Quest, Progression> playerQuests) {
        this.timestamp = timestamp;
        this.playerQuests = playerQuests;
        this.achievedQuests = 0;
    }

    /**
     * Get player timestamp.
     * @return timestamp.
     */
    public Long getTimestamp() {
        return this.timestamp;
    }

    /**
     * Increase number of achieved quests.
     */
    public void increaseAchievedQuests(String playerName) {
        this.achievedQuests++;

        if (this.achievedQuests == 3) {
            GlobalReward.sendGlobalReward(playerName);
        }
    }

    public void setAchievedQuests(int i) {
        this.achievedQuests = i;
    }

    /**
     * Get number of achieved quests.
     */
    public int getAchievedQuests() {
        return this.achievedQuests;
    }

    /**
     * Get player quests.
     * @return player quests.
     */
    public LinkedHashMap<Quest, Progression> getPlayerQuests() {
        return this.playerQuests;
    }

}
