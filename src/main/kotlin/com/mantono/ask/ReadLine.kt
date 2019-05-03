package com.mantono.ask

import com.github.ajalt.mordant.AnsiCode
import com.github.ajalt.mordant.TermColors
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI

private val trueColor: TermColors = TermColors(TermColors.Level.TRUECOLOR)
val bold: AnsiCode = trueColor.bold
val err: AnsiCode = trueColor.red
val reset: AnsiCode = trueColor.reset

/**
 * Write @param prompt to the given output in [Duplex] of @param stream and @return a [String],
 * either from the users input, or if the user input empty or blank, the @param default
 * value will be returned, which may be null. The readLine function is only responsible for
 * presenting the user with a prompt (output) and and reading the user's answer (input). It
 * does not apply any retry logic if the result is null or the user provide an empty input.
 * For that sort of functionality should [readLine] be used.
 */
fun readLine(
	prompt: String,
	default: String? = null,
	stream: Duplex = SystemStream
): String? {
	val def: String = if(default.isNullOrBlank()) "" else "$bold[$default]$reset"
	stream.write("$prompt$def:")
	val answer: String = stream.read()
	return if(answer.isNotBlank()) answer else default
}

/**
 * A more generic version of readLine than the String version of [readLine].
 *  This function can take any primitive type, and also [BigInteger] and [BigDecimal]
 *  and parse the use input as such.
 */
inline fun <reified T> readLine(
	prompt: String,
	default: T? = null,
	stream: Duplex = SystemStream
): T? {
	val def = default?.let { "[$default]" } ?: ""
	stream.write("$prompt$def:")
	val answer: String = stream.read()
	if(answer.isBlank()) return default

	return try {
		when(T::class) {
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
			URI::class -> URI(answer) as T
			else -> throw IllegalArgumentException("Unsupported class for parsing: ${T::class::qualifiedName}")
		}
	} catch(e: NumberFormatException) {
		stream.write(err("A ${T::class.simpleName} was expected, but '$answer' could not be parsed as such"))
		null
	}
}

/**
 * This function is an even more generic version of readLine, that can parse any type of
 * data that can be converted from a String. It does however require an extra argument, a parsing function
 * @param parse that can parse the String input into the given type [T].
 */
inline fun <T> readLine(
	prompt: String,
	default: T? = null,
	stream: Duplex = SystemStream,
	parse: (String) -> T?
): T? {
	val def = default?.let { "[$default]" } ?: ""
	stream.write("$prompt$def:")
	val answer: String = stream.read()
	return try {
		val parsedInput: T? = parse(answer)
		parsedInput ?: default
	} catch(e: Exception) {
		stream.write(err("$e\n"))
		null
	}
}