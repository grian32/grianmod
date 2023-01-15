package gg.rsmod.plugins.content.items.zulrah

val zulrahItems = listOf(Items.TANZANITE_FANG, Items.MAGIC_FANG, Items.SERPENTINE_VISAGE,
        Items.SERPENTINE_HELM_UNCHARGED, Items.TOXIC_BLOWPIPE_EMPTY)

for (i in zulrahItems) {
    onItemOption(i, "Dismantle") {
        val itemName = player.world.definitions.get(ItemDef::class.java, i).name

        player.queue {
            if (options("Yes", "No", title = "<col=800000>Dismantle $itemName</col>") == 1) {
                val removal = player.inventory.remove(i, 1, true)

                if (removal.hasSucceeded()) {
                    player.inventory.add(Items.ZULRAHS_SCALES, 20_000, true)
                }
            }
        }
    }
}