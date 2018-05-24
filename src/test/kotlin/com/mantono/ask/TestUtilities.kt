package com.mantono.ask

import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withTimeout

fun <T> testBlocking(func: suspend () -> T): T {
	return runBlocking {
		withTimeout(800) {
			func()
		}
	}
}