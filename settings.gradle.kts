pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "luminoso-api"

include("analytics")
include("authorization")
include("cdn")
include("tcs")
include("gateway")
include("users-management")
include("candidate-management")
include("website-management")
include("communications")
include("messages")

