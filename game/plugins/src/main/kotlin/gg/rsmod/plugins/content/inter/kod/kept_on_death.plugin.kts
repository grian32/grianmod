package gg.rsmod.plugins.content.inter.kod

onButton(interfaceId = 387, component = 21) {
    if (!player.lock.canInterfaceInteract()) {
        return@onButton
    }
    KeptOnDeath.open(player, world)
}

onInterfaceClose(interfaceId = KeptOnDeath.COMPONENT_ID) {
    /**
     * Have to resend inventory when this interface is closed as it sent a 'fake'
     * inventory container to the tab area, which can mess up other tab area
     * interfaces such as equipment stats.
     */
    player.inventory.dirty = true
}