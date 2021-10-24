package gg.rsmod.plugins.content.cmd

on_command("tile") {
    player.message("x: ${player.tile.x}; z: ${player.tile.z}; height: ${player.tile.height}")
}