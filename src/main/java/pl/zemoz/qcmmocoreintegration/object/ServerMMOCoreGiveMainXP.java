package pl.zemoz.qcmmocoreintegration.object;

import com.guillaumevdn.gcore.data.UserInfo;
import com.guillaumevdn.gcore.lib.material.Mat;
import com.guillaumevdn.gcore.lib.parseable.Parseable;
import com.guillaumevdn.gcore.lib.parseable.editor.EditorGUI;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPInteger;
import com.guillaumevdn.questcreator.QCLocaleEditor;
import com.guillaumevdn.questcreator.module.BranchProgressAttemptResult;
import com.guillaumevdn.questcreator.module.object.TimerObject;
import com.guillaumevdn.questcreator.module.quest.ActiveBranch;
import com.guillaumevdn.questcreator.module.quest.ObjectProgression;
import com.guillaumevdn.questcreator.module.quest.Quest;
import net.Indyuce.mmocore.api.experience.EXPSource;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import pl.zemoz.qcmmocoreintegration.MMOCoreIntegration;

import java.util.List;

public class ServerMMOCoreGiveMainXP extends TimerObject {

    private final PPInteger value = addComponent(new PPInteger("value", this, "1", 0, Integer.MAX_VALUE, true, this.SETTINGS_SLOT_START, EditorGUI.ICON_NUMBER, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_VALUELORE.getLines()));

    public ServerMMOCoreGiveMainXP(String id, Parseable parent, boolean mandatory, int editorSlot, Mat editorIcon, List<String> editorDescription) {
        super(id, MMOCoreIntegration.SERVER_MMOCORE_GIVE_MAIN_XP, parent, mandatory, editorSlot, editorIcon, editorDescription);
    }

    public Integer getValue(Player parser) {
        return this.value.getParsedValue(parser);
    }

    public void initialize(Quest quest, ActiveBranch activeBranch, ObjectProgression progress) {
    }

    public BranchProgressAttemptResult progressAttempt(Quest quest, ActiveBranch activeBranch, ObjectProgression progress, List<UserInfo> affectedUsers, List<Player> affected) {
        switch (checkAndTakeProgressConditions(quest, activeBranch, progress)) {
            case CANT_PROGRESS:
                return BranchProgressAttemptResult.NONE;
            case CANT_PROGRESS_GOTO:
                return BranchProgressAttemptResult.COMPLETE;
        }

        Player leader = quest.getLeader().toPlayer();
        Integer value = getValue(leader);

        for (Player pl : UserInfo.getOnlinePlayers(quest.getUsers())) {
            PlayerData playerData = PlayerData.get(pl);
            playerData.giveExperience(value, EXPSource.QUEST);
        }

        progress.setCurrent(1.0D);
        return BranchProgressAttemptResult.COMPLETE;
    }

}