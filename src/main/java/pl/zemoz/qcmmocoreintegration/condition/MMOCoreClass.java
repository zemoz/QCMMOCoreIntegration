package pl.zemoz.qcmmocoreintegration.condition;

import com.guillaumevdn.gcore.lib.material.Mat;
import com.guillaumevdn.gcore.lib.parseable.Parseable;
import com.guillaumevdn.gcore.lib.parseable.editor.EditorGUI;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPEnum;
import com.guillaumevdn.gcore.lib.parseable.primitive.PPString;
import com.guillaumevdn.questcreator.QCLocaleEditor;
import com.guillaumevdn.questcreator.module.condition.Condition;
import com.guillaumevdn.questcreator.module.quest.Quest;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.entity.Player;
import pl.zemoz.qcmmocoreintegration.MMOCoreIntegration;

import java.util.List;

public class MMOCoreClass extends Condition {

    private final PPEnum<Operation> operation = addComponent(new PPEnum<>("operation", this, "EQUALS", Operation.class, "operation", true, 10, EditorGUI.ICON_ENUM, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_OPERATIONLORE.getLines()));

    private final PPString classId = addComponent(new PPString("class", this, "warrior", true, 12, EditorGUI.ICON_STRING, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_SETTINGLORE.getLines()));

    public MMOCoreClass(String id, Parseable parent, boolean mandatory, int editorSlot, Mat editorIcon, List<String> editorDescription) {
        super(id, MMOCoreIntegration.MMOCORE_CLASS, parent, mandatory, editorSlot, editorIcon, editorDescription);
    }

    public Operation getOperation(Player parser) {
        return this.operation.getParsedValue(parser);
    }

    public String getClassId(Player parser) {
        return this.classId.getParsedValue(parser);
    }

    public boolean isValid(Player player, Player parser, Quest quest) {
        Operation operation = getOperation(parser);
        String classId = getClassId(parser);

        PlayerData playerData = PlayerData.get(player);

        switch (operation) {
            case EQUALS:
                return playerData.getProfess().getId().equals(classId);
            case DIFFERENT:
                return !playerData.getProfess().getId().equals(classId);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void take(Player player, Player parser, Quest quest) {
    }

    public enum Operation {
        EQUALS, DIFFERENT
    }
}