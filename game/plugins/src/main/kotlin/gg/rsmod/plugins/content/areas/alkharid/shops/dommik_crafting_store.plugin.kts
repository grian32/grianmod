package gg.rsmod.plugins.content.areas.alkharid.shops

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

createShop("Dommik's Crafting Store.", CoinCurrency()) {
    items[0] = ShopItem(Items.CHISEL, 2, 1 ,0, 1, 100)
    items[1] = ShopItem(Items.RING_MOULD, 4, 5, 3, 1, 100)
    items[2] = ShopItem(Items.NECKLACE_MOULD, 2, 5, 3, 1, 100)
    items[3] = ShopItem(Items.AMULET_MOULD, 2, 1, 0, 1, 100)
    items[4] = ShopItem(Items.NEEDLE, 3, 1, 0, 1, 100)
    items[5] = ShopItem(Items.THREAD, 100, 1, 0, 1, 5)
    items[6] = ShopItem(Items.HOLY_MOULD, 3, 5, 3, 1, 15)
    items[7] = ShopItem(Items.SICKLE_MOULD, 6, 10, 6, 1, 10)
    items[8] = ShopItem(Items.TIARA_MOULD, 10, 100, 65, 1, 10)
    items[9] = ShopItem(Items.BOLT_MOULD, 10, 25, 16, 1, 10)
    items[10] = ShopItem(Items.BRACELET_MOULD, 5, 5, 3, 1, 100)
}