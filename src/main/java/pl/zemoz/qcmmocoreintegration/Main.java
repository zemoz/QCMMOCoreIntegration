package pl.zemoz.qcmmocoreintegration;

import com.guillaumevdn.questcreator.QuestCreator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static java.util.Objects.isNull;

public class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (isNull(pluginManager.getPlugin("MMOCore"))) {
            getLogger().severe(String.format("[%s] - Disabled due to no MMOCore dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (isNull(pluginManager.getPlugin("QuestCreator"))) {
            getLogger().severe(String.format("[%s] - Disabled due to no QuestCreator dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        QuestCreator.inst().registerPluginIntegration("QCMMOCoreIntegration", MMOCoreIntegration.class);
    }
}
