package gg.rsmod.plugins.content.areas.outpost

on_obj_option(Objs.LADDER_16683, "climb-up") {
    if (player.tile == Tile(2435, 3346)) {
        player.moveTo(Tile(2435, 3346, 1))
    } else if (player.tile == Tile(2436, 3346, 1)) {
        player.moveTo(Tile(2436, 3346, 2))
    }
}

on_obj_option(Objs.LADDER_16679, "climb-down") {
    if (player.tile == Tile(2435, 3346, 1)) {
        player.moveTo(Tile(2435, 3346))
    } else if (player.tile == Tile(2436, 3346, 2)) {
        player.moveTo(Tile(2436, 3346 , 1))
    }
}