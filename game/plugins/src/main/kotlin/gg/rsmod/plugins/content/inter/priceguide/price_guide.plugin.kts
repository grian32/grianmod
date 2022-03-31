package gg.rsmod.plugins.content.inter.priceguide

onButton(interfaceId = 387, component = 19) {
    if (!player.lock.canInterfaceInteract()) {
        return@onButton
    }
    PriceGuide.open(player)
}

onInterfaceClose(interfaceId = PriceGuide.INTERFACE_ID) {
    PriceGuide.close(player)
    player.closeInputDialog()
}

onButton(interfaceId = PriceGuide.TAB_INTERFACE_ID, component = 0) {
    player.queue(TaskPriority.WEAK) {
        PriceGuide.add(this, player.getInteractingSlot(), player.getInteractingOption())
    }
}

onButton(interfaceId = PriceGuide.INTERFACE_ID, component = 2) {
    player.queue(TaskPriority.WEAK) {
        PriceGuide.remove(this, player.getInteractingSlot(), player.getInteractingOption())
    }
}

onButton(interfaceId = PriceGuide.INTERFACE_ID, component = 10) {
    PriceGuide.depositInventory(player)
}

onButton(interfaceId = PriceGuide.INTERFACE_ID, component = 5) {
    player.queue(TaskPriority.WEAK) {
        val item = searchItemInput("Select an item to ask about its price:")
        PriceGuide.search(player, item)
    }
}