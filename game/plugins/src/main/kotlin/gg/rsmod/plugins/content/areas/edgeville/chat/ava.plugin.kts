package gg.rsmod.plugins.content.areas.edgeville.chat

onNpcOption(Npcs.AVA, "talk-to") {
    player.queue {
        chatNpc("Would you like to buy an Ava's Accumulator for 25,000 coins?", Npcs.AVA, title = "Ava")
        val option = options(
            "Yes, please!",
            "No! Fuck off!"
        )

        when (option) {
            1 -> {
                if (player.inventory.remove(Items.COINS_995, 25_000, true).hasSucceeded()) {
                    player.inventory.add(Items.AVAS_ACCUMULATOR)
                } else {
                    player.message("You don't have enough coins!")
                }
            }
            2 -> {
                player.message("Ava slaps you.")
                player.hit(1)
            }
        }
    }
}