tasks.register("clean") {
    dependsOn(gradle.includedBuilds.map { it.task(":clean") })
}

tasks.register("check") {
    dependsOn(gradle.includedBuilds.map { it.task(":check") })
}
