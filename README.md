# Acid Rain

This mod is very simple and intended for events or survival. It adds acid rain/water damage to the game, affecting players caught in the rain or submerged in water. It's designed to add a survival challenge, making weather and water sources more dangerous.

## Features

*   **Acid Rain/Water Damage:** Players take damage when exposed to rain or submerged in water.
    *   **Player Only:** The acid rain only affects players.
    *   **Configurable Damage:** The damage amount and interval are configurable via commands.
    *   **Difficulty Scaling:**  The damage scales with the world difficulty just like damage from mobs.
    *   **Bypasses Armor:** Armor is ignored when calculating damage (but protection enchantments still apply).
    *   **No Knockback:** The damage does not cause knockback.
    *   **Small Exhaustion:** Applies a small amount of exhaustion (0.1 hunger) with each damage tick.
    *   **No Damage Tick Bypass:** The damage bypasses the `noDamageTicks` mechanic, ensuring consistent damage application, even if the player is already taking other forms of damage (like fire).

## Commands

The mod includes commands to control the acid rain effect. These commands require operator (OP) permissions (permission level 2 or higher).

*   `/acid on [interval] [damage]` : Enables acid rain/water damage.
    *   `interval`:  The interval in ticks between each damage application (default is 10 ticks, or 0.5 seconds). Must be an integer of 1 or greater.
    *   `damage`: The amount of damage dealt each interval (default is 1.0, or 0.5 hearts). Must be a float of 0.0 or greater.
    *   Example: `/acid on 20 0.5` enables acid rain, dealing 0.5 damage every 20 ticks (1 second).
    *   If `interval` and `damage` are omitted, the default values are used.
*   `/acid off`: Disables acid rain/water damage.
*   `/acid status`:  Displays the current status of the acid rain (enabled/disabled), the damage amount, and the damage interval.

## Configuration

The mod offers in-game configuration via commands:

*   **Damage Interval:**  Adjust the frequency of damage application.  A smaller interval means more frequent damage.
*   **Damage Amount:** Control the severity of the acid rain.

## Technical Details

*   **Mod ID:** `Acid Rain`
*   **Damage Interval:**  The `damageIntervalTicks` variable determines how often damage is applied. By default, it's set to 10 ticks (0.5 seconds).
*   **Damage Amount:** The `damageAmount` variable determines the base damage applied.
*   **Tick Handler:** The mod registers a `ServerTickEvents.END_SERVER_TICK` handler that processes all players in all loaded server worlds every tick.
*   **Custom Damage Type:** Uses it's own damage type.

## Installation

1.  Make sure you have the Fabric Loader installed.
2.  Download the latest version of the Acid Rain mod from Modrinth.
3.  Place the downloaded `.jar` file into your Minecraft's `mods` folder.

## Planned Features/Future Development

*   [ ] Config file for setting default damage and interval.
*   [ ] Add a custom effect or particle effect to indicate the acid rain/water.
*   [ ] Add config to make it affect mobs.
*   [ ] Add config to make it only affect rain and not water.
*   [ ] Translations
*   [ ] More than one random death message
*   [ ] Per-world acid rain toggling
*   [ ] Persistence (worlds/server remembers worlds with acid rain on/off)