package pl.zemoz.qcmmocoreintegration.condition;

import com.guillaumevdn.gcore.lib.material.Mat;
import com.guillaumevdn.gcore.lib.parseable.Parseable;
import com.guillaumevdn.gcore.lib.parseable.editor.EditorGUI;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPEnum;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPInteger;
import com.guillaumevdn.questcreator.QCLocaleEditor;
import com.guillaumevdn.questcreator.module.condition.Condition;
import com.guillaumevdn.questcreator.module.quest.Quest;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import pl.zemoz.qcmmocoreintegration.MMOCoreIntegration;

import java.util.List;

public class MMOCoreMainLevel extends Condition {

    private final PPEnum<Operation> operation = addComponent(new PPEnum<>("operation", this, "AT_LEAST", Operation.class, "operation", true, 10, EditorGUI.ICON_ENUM, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_OPERATIONLORE.getLines()));

    private final PPInteger value = addComponent(new PPInteger("value", this, "1", 0, Integer.MAX_VALUE, true, 12, EditorGUI.ICON_NUMBER, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_VALUELORE.getLines()));

    public MMOCoreMainLevel(String id, Parseable parent, boolean mandatory, int editorSlot, Mat editorIcon, List<String> editorDescription) {
        super(id, MMOCoreIntegration.MMOCORE_MAIN_LEVEL, parent, mandatory, editorSlot, editorIcon, editorDescription);
    }

    public Operation getOperation(Player parser) {
        return this.operation.getParsedValue(parser);
    }

    public Integer getValue(Player parser) {
        return this.value.getParsedValue(parser);
    }

    public boolean isValid(Player player, Player parser, Quest quest) {
        Operation operation = getOperation(parser);
        Integer value = getValue(parser);

        PlayerData playerData = PlayerData.get(player);

        switch (operation) {
            case EQUALS:
                return playerData.getLevel() == value;
            case DIFFERENT:
                return playerData.getLevel() != value;
            case AT_LEAST:
                return playerData.getLevel() >= value;
            case LESS_THAN:
                return playerData.getLevel() < value;
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