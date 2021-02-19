package dev.xWand.PunishmentPlus.playerdata;

import dev.xWand.PunishmentPlus.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerData {

    public File f;
    public FileConfiguration conf;
    public UUID uuid;

    Main plugin = Main.getPlugin(Main.class);

    public PlayerData(UUID uuid) {
        f = new File(plugin.getDataFolder() + "/playerdata/" + uuid.toString() + ".yml");
        conf = YamlConfiguration.loadConfiguration(f);
        saveData(conf, f);
        this.uuid = uuid;
    }

    public void saveData(FileConfiguration conf, File file) {
        try {
            conf.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMuted(boolean muted, String executor, String reason) {
        conf.set("muteData.isActive", muted);
        conf.set("muteData.executor", executor);
        conf.set("muteData.reason", reason);
        conf.set("muteData.when", System.currentTimeMillis()/1000);
        conf.set("muteData.expires", 0);
        saveData(conf, f);
    }

    public boolean isMuted() {
        return conf.getBoolean("muteData.isActive");
    }

    public String getMuteReason() {
        return conf.getString("muteData.reason");
    }

    public String getMuteExecutor() {
        return conf.getString("muteData.executor");
    }

    public long getMuteWhen() {
        return conf.getLong("muteData.when");
    }

    public long getMuteExpires() {
        return conf.getLong("muteData.expires");
    }

    // BANNING AND TEMPBANNING

    public void setBanned(boolean banned, String executor, String reason) {
        conf.set("banData.isActive", banned);
        conf.set("banData.executor", executor);
        conf.set("banData.reason", reason);
        conf.set("banData.when", System.currentTimeMillis()/1000);
        conf.set("banData.expires", 0);
        saveData(conf, f);
    }

    public void setTempbanned(boolean banned, String executor, String reason, long millis) {
        conf.set("banData.isActive", banned);
        conf.set("banData.executor", executor);
        conf.set("banData.reason", reason);
        conf.set("banData.when", System.currentTimeMillis()/1000);
        conf.set("banData.expires", (System.currentTimeMillis()/1000) + millis);
        saveData(conf, f);
    }

    public boolean isTempbanned() {
        if (getExpires() == 0) {
            return false;
        }
        return true;
    }

    public void setBanned(boolean banned) {
        conf.set("banData.isActive", banned);
        if (banned == false) {
            conf.set("banData.expires", 0);
        }
        saveData(conf, f);
    }

    public boolean isBanned() {
        return conf.getBoolean("banData.isActive");
    }

    public String getExecutor() {
        return conf.getString("banData.executor");
    }

    public String getReason() {
        return conf.getString("banData.reason");
    }

    public long getMillis() {
        return conf.getLong("banData.when");
    }

    public long getExpires() {
        return conf.getLong("banData.expires");
    }

    public boolean exists() {
        return f.exists();
    }
}
