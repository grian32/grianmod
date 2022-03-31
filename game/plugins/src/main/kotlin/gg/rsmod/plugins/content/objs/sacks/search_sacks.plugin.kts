package gg.rsmod.plugins.content.objs.sacks

private val SACKS = setOf(Objs.SACKS_365)

SACKS.forEach { sack ->
    onObjOption(obj = sack, option = "search") {
        player.message("There's nothing interesting in these sacks.")
    }
}