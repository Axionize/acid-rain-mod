package com.lightning.axionize;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AcidRainMod implements ModInitializer {
	public static final String MOD_ID = "Acid Rain";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean isAcidEnabled = false;
	public static int damageIntervalTicks = 10; // Default: Every 10 ticks (0.5 seconds)
	public static float damageAmount = 1.0f;     // Default: 1.0 health point (0.5 hearts)

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Acid Rain Mod...");
		registerCommands();
		registerTickHandler();
	}

	private void registerCommands() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("acid")
					.requires(source -> source.hasPermissionLevel(2))
					.then(CommandManager.literal("on")
							.then(CommandManager.argument("interval", IntegerArgumentType.integer(1)) // Minimum interval of 1 tick
									.then(CommandManager.argument("damage", FloatArgumentType.floatArg(0.0f)) // Minimum damage of 0
											.executes(context -> {
												isAcidEnabled = true;
												damageIntervalTicks = IntegerArgumentType.getInteger(context, "interval");
												damageAmount = FloatArgumentType.getFloat(context, "damage");
												context.getSource().sendFeedback(() -> Text.literal(String.format("Acid rain/water enabled. Damage: %.1f every %d ticks.", damageAmount, damageIntervalTicks)), true);
												return 1;
											})))
							.executes(context -> { // /acid on  (with defaults)
								isAcidEnabled = true;
								context.getSource().sendFeedback(() -> Text.literal(String.format("Acid rain/water enabled. Damage: %.1f every %d ticks.", damageAmount, damageIntervalTicks)), true);
								return 1;
							}))
					.then(CommandManager.literal("off")
							.executes(context -> {
								isAcidEnabled = false;
								context.getSource().sendFeedback(() -> Text.literal("Acid rain/water disabled."), true);
								return 1;
							}))
					.then(CommandManager.literal("status")
							.executes(context -> {
								String status = isAcidEnabled ? "enabled" : "disabled";
								String message = String.format("Acid rain/water is currently %s. Damage: %.1f every %d ticks.", status, damageAmount, damageIntervalTicks);
								context.getSource().sendFeedback(() -> Text.literal(message), false);
								return 1;
							}))
			);
		});
	}

	private void registerTickHandler() {
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			if (!isAcidEnabled) {
				return;
			}

			for (ServerWorld world : server.getWorlds()) {
				for (ServerPlayerEntity player : world.getPlayers()) { // Directly iterate over players
					processPlayer(player, world);
				}
			}
		});
	}

	private void processPlayer(ServerPlayerEntity player, ServerWorld world) {
		if (player.age % damageIntervalTicks == 0) { // Check for damage interval
			if (isInAcid(player, world)) {
				player.damage(world, AcidRainDamageTypes.of(world, AcidRainDamageTypes.ACID_DAMAGE_TYPE), damageAmount);
			}
		}
	}

	private boolean isInAcid(PlayerEntity player, World world) {
		return player.isWet();
	}
}