package gg.rsmod.plugins.content.areas.alkharid.chat

onNpcOption(Npcs.SHOP_KEEPER_2817, "Talk-to") {
    player.queue {
        chatNpc("Can I help you at all?")
        val option = options(
            "Yes please. What are you selling?",
            "No thanks.",
            "What do you think of Ali Morrisane?"
        )

        when (option) {
            1 -> player.openShop("Al Kharid General Store")
            2 -> chatPlayer("No thanks.")
            3 -> aliMorrisane(this)
        }
    }
}


onNpcOption(Npcs.SHOP_KEEPER_2817, "Trade") {
    player.openShop("Al Kharid General Store")
}

suspend fun aliMorrisane(queue: QueueTask) = with (queue) {
    chatPlayer("What do you think of Ali Morrisane?")
    chatNpc("I don't trust him, not after he's sent his men round to threaten us.")
    chatNpc("Did you know, we've had four different thugs come round and threaten us if we don't join them.")
    chatNpc("I've had hammers, rakes, swords and even scissors waved at me!")
    chatPlayer("What will you do about it?")
    chatNpc("We'll carry on as normal with the store. Business as usual!")
    chatNpc("Can I interest you in anything?")

    val option = options(
        "Yes please. What are you selling?",
        "No thanks."
    )

    when (option) {
        1 -> player.openShop("Al Kharid General Store")
        2 -> chatPlayer("No thanks.")
    }
}