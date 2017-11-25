package com.mantono.ask

import kotlin.reflect.KClass

fun main(args: Array<String>)
{
	val long: Long = ask<Long>("adsadsa")
	val test: Test = select("Pick an enum choice", Test.TWO)
	println(test)
}

enum class Test
{
	ONE,
	TWO,
	THREE
}