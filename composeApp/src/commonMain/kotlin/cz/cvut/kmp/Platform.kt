package cz.cvut.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform