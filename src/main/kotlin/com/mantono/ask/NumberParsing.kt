package com.mantono.ask

val intRegex = Regex("^\\d+\$")
fun String.isInt(): Boolean = intRegex.matches(this)

val longRegex = Regex("^\\d+L?\$")
fun String.isLong(): Boolean = longRegex.matches(this)

val floatRegex = Regex("^\\d+(\\.?\\d*)f\$")
fun String.isFloat(): Boolean = floatRegex.matches(this)

val doubleRegex = Regex("^\\d+(\\.?\\d*)\$")
fun String.isDouble(): Boolean = doubleRegex.matches(this)

fun String.isByte(): Boolean
{
	if(!isInt()) return false
	return toInt() in Byte.MIN_VALUE .. Byte.MAX_VALUE
}

fun String.isShort(): Boolean
{
	if(!isInt()) return false
	return toInt() in Short.MIN_VALUE .. Short.MAX_VALUE
}