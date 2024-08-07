package gg.rsmod.plugins.content.areas.alkharid.shops

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

createShop("Ranael's Super Skirt Store.", CoinCurrency()) {
    items[0] = ShopItem(Items.BRONZE_PLATESKIRT, 5, 80, 52, 1, 100)
    items[1] = ShopItem(Items.IRON_PLATESKIRT, 3, 280, 182, 1, 400)
    items[2] = ShopItem(Items.STEEL_PLATESKIRT, 2, 1000, 650, 1, 900)
    items[3] = ShopItem(Items.BLACK_PLATESKIRT, 1, 1920, 1248, 1, 1200)
    items[4] = ShopItem(Items.MITHRIL_PLATESKIRT, 1, 2600, 1690, 1, 2000)
    items[5] = ShopItem(Items.ADAMANT_PLATESKIRT, 1, 6400, 4160, 1, 13000)
}