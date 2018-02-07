package com.bryanherbst.gss

/**
 * Represents the valid plate configurations in PowerUp
 *
 * Represented in the same format the robots will receive.
 * LRL = left, right left. For the red alliance, that means the near switch plates and far scale plates are red
 * (near and far being relative to the scoring table)
 */
enum class PlateConfiguration {
    LLL,
    RRR,
    LRL,
    RLR
}