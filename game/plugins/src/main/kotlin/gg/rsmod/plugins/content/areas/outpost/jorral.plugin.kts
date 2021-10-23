package gg.rsmod.plugins.content.areas.outpost

spawn_npc(3490, 2437, 3347, direction = Direction.WEST)

on_npc_option(3490, "talk-to") {
    player.queue {
        // post-quest dialogue in osrs
        chatNpc("Thanks for the help again. Feel free to browse the room next door!")
    }
}