/*
 * Copyright (c) 2020, Mark <https://github.com/Mark7625>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.runelist.defs.items

class ItemDefinition {

    var id = 0
    var name = ""
    var incomplete = false
    var members = false
    var tradeable = false
    var tradeable_on_ge = false
    var stackable = false
    var stacked = false
    var noted = false
    var noteable = false
    var linked_id_item = 0
    var linked_id_noted = 0
    var linked_id_placeholder = 0
    var equipable = false
    var equipable_by_player = false
    var equipable_weapon = false
    var cost = 0
    var lowalch = 0
    var highalch = 0
    var weight = 0.0
    var buy_limit = 0
    var quest_item = false
    var release_date = ""
    var duplicate = false
    var examine = ""
    var wiki_name = ""
    var wiki_url = ""
    var equipment: Equipment = Equipment()

    companion object {

        var DEFINITIONS = HashMap<Int, ItemDefinition>()

        fun forId(item: Int): ItemDefinition {
            return DEFINITIONS[item]!!
        }
    }
}

class Equipment {

    var attack_stab = 0
    var attack_slash = 0
    var attack_crush = 0
    var attack_magic = 0
    var attack_ranged = 0
    var defence_stab = 0
    var defence_slash = 0
    var defence_crush = 0
    var defence_magic = 0
    var defence_ranged = 0
    var melee_strength = 0
    var ranged_strength = 0
    var magic_damage = 0
    var prayer = 0
    var slot = ""
    var requirements: Requirements = Requirements()
}

class Requirements {
    var attack = 0
    var defence = 0
    var strength = 0
    var magic = 0
    var ranged = 0
    var slayer = 0
}
