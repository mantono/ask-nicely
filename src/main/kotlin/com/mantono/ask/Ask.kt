package com.mantono.ask

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

val scanner = Scanner(System.`in`)

fun readLine(prompt: String, default: String? = null): String {
	val def = default?.let { " [$default]" } ?: ""
	System.out.print("$prompt$def:")
	return scanner.nextLine()
}

inline fun <reified T> readLine(prompt: String, default: T? = null): T
{
	while(true)
	{
		val def = default?.let { " [$default]" } ?: ""
		System.out.print("$prompt$def:")
		val answer: String = scanner.nextLine()
		try
		{
			return when(T::class)
			{
				Byte::class -> answer.toByte()
				Short::class -> answer.toShort()
				Int::class -> answer.toInt()
				Long::class -> answer.toLong()
				Float::class -> answer.toFloat()
				Double::class -> answer.toDouble()
				Boolean::class -> answer.toBoolean()
				BigInteger::class -> BigInteger(answer)
				BigDecimal::class -> BigDecimal(answer)
				String::class -> answer
				else -> throw IllegalArgumentException("Unsupported class for parsing: ${T::class::qualifiedName}")
			} as T
		}
		catch(e: NumberFormatException)
		{
			println("A ${T::class.simpleName} was expected, but $answer could not be parsed as such")
		}
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