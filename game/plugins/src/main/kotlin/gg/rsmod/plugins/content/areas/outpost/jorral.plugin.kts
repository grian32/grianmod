package gg.rsmod.plugins.content.areas.outpost

spawnNpc(3490, 2437, 3347, direction = Direction.WEST)

onNpcOption(3490, "talk-to") {
    player.queue {
        // post-quest dialogue in osrs
        chatNpc("Thanks for the help again. Feel free to browse the room next door!")
    }
}