pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
rootProject.name = "luminoso-api"

include(":api-authorization")
include(":common-jpa")
include("analytics")
include("authorization")
include("cdn")
include("tcs")
include("gateway")
include("users-management")
include("candidate-management")
include("website-management")
include("communications")

