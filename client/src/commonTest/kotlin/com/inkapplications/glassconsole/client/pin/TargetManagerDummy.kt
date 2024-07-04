package com.inkapplications.glassconsole.client.pin

import regolith.init.InitTarget
import regolith.init.TargetManager
import kotlin.reflect.KClass

object TargetManagerDummy: TargetManager {
    override suspend fun <T : InitTarget> awaitTarget(targetClass: KClass<T>): T = TODO()
    override fun postTarget(target: InitTarget) = TODO()
}
