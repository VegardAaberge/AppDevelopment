package com.example.speakdanish.domain

import platform.Foundation.NSUUID

actual fun randomUUID(): String = NSUUID().UUIDString()