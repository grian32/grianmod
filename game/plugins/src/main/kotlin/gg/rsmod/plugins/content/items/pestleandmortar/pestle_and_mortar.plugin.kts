package gg.rsmod.plugins.content.items.pestleandmortar

// TODO: test lol

val CRUSHABLE_ITEMS = listOf(
    CrushableItem(Items.UNICORN_HORN, Items.UNICORN_HORN_DUST),
    CrushableItem(Items.CHOCOLATE_BAR, Items.CHOCOLATE_DUST),
    CrushableItem(Items.KEBBIT_TEETH, Items.KEBBIT_TEETH_DUST), // rofl what the wiki page for pestle and mortar calls it ground kebbit teeth but u go there and its kebbit teeth dust ???? what a scam
    CrushableItem(Items.GORAK_CLAWS, Items.GORAK_CLAW_POWDER),
    CrushableItem(Items.BIRD_NEST_5075, Items.CRUSHED_NEST),
    CrushableItem(Items.DESERT_GOAT_HORN, Items.GOAT_HORN_DUST),
    CrushableItem(Items.CHARCOAL, Items.GROUND_CHARCOAL),
    CrushableItem(Items.RUNE_SHARDS, Items.RUNE_DUST),
    CrushableItem(Items.ASHES, Items.GROUND_ASHES),
    CrushableItem(Items.RAW_KARAMBWAN, Items.KARAMBWAN_PASTE),
    CrushableItem(Items.POISON_KARAMBWAN, Items.KARAMBWAN_PASTE_3153),
    CrushableItem(Items.COOKED_KARAMBWAN, Items.KARAMBWAN_PASTE_3154),
    // CrushableItem(Items.LAVA_SCALE, Items.LAVA_SCALE_SHARD), TODO: cba
    CrushableItem(Items.SUPERIOR_DRAGON_BONES, Items.CRUSHED_SUPERIOR_DRAGON_BONES)
)

// sqirk juice requires a beer glass in inventory
val SQIRK = listOf(
    CrushableItem(Items.SPRING_SQIRK, Items.SPRING_SQIRKJUICE, 4),
    CrushableItem(Items.SUMMER_SQIRK, Items.SUMMER_SQIRKJUICE, 2),
    CrushableItem(Items.AUTUMN_SQIRK, Items.AUTUMN_SQIRKJUICE, 3),
    CrushableItem(Items.WINTER_SQIRK, Items.WINTER_SQIRKJUICE, 5)
)

for (i in CRUSHABLE_ITEMS) {
    on_item_on_item(Items.PESTLE_AND_MORTAR, i.item) {
        // always uses 1 so dont need to care about amount :msncool:
        if (player.inventory.remove(i.item, 1, true).hasSucceeded()) {
            player.inventory.add(i.crushedItem,1, true)
        }
    }
}

for (i in SQIRK) {
    on_item_on_item(Items.PESTLE_AND_MORTAR, i.item) {
        if (
            player.inventory.remove(i.item, i.amountNeeded, true).hasSucceeded() &&
            player.inventory.remove(Items.BEER_GLASS, 1, true).hasSucceeded()
        ) {
            player.inventory.add(i.crushedItem,1, true)
        }
    }
}

on_item_on_item(Items.PESTLE_AND_MORTAR, Items.BLACK_MUSHROOM) {
    if (
        player.inventory.remove(Items.BLACK_MUSHROOM, 1, true).hasSucceeded() &&
        player.inventory.remove(Items.VIAL, 1, true).hasSucceeded()
    ) {
        player.inventory.add(Items.BLACK_MUSHROOM_INK, 1, true)
    }
}