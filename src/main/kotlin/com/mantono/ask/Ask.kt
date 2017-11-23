package com.mantono.ask

import com.github.ajalt.mordant.AnsiCode
import com.github.ajalt.mordant.TermColors
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

val scanner = Scanner(System.`in`)

val bold: AnsiCode = TermColors(TermColors.Level.ANSI16).bold
val reset: AnsiCode = TermColors(TermColors.Level.ANSI16).reset

fun readLine(prompt: String, default: String? = null): String
{
	val def = default?.let { " [$default]" } ?: ""
	System.out.print("$prompt$def:")
	return scanner.nextLine()
}

inline fun <reified T> readLine(prompt: String, default: T? = null): T?
{
	val def = default?.let { "[$default]" } ?: ""
	System.out.print("$prompt$def:")
	val answer: String = scanner.nextLine()
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
		println("A ${T::class.simpleName} was expected, but $answer could not be parsed as such")
		null
	}
}

inline fun <reified T> ask(q: String, default: T? = null): T
{
	while(true)
	{
		readLine(q, default)?.let { return it }
	}
}

tailrec fun ask(q: String): String
{
	val response: String = readLine(q)
	return when(response.isNotBlank())
	{
		true -> response
		false ->
		{
			println("This value is mandatory, please provide an input.")
			ask(q)
		}
	}
}

fun ask(q: String, default: String): String
{
	val response: String = readLine(q, default)
	return when(response.isNotBlank())
	{
		true -> response
		false -> default
	}
}

tailrec fun ask(q: String, options: List<String>, default: String? = null): String
{
	options.forEachIndexed { i, opt -> println("$i) $opt") }
	val answer = readLine(q)
	return when(answer in options)
	{
		true -> answer
		false ->
		{
			if(answer.isInt())
			{
				val i: Int = answer.toInt()
				if(i in options.indices)
					return options[i]
			}
			println("'$answer' is not a valid option")
			ask(q, options, default)
		}
	}
}

//fun ask(q: String, options: List<String>, default: String): String = TODO()

tailrec fun askBinary(q: String, default: Boolean? = null): Boolean
{
	val response: String = readLine(q, default?.yesOrNo()).toLowerCase()
	return when(response)
	{
		"yes", "y", "true" -> true
		"no", "n", "false" -> false
		else -> when(response.isBlank())
		{
			true -> default ?: askBinary(q)
			false ->
			{
				println("Please answer yes/y or no/n")
				askBinary(q, default)
			}
		}
	}
}

fun Boolean.yesOrNo(): String = if(this) "yes" else "no"