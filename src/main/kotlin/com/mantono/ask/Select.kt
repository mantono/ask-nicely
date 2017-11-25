package com.mantono.ask

import java.io.InputStream

tailrec fun select(q: String, options: List<String>, default: String? = null, input: InputStream = System.`in`): String
{
	default?.let {
		if(it !in options)
			throw IllegalArgumentException("Default argument '$default' is not among the available options $options")
	}

	options.forEachIndexed { i, opt -> println("$i) $opt") }
	val answer = readLine(q)
	default?.let { if(answer.isBlank()) return default }
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
			println(err("'$answer' is not a valid option"))
			select(q, options, default, input)
		}
	}
}

inline fun <reified T: Enum<T>> select(q: String, default: T? = null, input: InputStream = System.`in`): T
{
	val options: List<String> = enumValues<T>().map { it.name }.toList()
	val answer: String = select(q, options, default?.name, input)
	return enumValueOf(answer)
}