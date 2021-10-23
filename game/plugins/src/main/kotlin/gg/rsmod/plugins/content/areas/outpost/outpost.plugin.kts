package gg.rsmod.plugins.content.areas.outpost

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

// TODO: face right direction

on_obj_option(16683, "climb-up") {
    if (player.tile == Tile(2435, 3346)) {
        player.teleport(Tile(2435, 3346, 1), TeleportType.CLIMB_UP_LADDER)
    } else if (player.tile == Tile(2436, 3346, 1)) {
        player.teleport(Tile(2436, 3346, 2), TeleportType.CLIMB_UP_LADDER)
    }
}

on_obj_option(16679, "climb-down") {
    if (player.tile == Tile(2435, 3346, 1)) {
        player.teleport(Tile(2435, 3346), TeleportType.CLIMB_DOWN_LADDER)
    } else if (player.tile == Tile(2436, 3346, 2)) {
        player.teleport(Tile(2436, 3346 , 1), TeleportType.CLIMB_DOWN_LADDER)
    }
}