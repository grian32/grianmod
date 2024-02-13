package gg.rsmod.plugins.content.areas.alkharid.chat

val TANNING_INTERFACE = 324

onNpcOption(Npcs.ELLIS, "Trade") {
    player.openInterface(TANNING_INTERFACE, InterfaceDestination.MAIN_SCREEN)
}
