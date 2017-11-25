package com.mantono.ask

import java.io.InputStream

inline fun <reified T> ask(q: String, default: T? = null, input: InputStream = System.`in`): T
{
	while(true)
	{
		readLine(q, default, input)?.let { return it }
	}
}

tailrec fun ask(q: String, input: InputStream = System.`in`): String
{
	val response: String = readLine(q, input = input)
	return when(response.isNotBlank())
	{
		true -> response
		false ->
		{
			println(err("This value is mandatory, please provide an input."))
			ask(q, input)
		}
	}
}

fun ask(q: String, default: String, input: InputStream = System.`in`): String
{
	val response: String = readLine(q, default, input)
	return when(response.isNotBlank())
	{
		true -> response
		false -> default
	}
}

tailrec fun askBinary(q: String, default: Boolean? = null, input: InputStream = System.`in`): Boolean
{
	val response: String = readLine(q, default?.yesOrNo()).toLowerCase()
	return when(response)
	{
		"yes", "y", "true" -> true
		"no", "n", "false" -> false
		else -> when(response.isBlank())
		{
			true -> default ?: askBinary(q, input = input)
			false ->
			{
				println(err("Please answer yes/y or no/n"))
				askBinary(q, default, input)
			}
		}
	}
}

fun Boolean.yesOrNo(): String = if(this) "yes" else "no"