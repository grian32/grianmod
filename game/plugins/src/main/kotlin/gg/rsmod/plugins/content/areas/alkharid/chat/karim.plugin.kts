package gg.rsmod.plugins.content.areas.alkharid.chat

onNpcOption(Npcs.KARIM, "Talk-to") {
    player.queue {
        chatNpc("Would you like to buy a nice kebab? Only one gold.")
        val option = options(
            "I think I'll give it a miss.",
            "Yes please.",
            "What do you think of Ali Morrisane?"
        )

        when (option) {
            1 -> chatPlayer("I think I'll give it a miss.")
            2 -> buyKebab(this)
            3 -> aliMorrisane(this)
        }
    }
}

suspend fun buyKebab(queue: QueueTask) = with (queue) {
    val inventory = player.inventory

    if (inventory.contains(Items.COINS_995)) {
        inventory.remove(Items.COINS_995, 1, true)
        inventory.add(Items.KEBAB, 1, true)
        return
    } else {
        chatPlayer("Oops, I forgot to bring any money with me.")
        chatNpc("Come back when you have some.", npc = Npcs.KARIM)
    }
}

suspend fun aliMorrisane(queue: QueueTask) = with (queue) {
    chatPlayer("What do you think of Ali Morrisane?")
    chatNpc("I don't like him. He makes all these claims about being the best salesman in Gielinor, and he even has some thugs that he's hired to threaten us.")
    chatNpc("One of them came round here and threatened to cut my head off if I didn't cooperate with them!")
    chatNpc("I've also heard some rumours about them from my friend Ali in Pollnivneach...")
    chatPlayer("What sort of rumours?")
    chatNpc("Apparently he has men there as well, who have also been threatening people.\n")
    chatNpc("And it's said that they even intend to steal from the ruler of Al-Kharid himself!")
    chatPlayer("Really?")
    chatNpc("That's what I've heard. Anyway, are you sure you wouldn't like a kebab?")

    val option = options(
        "I think I'll give it a miss.",
        "Yes please."
    )

    when (option) {
        1 -> chatPlayer("I think I'll give it a miss.")
        2 -> buyKebab(this)
    }
}