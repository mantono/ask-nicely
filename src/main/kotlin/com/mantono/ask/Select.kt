package com.mantono.ask

tailrec suspend fun select(q: String, options: List<String>, default: String? = null, stream: DuplexStream = SystemStream): String
{
	default?.let {
		if(it !in options)
			throw IllegalArgumentException("Default argument '$default' is not among the available options $options")
	}

	options.forEachIndexed { i, opt -> println("$i) $opt") }
	val answer = readLine(q, stream = stream)
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
			select(q, options, default, stream)
		}
	}
}

suspend inline fun <reified T: Enum<T>> select(q: String, default: T? = null, stream: DuplexStream = SystemStream): T
{
	val options: List<String> = enumValues<T>().map { it.name }.toList()
	val answer: String = select(q, options, default?.name, stream)
	return enumValueOf(answer)
}