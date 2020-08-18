package pl.zemoz.qcmmocoreintegration.object;

import com.guillaumevdn.gcore.data.UserInfo;
import com.guillaumevdn.gcore.lib.material.Mat;
import com.guillaumevdn.gcore.lib.parseable.Parseable;
import com.guillaumevdn.gcore.lib.parseable.editor.EditorGUI;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPString;
import com.guillaumevdn.gcore.libs.org.bukkit.configuration.util.Validate;
import com.guillaumevdn.questcreator.QCLocaleEditor;
import com.guillaumevdn.questcreator.module.BranchProgressAttemptResult;
import com.guillaumevdn.questcreator.module.object.TimerObject;
import com.guillaumevdn.questcreator.module.quest.ActiveBranch;
import com.guillaumevdn.questcreator.module.quest.ObjectProgression;
import com.guillaumevdn.questcreator.module.quest.Quest;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.PlayerClass;
import net.Indyuce.mmocore.api.player.profess.SavedClassInformation;
import org.bukkit.entity.Player;
import pl.zemoz.qcmmocoreintegration.MMOCoreIntegration;

import java.util.List;

public class ServerMMOCoreChangeClass extends TimerObject {
    private final PPString classId = addComponent(new PPString("class", this, "warrior", true, 12, EditorGUI.ICON_STRING, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_SETTINGLORE.getLines()));

    public ServerMMOCoreChangeClass(String id, Parseable parent, boolean mandatory, int editorSlot, Mat editorIcon, List<String> editorDescription) {
        super(id, MMOCoreIntegration.SERVER_MMOCORE_CHANGE_CLASS, parent, mandatory, editorSlot, editorIcon, editorDescription);
    }

    public String getClassId(Player parser) {
        return this.classId.getParsedValue(parser);
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
        String classId = getClassId(leader);
        PlayerClass playerClass = MMOCore.plugin.classManager.get(classId);
        Validate.notNull(playerClass, "Class with id " + classId + " does not exist!");

        for (Player player : UserInfo.getOnlinePlayers(quest.getUsers())) {
            PlayerData playerData = PlayerData.get(player);
            SavedClassInformation classInfo;
            if (playerData.hasSavedClass(playerClass)) {
                classInfo = playerData.getClassInfo(playerClass);
            } else {
                classInfo = new SavedClassInformation(1, 0, 0, 0, 0);
            }
            classInfo.load(playerClass, playerData);
            while (playerData.hasSkillBound(0))
                playerData.unbindSkill(0);
        }
        progress.setCurrent(1.0D);
        return BranchProgressAttemptResult.COMPLETE;
    }
}
