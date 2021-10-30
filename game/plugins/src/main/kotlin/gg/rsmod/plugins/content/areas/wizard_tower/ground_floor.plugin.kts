package gg.rsmod.plugins.content.areas.wizard_tower

val MESSAGES = listOf(
    "Living with a Wizard Husband - a Housewife's Story",
    "How to become the Ultimate Wizard of the Universe",
    "Fire, Earth and Water - What's it all about?",
    "So you think you're a Mage? Volume 28"
)

on_obj_option(Objs.BOOKCASE_12539, "search") {
    player.queue {
        this.messageBox("There's a large selection of books, the majority of which look fairly old. Some very strange names... You pick one at random :")
        this.messageBox("'${MESSAGES.random()}'")
        this.messageBox("Interesting...")
    }
}


spawn_npc(Npcs.PROFESSOR_ONGLEWIP, 3112, 3158, 0, 5)

on_npc_option(Npcs.PROFESSOR_ONGLEWIP, "talk-to") {
    player.queue {
        chatPlayer("Do you live here too?", title = player.username)
        chatNpc(
            "Oh no, I come from the Gnome Stronghold. I've been sent here by King Narnode to learn about human magics.",
            npc = Npcs.PROFESSOR_ONGLEWIP,
            title = "Professor Onglewip"
        )
        chatPlayer("So where's this Gnome Stronghold?", title = player.username)
        chatNpc(
            "It's in the North West of the continent - a long way away. You should visit us there some time. The food's great, and the company's delightful.",
            npc = Npcs.PROFESSOR_ONGLEWIP,
            title = "Professor Onglewip"
        )
        chatPlayer("I'll try and make time for it. Sounds like a nice place.", title = player.username)
        chatNpc(
            "Well, it's full of gnomes. How much nicer could it be?",
            npc = Npcs.PROFESSOR_ONGLEWIP,
            title = "Professor Onglewip"
        )
    }
}

spawn_item(Items.LEATHER_BOOTS, 1, 3111, 3159)
listOf(Pair(3106, 3160), Pair(3106, 3159), Pair(3105, 3159)).forEach {
    spawn_item(Items.LOGS, 1, it.first, it.second)
}
