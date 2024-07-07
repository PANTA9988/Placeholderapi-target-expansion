package com.panta.targetextension;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;

public class MiExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getIdentifier() {
        return "Target";
    }

    @Override
    public String getAuthor() {
        return "PANTA9988";
    }

    @Override
    public String getVersion() {
        return "1.3";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        // Obtener la entidad que el jugador está mirando
        Entity target = getTargetEntity(player);
        if (identifier.equals("health")) {
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) target;
                return String.valueOf(livingEntity.getHealth());
            } else {
                return "0";
            }
        } else if (identifier.equals("max_health")) {
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) target;
                return String.valueOf(livingEntity.getMaxHealth());
            } else {
                return "1";
            }
        }

        // Handle other placeholders based on identifier
        String valueToReturn = "";
        if (target instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) target;
            switch (identifier) {
                case "health_round":
                    valueToReturn = String.valueOf(Math.round(livingEntity.getHealth()));
                    break;
                case "max_health_round":
                    valueToReturn = String.valueOf(Math.round(livingEntity.getMaxHealth()));
                    break;
                case "id":
                    valueToReturn = String.valueOf(livingEntity.getEntityId());
                    break;
                case "effects":
                    valueToReturn = String.valueOf(livingEntity.getActivePotionEffects());
                    break;
                case "cords":
                    valueToReturn = String.valueOf(livingEntity.getLocation());
                    break;
                case "type":
                    valueToReturn = String.valueOf(livingEntity.getType());
                    break;
                case "customname":
                    valueToReturn = String.valueOf(livingEntity.getCustomName());
                    break;
                // Add more cases for other placeholders you want to support
            }
        } else {
            // Set default values for non-LivingEntity based on the placeholder (optional)
            switch (identifier) {
                case "health_round":
                case "max_health_round":
                case "id":
                case "effects":
                case "cords":
                case "type":
                case "customname":
                    valueToReturn = ""; // Or a different default value
                    break;
            }
        }

        return valueToReturn;
    }

    private Entity getTargetEntity(Player player) {
        RayTraceResult result = player.getWorld().rayTraceEntities(
                player.getEyeLocation(),
                player.getEyeLocation().getDirection(),
                300,
                entity -> !entity.equals(player) && entity instanceof LivingEntity
        );

        return result != null ? result.getHitEntity() : null;
    }
}