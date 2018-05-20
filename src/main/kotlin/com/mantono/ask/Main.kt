package com.mantono.ask

import kotlinx.coroutines.experimental.runBlocking
import kotlin.reflect.KClass

fun main(args: Array<String>)
{
	runBlocking {
		val long: Long = ask<Long>("Enter your age")
		val test: Test = select("Pick an enum choice", Test.TWO)
		println(test)
	}
}

enum class Test
{
	ONE,
	TWO,
	THREE
}