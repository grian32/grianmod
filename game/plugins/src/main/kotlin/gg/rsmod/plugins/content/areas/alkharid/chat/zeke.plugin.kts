package gg.rsmod.plugins.content.areas.alkharid.chat


// TODO: add al kharid flyer interaction

onNpcOption(Npcs.ZEKE, "Trade") {
    player.openShop("Zeke's Superior Scimitar")
}

onNpcOption(Npcs.ZEKE, "Talk-to") {
    player.queue {
        chatNpc("A thousand greetings, sir.", npc = Npcs.ZEKE)
        val option = options(
            "Do you want to trade?",
            "Nice cloak.",
            "Could you sell me a dragon scimitar?",
            "What do you think of Ali Morrisane?"
        )

        when (option) {
            1 -> trade(this)
            2 -> cloak(this)
            3 -> dragonScimitar(this)
            4 -> aliMorrisane(this)
            else -> return@queue
        }
    }
}

suspend fun trade(queue: QueueTask) = with (queue) {
    chatPlayer("Do you want to trade?")
    chatNpc("Yes, certainly. I deal in scimitars.", npc = Npcs.ZEKE)
}

suspend fun cloak(queue: QueueTask) = with (queue) {
    chatPlayer("Nice cloak.")
    chatNpc("Thank you.", npc = Npcs.ZEKE)
}

suspend fun dragonScimitar(queue: QueueTask) = with (queue) {
    chatPlayer("Could you sell me a dragon scimitar?")
    chatNpc("A dragon scimitar? A DRAGON scimitar?", npc = Npcs.ZEKE)
    chatNpc("The banana-brained nitwits who make them would never dream of selling any to me.", npc = Npcs.ZEKE)
    chatPlayer("Hmmm, funny you should say that...")
    chatNpc("Perhaps you'd like to take a look at my stock?", npc = Npcs.ZEKE)
    val option = options(
        "Yes please, Zeke.",
        "Not today, thank you."
    )

    when (option) {
        1 -> player.openShop("Zeke's Superior Scimitar")
        2 -> return
    }
}

suspend fun aliMorrisane(queue: QueueTask) = with (queue) {
    chatPlayer("What do you think about Ali Morrisane?")
    chatNpc("He is a dangerous man.", npc = Npcs.ZEKE)
    chatNpc("Although he does not appear to be dangerous, he has brought several men to this town who have threatened me and several others.", npc = Npcs.ZEKE)
    chatNpc("One man even threatened me with a hammer, saying that when he set up his smithy, my shoddy workmanship would be revealed!", npc = Npcs.ZEKE)
    chatPlayer("What will you do about these threats?")
    chatNpc("Oh, I am quite confident in the quality of my work...as will you be if you take a look at my wares.", npc = Npcs.ZEKE)

    val option = options(
        "Yes please, Zeke.",
        "Not today, thank you."
    )

    when (option) {
        1 -> player.openShop("Zeke's Superior Scimitar")
        2 -> return
    }
}