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
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.attribute.PlayerAttribute;
import org.bukkit.entity.Player;
import pl.zemoz.qcmmocoreintegration.MMOCoreIntegration;

import java.util.List;

public class MMOCoreAttribute extends Condition {

    private final PPString attribute = addComponent(new PPString("attribute", this, "strength", true, 10, EditorGUI.ICON_STRING, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_SETTINGLORE.getLines()));

    private final PPEnum<Operation> operation = addComponent(new PPEnum<>("operation", this, "AT_LEAST", Operation.class, "operation", true, 12, EditorGUI.ICON_ENUM, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_OPERATIONLORE.getLines()));

    private final PPInteger value = addComponent(new PPInteger("value", this, "1", 0, Integer.MAX_VALUE, true, 14, EditorGUI.ICON_NUMBER, QCLocaleEditor.GUI_QUESTCREATOR_EDITOR_GENERIC_VALUELORE.getLines()));

    public MMOCoreAttribute(String id, Parseable parent, boolean mandatory, int editorSlot, Mat editorIcon, List<String> editorDescription) {
        super(id, MMOCoreIntegration.MMOCORE_ATTRIBUTE, parent, mandatory, editorSlot, editorIcon, editorDescription);
    }

    public String getAttribute(Player parser) {
        return this.attribute.getParsedValue(parser);
    }

    public MMOCoreAttribute.Operation getOperation(Player parser) {
        return this.operation.getParsedValue(parser);
    }

    public Integer getValue(Player parser) {
        return this.value.getParsedValue(parser);
    }

    public boolean isValid(Player player, Player parser, Quest quest) {
        String attributeName = getAttribute(parser);
        Operation operation = getOperation(parser);
        Integer value = getValue(parser);

        PlayerData playerData = PlayerData.get(player);
        PlayerAttribute attribute = MMOCore.plugin.attributeManager.get(attributeName);
        int attributeValue = playerData.getAttributes().getAttribute(attribute);

        switch (operation) {
            case EQUALS:
                return attributeValue == value;
            case DIFFERENT:
                return attributeValue != value;
            case AT_LEAST:
                return attributeValue >= value;
            case LESS_THAN:
                return attributeValue < value;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void take(Player player, Player parser, Quest quest) {
    }

    public enum Operation {
        EQUALS, DIFFERENT, AT_LEAST, LESS_THAN
    }
}