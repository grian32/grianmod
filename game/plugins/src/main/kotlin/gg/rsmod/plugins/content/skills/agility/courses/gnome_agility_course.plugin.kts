package gg.rsmod.plugins.content.skills.agility.courses

onObjOption(Objs.LOG_BALANCE_23145, "walk-across") {
    player.queue {
        val originalAnims = player.anims
        player.updateAnims(-1, 762, 762, 762, 763, 762, 762)
        player.message("You walk carefully across the slippery log...", ChatMessageType.SPAM)
        // start tile: 2474, 3436, 0
        // end tile: 2474, 3429, 0
        // direction: south
        player.lock()
        player.walkTo(Tile(2474, 3429), MovementQueue.StepType.FORCED_WALK, false)
        wait(8)
        player.unlock()
        player.updateAnims(originalAnims)
        player.message("...You make it safely to the other side.", ChatMessageType.SPAM)
        player.addXp(Skills.AGILITY, 7.5)
    }
}
