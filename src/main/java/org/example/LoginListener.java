package org.example;

import fr.xephi.authme.events.LoginEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LoginListener implements Listener {

    private final LoginInvincibility plugin;

    public LoginListener(LoginInvincibility plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLogin(LoginEvent event) {
        Player player = event.getPlayer();

        // Verifica se o jogador não sofreu dano PvP antes de deslogar
        if (!plugin.getPvpDamagedPlayers().contains(player.getUniqueId())) {
            plugin.giveInvincibility(player);
        }

        // Remove o jogador da lista de dano PvP após o login (para a próxima sessão)
        plugin.getPvpDamagedPlayers().remove(player.getUniqueId());
    }
}
