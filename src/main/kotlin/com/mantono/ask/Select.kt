package com.mantono.ask

fun select(
	q: String,
	options: List<String>,
	default: String? = null,
	stream: Duplex = SystemStream
): String {
	require(options.isNotEmpty()) { "Argument options cannot be empty" }

	default?.let {
		if(it !in options)
			throw IllegalArgumentException("Default argument '$default' is not among the available options $options")
	}

	options.forEachIndexed { i, opt -> stream.write("$i) $opt\n") }
	val answer: String = readLine(q, stream = stream) ?: ""
	default?.let { if(answer.isBlank()) return default }
	return when(answer in options) {
		true -> answer
		false -> {
			if(answer.isInt()) {
				val i: Int = answer.toInt()
				if(i in options.indices)
					return options[i]
			}
			val errorMessage: String = if(answer.isBlank()) {
				"Input was blank\n"
			} else {
				"'$answer' is not a valid option\n"
			}
			stream.write(err(errorMessage))
			select(q, options, default, stream)
		}
	}
}

tailrec fun <T> select(
	q: String,
	options: List<T>,
	default: T? = null,
	stream: Duplex = SystemStream
): T {
	require(options.isNotEmpty()) { "Argument options cannot be empty" }
	
	default?.let {
		if(it !in options)
			throw IllegalArgumentException("Default argument '$default' is not among the available options $options")
	}

	options.forEachIndexed { i, opt -> stream.write("$i) $opt\n") }
	val answer: String = readLine(q, stream = stream) ?: ""
	default?.let { if(answer.isBlank()) return default }

	if(answer.isInt()) {
		val i: Int = answer.toInt()
		if(i in options.indices)
			return options[i]
	}
	val errorMessage: String = if(answer.isBlank()) {
		"Input was blank\n"
	} else {
		"'$answer' is not a valid option\n"
	}
	stream.write(err(errorMessage))
	return select(q, options, default, stream)
}

inline fun <reified T: Enum<T>> select(
	q: String,
	default: T? = null,
	stream: Duplex = SystemStream
): T {
	val options: List<String> = enumValues<T>().map { it.name }.toList()
	val answer: String = select(q, options, default?.name, stream)
	return enumValueOf(answer)
}