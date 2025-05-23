package com.inkapplications.glassconsole

import kimchi.logger.KimchiLogger
import regolith.init.InitRunnerCallbacks
import regolith.init.InitTarget
import regolith.init.Initializer
import regolith.processes.daemon.Daemon
import regolith.processes.daemon.DaemonCallbacks
import regolith.processes.daemon.DaemonRunAttempt
import kotlin.reflect.KClass

internal class KimchiRegolithAdapter(
    private val logger: KimchiLogger
): InitRunnerCallbacks, DaemonCallbacks {
    override fun onComplete() {
        logger.info("Initialization complete")
    }

    override fun onInitializerAwaitingTarget(initializer: Initializer, target: KClass<out InitTarget>) {
        logger.debug("[INIT_WAIT] ${initializer::class.simpleName} -> ${target.simpleName}")
    }

    override fun onInitializerComplete(initializer: Initializer) {
        logger.debug("[INIT_COMPLETE] ${initializer::class.simpleName}")
    }

    override fun onInitializerError(initializer: Initializer, error: Throwable) {
        logger.error("[INIT_ERROR] ${initializer::class.simpleName}", error)
    }

    override fun onTargetReached(target: InitTarget) {
        logger.debug("[TARGET] ${target::class.simpleName}")
    }

    override fun onDaemonError(daemon: Daemon, error: Throwable) {
        logger.error("[DAEMON_ERROR] ${daemon::class.simpleName}", error)
    }

    override fun onDaemonStarted(daemon: Daemon) {
        logger.debug("[DAEMON_START] ${daemon::class.simpleName}")
    }

    override fun onDaemonRestart(daemon: Daemon, previousAttempts: List<DaemonRunAttempt>) {
        logger.warn("[DAEMON_RESTART] ${daemon::class.simpleName} (attempt ${previousAttempts.size + 1})")
    }

    override fun onPanic(daemon: Daemon, error: Throwable) {
        logger.error("[DAEMON_PANIC] ${daemon::class.simpleName}", error)
    }
}
