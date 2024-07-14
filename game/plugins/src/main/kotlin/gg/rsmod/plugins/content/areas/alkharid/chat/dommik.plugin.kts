package gg.rsmod.plugins.content.areas.alkharid.chat

onNpcOption(Npcs.DOMMIK, "Trade") {
    player.openShop("Dommik's Crafting Store.")
}

onNpcOption(Npcs.DOMMIK, "Talk-to") {
    player.queue {
        chatNpc("Would you like to buy some crafting equipment?")
        val option = options(
            "No thanks; I've got all the Crafting equipment I need.",
            "Let's see what you've got then."
        )

        when (option) {
            1 -> {
                chatPlayer("No thanks; I've got all the Crafting equipment I need.")
                chatNpc("Okay. Fare well on your travels.")
            }

            2 -> {
                player.openShop("Dommik's Crafting Store.")
            }
        }
    }
}

// TODO: add al kharid flyer interaction
// TODO: change dialogue to post quest completion