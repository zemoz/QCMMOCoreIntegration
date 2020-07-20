package pl.zemoz.qcmmocoreintegration;

import com.guillaumevdn.gcore.lib.integration.PluginIntegration;
import com.guillaumevdn.questcreator.module.condition.ConditionType;
import com.guillaumevdn.questcreator.module.object.QObjectType;
import com.guillaumevdn.questcreator.util.parseable.container.CPLocation;
import pl.zemoz.qcmmocoreintegration.condition.MMOCoreClass;
import pl.zemoz.qcmmocoreintegration.condition.MMOCoreMainLevel;
import pl.zemoz.qcmmocoreintegration.condition.MMOCoreProfessionLevel;
import pl.zemoz.qcmmocoreintegration.object.ServerMMOCoreGiveMainXP;
import pl.zemoz.qcmmocoreintegration.object.ServerMMOCoreGiveProfessionXP;

public class MMOCoreIntegration extends PluginIntegration {

    public static ConditionType MMOCORE_CLASS = null;
    public static ConditionType MMOCORE_MAIN_LEVEL = null;
    public static ConditionType MMOCORE_PROFESSION_LEVEL = null;

    public static QObjectType SERVER_MMOCORE_GIVE_MAIN_XP = null;
    public static QObjectType SERVER_MMOCORE_GIVE_PROFESSION_XP = null;

    public MMOCoreIntegration(String pluginName) {
        super(pluginName);
    }

    public void enable() {
        MMOCORE_CLASS = ConditionType.registerType("MMOCORE_CLASS", MMOCoreClass.class, "MMOCore");
        MMOCORE_MAIN_LEVEL = ConditionType.registerType("MMOCORE_MAIN_LEVEL", MMOCoreMainLevel.class, "MMOCore");
        MMOCORE_PROFESSION_LEVEL = ConditionType.registerType("MMOCORE_PROFESSION_LEVEL", MMOCoreProfessionLevel.class, "MMOCore");

        SERVER_MMOCORE_GIVE_MAIN_XP = QObjectType.registerType("SERVER_MMOCORE_GIVE_MAIN_XP", ServerMMOCoreGiveMainXP.class, QObjectType.Category.SERVER, CPLocation.Need.USELESS, "MMOCore");
        SERVER_MMOCORE_GIVE_PROFESSION_XP = QObjectType.registerType("SERVER_MMOCORE_GIVE_PROFESSION_XP", ServerMMOCoreGiveProfessionXP.class, QObjectType.Category.SERVER, CPLocation.Need.USELESS, "MMOCore");
    }

    public void disable() {
        MMOCORE_CLASS = MMOCORE_CLASS.unregister();
        MMOCORE_MAIN_LEVEL = MMOCORE_MAIN_LEVEL.unregister();
        MMOCORE_PROFESSION_LEVEL = MMOCORE_PROFESSION_LEVEL.unregister();

        SERVER_MMOCORE_GIVE_MAIN_XP = SERVER_MMOCORE_GIVE_MAIN_XP.unregister();
        SERVER_MMOCORE_GIVE_PROFESSION_XP = SERVER_MMOCORE_GIVE_PROFESSION_XP.unregister();
    }
}