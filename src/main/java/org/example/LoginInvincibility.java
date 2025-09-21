package org.example;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LoginInvincibility extends JavaPlugin implements Listener {

    private final Set<UUID> pvpDamagedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        // Registra o listener para o evento de login do AuthMe
        getServer().getPluginManager().registerEvents(new LoginListener(this), this);
        // Registra o listener para eventos de dano neste plugin
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("LoginInvincibility habilitado!");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            pvpDamagedPlayers.add(event.getEntity().getUniqueId());
        }
    }


    public void giveInvincibility(Player player) {
        // Aplica o efeito de invencibilidade por 5 segundos (100 ticks)
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 4));
        player.sendMessage(ChatColor.GREEN + "Você está invencível por 5 segundos!");

        // Agenda o fim da invencibilidade
        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(ChatColor.RED + "Sua invencibilidade acabou!");
            }
        }.runTaskLater(this, 100L); // 100 ticks = 5 segundos
    }

    public Set<UUID> getPvpDamagedPlayers() {
        return pvpDamagedPlayers;
    }
}
