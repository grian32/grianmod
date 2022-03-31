package gg.rsmod.plugins.content.mechanics.ladders

loadService(LadderService())

onWorldInit {
    world.getService(LadderService::class.java)?.let { service ->
        service.gates.climbUp.forEach { ladder ->
            onObjOption(ladder.id, "climb-up") {
                ladder.locations.forEach { loc ->
                    player.queue {
                        if (player.tile == Tile(loc.start[0], loc.start[1], loc.start[2])) {
                            player.animate(828)
                            wait(2)
                            player.moveTo(Tile(loc.end[0], loc.end[1], loc.end[2]))
                        }
                    }
                }
            }
        }

        service.gates.climbDown.forEach { ladder ->
            onObjOption(ladder.id, "climb-down") {
                ladder.locations.forEach { loc ->
                    player.queue {
                        if (player.tile == Tile(loc.start[0], loc.start[1], loc.start[2])) {
                            player.animate(827)
                            wait(2)
                            player.moveTo(Tile(loc.end[0], loc.end[1], loc.end[2]))
                        }
                    }
                }
            }
        }
    }
}