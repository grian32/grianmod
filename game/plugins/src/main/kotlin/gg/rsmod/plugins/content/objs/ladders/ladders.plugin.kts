package gg.rsmod.plugins.content.objs.ladders

import com.google.gson.Gson
import java.nio.file.Files
import java.nio.file.Paths

data class Location(val start: List<Int>, val end: List<Int>)

data class Ladder(val locations: List<Location>, val id: Int)

data class LaddersJSON(val climbUp: List<Ladder>, val climbDown: List<Ladder>)

val file = Paths.get("./data/cfg/ladders/ladders.json")

lateinit var gates: LaddersJSON

Files.newBufferedReader(file).use { reader ->
    gates = Gson().fromJson(reader, LaddersJSON::class.java)
}

gates.climbUp.forEach { ladder ->
    on_obj_option(ladder.id, "climb-up") {
        ladder.locations.forEach {
            if (player.tile == Tile(it.start[0], it.start[1], it.start[2])) {
                player.moveTo(Tile(it.end[0], it.end[1], it.end[2]))
            }
        }
    }
}

gates.climbDown.forEach { ladder ->
    on_obj_option(ladder.id, "climb-down") {
        ladder.locations.forEach {
            if (player.tile == Tile(it.start[0], it.start[1], it.start[2])) {
                player.moveTo(Tile(it.end[0], it.end[1], it.end[2]))
            }
        }
    }
}