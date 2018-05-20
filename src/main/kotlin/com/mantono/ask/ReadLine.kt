package com.mantono.ask

import com.github.ajalt.mordant.AnsiCode
import com.github.ajalt.mordant.TermColors
import java.math.BigDecimal
import java.math.BigInteger

private val trueColor: TermColors = TermColors(TermColors.Level.TRUECOLOR)
val bold: AnsiCode = trueColor.bold
val err: AnsiCode = trueColor.red
val reset: AnsiCode = trueColor.reset

suspend fun readLine(prompt: String, default: String? = null, stream: Duplex = SystemStream): String
{
	val def = default?.let { if(default.isNotBlank()) "$bold[$default]$reset" } ?: ""
	stream.write("$prompt$def:")
	return stream.read()
}

suspend inline fun <reified T> readLine(prompt: String, default: T? = null, stream: Duplex = SystemStream): T?
{
	val def = default?.let { "[$default]" } ?: ""
	stream.write("$prompt$def:")
	val answer: String = stream.read()
	if(answer.isBlank()) return default

	return try
	{
		when(T::class)
		{
			Byte::class -> answer.toByte() as T
			Short::class -> answer.toShort() as T
			Int::class -> answer.toInt() as T
			Long::class -> answer.removeSuffix("L").toLong() as T
			Float::class -> answer.toFloat() as T
			Double::class -> answer.toDouble() as T
			Boolean::class -> answer.toBoolean() as T
			BigInteger::class -> BigInteger(answer) as T
			BigDecimal::class -> BigDecimal(answer) as T
			String::class -> answer as T
			else -> throw IllegalArgumentException("Unsupported class for parsing: ${T::class::qualifiedName}")
		}
	}
	catch(e: NumberFormatException)
	{
		stream.write(err("A ${T::class.simpleName} was expected, but '$answer' could not be parsed as such"))
		null
	}
}