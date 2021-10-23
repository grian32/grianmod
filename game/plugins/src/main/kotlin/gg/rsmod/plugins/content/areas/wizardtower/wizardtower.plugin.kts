package gg.rsmod.plugins.content.areas.wizardtower

on_obj_option(Objs.LADDER_2147, "climb-down") {
    if (player.tile == Tile(3105, 3162)) {
        player.moveTo(Tile(3104, 9576))
    }
}

on_obj_option(Objs.LADDER_2148, "climb-up") {
    if (player.tile == Tile(3104, 9576)) {
        player.moveTo(Tile(3105, 3162))
    }
}