package gg.rsmod.plugins.content.areas.alkharid.shops

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

createShop("Al Kharid General Store", CoinCurrency()) {
    items[0] = ShopItem(Items.POT, 5, 1, 0, 1, 10)
    items[1] = ShopItem(Items.JUG, 2, 1, 0, 1, 100)
    items[2] = ShopItem(Items.EMPTY_JUG_PACK, 5, 182, 56, 1, 20)
    items[3] = ShopItem(Items.SHEARS, 2, 1, 0, 1, 100)
    items[4] = ShopItem(Items.BUCKET, 3, 2, 0, 1, 10)
    items[5] = ShopItem(Items.EMPTY_BUCKET_PACK, 650, 200, 0, 1, 10)
    items[6] = ShopItem(Items.BOWL, 2, 5, 1, 1, 50)
    items[7] = ShopItem(Items.CAKE_TIN, 2, 13, 4, 1, 50)
    items[8] = ShopItem(Items.TINDERBOX, 2, 1, 0, 1, 100)
    items[9] = ShopItem(Items.CHISEL, 2, 1, 0, 1, 100)
    items[10] = ShopItem(Items.HAMMER, 5, 1, 0, 1, 100)
    items[11] = ShopItem(Items.NEWCOMER_MAP, 5, 1, 0, 1, 100)
    items[12] = ShopItem(Items.SECURITY_BOOK, 5, 2, 0, 1, 100)
}