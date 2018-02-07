package com.bryanherbst.gss.extension

import java.util.*

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) +  start