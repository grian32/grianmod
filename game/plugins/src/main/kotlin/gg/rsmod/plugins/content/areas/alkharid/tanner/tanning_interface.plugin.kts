package gg.rsmod.plugins.content.areas.alkharid.tanner

val TANNING_INTERFACE = 324

val HIDES = listOf(
    TannableHide(Items.COWHIDE, Items.LEATHER, "Soft leather", 1),
    TannableHide(Items.COWHIDE, Items.HARD_LEATHER, "Hard leather", 3),
    TannableHide(Items.SNAKE_HIDE_7801, Items.SNAKESKIN, "Snakeskin", 20),
    TannableHide(Items.SNAKE_HIDE, Items.SNAKESKIN, "Snakeskin", 15),
    TannableHide(Items.GREEN_DRAGONHIDE, Items.GREEN_DRAGON_LEATHER, "Green d'hide", 20),
    TannableHide(Items.BLUE_DRAGONHIDE, Items.BLUE_DRAGON_LEATHER, "Blue d'hide", 20),
    TannableHide(Items.RED_DRAGONHIDE, Items.RED_DRAGON_LEATHER, "Red d'hide", 20),
    TannableHide(Items.BLACK_DRAGONHIDE, Items.BLACK_DRAGON_LEATHER, "Black d'hide", 20)
)

onInterfaceOpen(TANNING_INTERFACE) {
    HIDES.forEachIndexed { index, hide ->
        player.setComponentItem(TANNING_INTERFACE, 100 + index, hide.id, 250)
        player.setComponentText(TANNING_INTERFACE, 108 + index, hide.name)
        player.setComponentText(TANNING_INTERFACE, 116 + index, "${hide.price} coins")
        if (player.inventory.contains(hide.id) && player.inventory.getItemCount(Items.COINS_995) >= hide.hidePrice) {
            player.setComponentTextColor(TANNING_INTERFACE, 108 + index, 0, 25, 31)
            player.setComponentTextColor(TANNING_INTERFACE, 116 + index, 0, 25, 31)
        } else {
            player.setComponentTextColor(TANNING_INTERFACE, 108 + index, 31, 0, 0)
            player.setComponentTextColor(TANNING_INTERFACE, 116 + index, 31, 0, 0)
        }
    }
}

HIDES.forEachIndexed { index, hide ->
    onOpModel(TANNING_INTERFACE, 148 + index) {
        tanHide(hide, player, 1)
    }

    onOpModel(TANNING_INTERFACE, 140 + index) {
        tanHide(hide, player, 5)
    }

    onOpModel(TANNING_INTERFACE, 132 + index) {
        player.queue {
            val amount = inputInt("Enter amount:")
            tanHide(hide, player, amount)
        }
    }

    onOpModel(TANNING_INTERFACE, 124 + index) {
        tanHide(hide, player, 28)
    }
}

fun tanHide(hide: TannableHide, player: Player, amount: Int) {
    if (
        player.inventory.getItemCount(hide.id) <= amount &&
        player.inventory.getItemCount(Items.COINS_995) <= amount * hide.hidePrice
    ) {
        repeat(amount - 1) {
            if (
                player.inventory.getItemCount(Items.COINS_995) >= hide.hidePrice &&
                player.inventory.contains(hide.id)
            ) {
                player.inventory.remove(hide.id)
                player.inventory.remove(Items.COINS_995, amount = hide.hidePrice)
                player.inventory.add(hide.product)
            } else {
                return@repeat
            }
        }
    }
}
