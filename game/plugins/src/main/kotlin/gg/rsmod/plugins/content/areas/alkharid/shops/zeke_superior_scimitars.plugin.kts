package gg.rsmod.plugins.content.areas.alkharid.shops

import gg.rsmod.plugins.content.mechanics.shops.CoinCurrency

createShop("Zeke's Superior Scimitar", CoinCurrency()) {
    items[0] = ShopItem(Items.BRONZE_SCIMITAR, 5, 32, 17, 5, 100)
    items[1] = ShopItem(Items.IRON_SCIMITAR, 3, 112, 61, 3, 200)
    items[2] = ShopItem(Items.STEEL_SCIMITAR, 2, 400, 220, 2, 600)
    items[3] = ShopItem(Items.MITHRIL_SCIMITAR, 1, 1040, 572, 1, 4000)
}