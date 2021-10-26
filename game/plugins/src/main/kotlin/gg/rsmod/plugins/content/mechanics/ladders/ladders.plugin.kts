package gg.rsmod.plugins.content.mechanics.ladders

load_service(LadderService())

on_world_init {
    world.getService(LadderService::class.java)?.let { service ->
        service.gates.climbUp.forEach { ladder ->
            on_obj_option(ladder.id, "climb-up") {
                ladder.locations.forEach {
                    if (player.tile == Tile(it.start[0], it.start[1], it.start[2])) {
                        player.moveTo(Tile(it.end[0], it.end[1], it.end[2]))
                    }
                }
            }
        }

        service.gates.climbDown.forEach { ladder ->
            on_obj_option(ladder.id, "climb-down") {
                ladder.locations.forEach {
                    if (player.tile == Tile(it.start[0], it.start[1], it.start[2])) {
                        player.moveTo(Tile(it.end[0], it.end[1], it.end[2]))
                    }
                }
            }
        }
    }
}