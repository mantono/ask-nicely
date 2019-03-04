package com.mantono.ask

private val intRegex = Regex("^\\d+\$")
fun String.isInt(): Boolean = intRegex.matches(this)

private val longRegex = Regex("^\\d+L?\$")
fun String.isLong(): Boolean = longRegex.matches(this)

private val floatRegex = Regex("^\\d+(\\.?\\d*)f\$")
fun String.isFloat(): Boolean = floatRegex.matches(this)

private val doubleRegex = Regex("^\\d+(\\.?\\d*)\$")
fun String.isDouble(): Boolean = doubleRegex.matches(this)

fun String.isByte(): Boolean {
	if(!isInt()) return false
	return toInt() in Byte.MIN_VALUE .. Byte.MAX_VALUE
}

fun String.isShort(): Boolean {
	if(!isInt()) return false
	return toInt() in Short.MIN_VALUE .. Short.MAX_VALUE
}

fun CharSequence?.isNotNullOrBlank(): Boolean = this != null && this.isNotBlank()