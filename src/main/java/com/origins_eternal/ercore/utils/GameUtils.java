package com.origins_eternal.ercore.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.origins_eternal.ercore.event.KeyEvent.password;

@SideOnly(Side.CLIENT)
public class GameUtils {
    public static boolean checkChinese() {
        return System.getProperty("user.language").equals("zh");
    }

    public static void moveFiles() throws IOException {
        Minecraft mc = Minecraft.getMinecraft();
        String gamepath = mc.gameDir.getPath();
        String frompath = gamepath + "\\mods\\Evolution-Reset-Resource-Pack-1.0.0.zip";
        String topath = gamepath + "\\resourcepacks\\Evolution-Reset-Resource-Pack-1.0.0.zip";
        File pack = new File(frompath);
        File resourcepack = new File(topath);
        Path resource = Paths.get(topath);
        Path backup = Paths.get(frompath);
        if (pack.exists() && !resourcepack.exists()) {
            Files.copy(backup, resource);
        } else if (!pack.exists() && resourcepack.exists()) {
            Files.copy(resource, backup);
        }
    }

    public static void installResourcepacks() {
        Minecraft mc = Minecraft.getMinecraft();
        ResourcePackRepository Repository = mc.getResourcePackRepository();
        Repository.updateRepositoryEntriesAll();
        List<ResourcePackRepository.Entry> Packs = Repository.getRepositoryEntriesAll();
        List<ResourcePackRepository.Entry> Resourcepacks = new ArrayList<>();
        for (ResourcePackRepository.Entry pack : Packs) {
            if (pack.getResourcePackName().contains("Evolution-Reset")) {
                Resourcepacks.add(pack);
            }
            Repository.setRepositories(Resourcepacks);
        }
    }

    public static void setChinese() {
        Minecraft mc = Minecraft.getMinecraft();
        GameSettings gameSettings = mc.gameSettings;
        if (checkChinese()) {
            mc.getLanguageManager().setCurrentLanguage(new Language("zh_cn", "CN", "简体中文", false));
            gameSettings.language = "zh_cn";
        }
    }

    public static void checkTags(EntityPlayer player, String letter) {
        Set<String> tags = player.getTags();
        String registerData = "true";
        EntityDataManager dataManager = player.getDataManager();
        if (!tags.contains(registerData)) {
            dataManager.register(password, "S");
            player.addTag(registerData);
        } else {
            String data = dataManager.get(password);
            dataManager.set(password, data + letter);
        }
    }

    public static void addPotions(EntityPlayer player, Potion potion, int time, int level, Boolean showIcon, Boolean showParticles) {
        player.addPotionEffect(new PotionEffect(potion, time, level, showIcon, showParticles));
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                player.removePotionEffect(potion);
            }
        };
        timer.schedule(timerTask, time / 20 * 1000);
    }
}