package gg.rsmod.plugins.content.inter.options

onButton(interfaceId = OptionsTab.INTERFACE_ID, component = 98) {
    /**
     Teleport inside + doors: varp 1047
     */
    if (!player.lock.canInterfaceInteract()) {
        return@onButton
    }
    player.openInterface(interfaceId = 370, dest = InterfaceDestination.TAB_AREA)
    player.setComponentText(interfaceId = 370, component = 20, text = "Number of rooms: 9")
}

onButton(interfaceId = 370, component = 21) {
    player.closeInterface(interfaceId = 370)
}