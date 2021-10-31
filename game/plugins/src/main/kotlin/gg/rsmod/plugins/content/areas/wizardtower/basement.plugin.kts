package gg.rsmod.plugins.content.areas.wizardtower

import gg.rsmod.plugins.content.magic.TeleportType
import gg.rsmod.plugins.content.magic.teleport

// TODO: figure out why rune essence mine loc isnt same as osrs =[

spawn_npc(Npcs.CHICKEN_1173, 3109, 9568, walkRadius = 3)
spawn_npc(Npcs.SEDRIDOR, 3102, 9570, walkRadius = 5)

on_npc_option(Npcs.SEDRIDOR, "talk-to") {
    player.queue {
        chatNpc(
            "Welcome adventurer, to the world renowned Wizards' Tower. How may I help you?",
            npc = Npcs.SEDRIDOR,
            title = "Sedridor"
        )
        chatPlayer("Hello there.", title = player.username)
        chatNpc(
            "Hello again ${player.username}. What can I do for oyu?",
            npc = Npcs.SEDRIDOR,
            title = "Sedridor"
        )
        val option = options(
            "Nothing thanks, I'm just looking around.",
            "Can you teleport me to the Rune Essence?",
            title = "Select an option"
        )

        when (option) {
            1 -> {
                chatPlayer("Nothing thanks, I'm just looking around.", title = player.username)
                chatNpc(
                    "Well, take care adventurer. You stand on the ruins of the old destroyed Wizards' Tower. Strange and powerful magicks lurk here.",
                    npc = Npcs.SEDRIDOR,
                    title = "Sedridor"
                )
            }
            2 -> {
                chatPlayer("Can you teleport me to the Rune Essence?")
                runeEssenceTeleport(player)
            }
        }
    }
}

on_npc_option(Npcs.SEDRIDOR, "teleport") {
    runeEssenceTeleport(player)
}

listOf(381, 380).forEach {
    on_obj_option(it, "search") {
        player.message(
            listOf(
                "You find nothing to interest you.",
                "You don't find anything that you'd ever want to read.",
                "None of them look very interesting."
            ).random()
        )
    }
}

fun runeEssenceTeleport(player: Player) {
    player.teleport(Tile(2910, 4832), TeleportType.RUNE_ESSENCE_MINE)
    player.getInteractingNpc().graphic(108)
    world.spawn(AreaSound(player.tile, 200, 5, 1))
}