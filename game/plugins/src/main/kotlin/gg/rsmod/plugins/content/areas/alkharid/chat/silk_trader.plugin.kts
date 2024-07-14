package gg.rsmod.plugins.content.areas.alkharid.chat

onNpcOption(Npcs.SILK_TRADER, "Talk-to") {
    player.queue {
        chatNpc("Do you want to buy any fine silks?")
        val option = options(
            "How much are they?",
            "No. Silk doesn't suit me."
        )

        when (option) {
            1 -> {
                chatPlayer("How much are they?")
                chatNpc("3 gp.")

                val priceOption = options(
                    "No. That's too much for me.",
                    "Okay, that sounds good"
                )

                when (priceOption) {
                    1 -> thatsTooMuch(this)
                    2 -> {
                        chatPlayer("Okay, that sounds good")
                        buySilk(this, 3)
                    }
                }
            }
            2 -> chatPlayer("No. Silk doesn't suit me.")
        }
    }
}

suspend fun thatsTooMuch(queue: QueueTask) = with(queue) {
    chatPlayer("No. That's too much for me.")
    chatNpc("2 gp and that's as low as I'll go.")
    chatNpc("I'm not selling it for any less. You'll probably go and sell it in Varrock for a profit, anyway.")

    val option = options(
        "2 gp sounds good.",
        "No, really. I don't want it."
    )

    when (option) {
        1 -> {
            chatPlayer("2 gp sounds good.")
            buySilk(this, 2)
        }
        2 -> {
            chatPlayer("No, really. I don't want it.")
            chatNpc("Okay, but that's the best price you're going to get.")
        }

        else -> return@with
    }
}

suspend fun buySilk(queue: QueueTask, cost: Int) = with(queue) {
    val inventory = player.inventory
    val coinsAmount =  inventory.getItemCount(Items.COINS_995)

    if (inventory.contains(Items.COINS_995) && coinsAmount >= cost) {

        itemMessageBox("You buy some silk for $cost gp.", Items.SILK)

        inventory.remove(Items.COINS_995, cost, true)
        inventory.add(Items.SILK, 1, true)
    } else {
        chatPlayer("Oh dear. I don't have enough money.")
        chatNpc("Well, come back when you do have some money!")
    }
}