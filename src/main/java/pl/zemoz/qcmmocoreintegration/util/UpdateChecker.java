package pl.zemoz.qcmmocoreintegration.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;

public class UpdateChecker {
    private final Plugin plugin;
    private final int id;

    public UpdateChecker(Plugin plugin, int id) {
        this.plugin = plugin;
        this.id = id;
    }

    public void checkForUpdate() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            plugin.getLogger().log(Level.INFO, "Checking for update.");
            try {
                HttpsURLConnection connection = (HttpsURLConnection) (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.id)).openConnection();
                connection.setRequestMethod("GET");
                String latestVersion = (new BufferedReader(new InputStreamReader(connection.getInputStream()))).readLine();

                if (isOutdated(latestVersion)) {
                    plugin.getLogger().log(Level.INFO, "------------------------------------------");
                    plugin.getLogger().log(Level.INFO, "New version available: " + latestVersion);
                    plugin.getLogger().log(Level.INFO, "Download: " + getDownloadUrl());
                    plugin.getLogger().log(Level.INFO, "------------------------------------------");
                } else {
                    plugin.getLogger().log(Level.INFO, "No new version available.");
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.INFO, "Checking for new version failed.");
            }
        });
    }

    private boolean isOutdated(String latestVersion) {
        String currentVersion = plugin.getDescription().getVersion();
        if (currentVersion.equals(latestVersion))
            return false;
        String[] currentVersionParts = currentVersion.replaceAll("[^0-9.]", "").split("\\.");
        String[] latestVersionParts = latestVersion.replaceAll("[^0-9.]", "").split("\\.");

        int length = Math.max(currentVersionParts.length, latestVersionParts.length);
        for (int i = 0; i < length; i++) {
            int currentPart = i < currentVersionParts.length ?
                    Integer.parseInt(currentVersionParts[i]) : 0;
            int latestPart = i < latestVersionParts.length ?
                    Integer.parseInt(latestVersionParts[i]) : 0;
            if (currentPart < latestPart)
                return true;
        }
        return false;
    }

    private String getDownloadUrl() {
        return "https://www.spigotmc.org/resources/" + this.id;
    }
}
