package pl.zemoz.qcmmocoreintegration.condition;

import com.guillaumevdn.gcore.lib.material.Mat;
import com.guillaumevdn.gcore.lib.parseable.Parseable;
import com.guillaumevdn.gcore.lib.parseable.editor.EditorGUI;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPEnum;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPInteger;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPString;
import com.guillaumevdn.questcreator.QCLocaleEditor;
import com.guillaumevdn.questcreator.module.condition.Condition;
import com.guillaumevdn.questcreator.module.quest.Quest;
import net.Indyuce.mmocore.MMOCore;
import net.Indyuce.mmocore.api.experience.Profession;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import pl.zemoz.qcmmocoreintegration.MMOCoreIntegration;

import java.util.List;

public class MMOCoreProfessionLevel extends Condition {

    private final PPString profession = addComponent(new PPString("profession", this, "mining", true, 10, EditorGUI.ICON_STRING, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_SETTINGLORE.getLines()));

    private final PPEnum<Operation> operation = addComponent(new PPEnum<>("operation", this, "AT_LEAST", Operation.class, "operation", true, 12, EditorGUI.ICON_ENUM, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_OPERATIONLORE.getLines()));

    private final PPInteger value = addComponent(new PPInteger("value", this, "1", 0, Integer.MAX_VALUE, true, 14, EditorGUI.ICON_NUMBER, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_VALUELORE.getLines()));

    public MMOCoreProfessionLevel(String id, Parseable parent, boolean mandatory, int editorSlot, Mat editorIcon, List<String> editorDescription) {
        super(id, MMOCoreIntegration.MMOCORE_PROFESSION_LEVEL, parent, mandatory, editorSlot, editorIcon, editorDescription);
    }

    public String getProfessionName(Player parser) {
        return this.profession.getParsedValue(parser);
    }

    public Operation getOperation(Player parser) {
        return this.operation.getParsedValue(parser);
    }

    public Integer getValue(Player parser) {
        return this.value.getParsedValue(parser);
    }

    public boolean isValid(Player player, Player parser, Quest quest) {
        String professionName = getProfessionName(parser);
        Operation operation = getOperation(parser);
        Integer value = getValue(parser);

        PlayerData playerData = PlayerData.get(player);
        Profession profession = MMOCore.plugin.professionManager.get(professionName);
        int level = playerData.getCollectionSkills().getLevel(profession);

        switch (operation) {
            case EQUALS:
                return level == value;
            case DIFFERENT:
                return level != value;
            case AT_LEAST:
                return level >= value;
            case LESS_THAN:
                return level < value;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void take(Player player, Player parser, Quest quest) {
    }

    public enum Operation {
        EQUALS, DIFFERENT, AT_LEAST, LESS_THAN;
    }
}