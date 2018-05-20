package com.mantono.ask

suspend inline fun <reified T> ask(q: String, default: T? = null, stream: DuplexStream = SystemStream): T
{
	while(true)
	{
		readLine(q, default, stream)?.let { return it }
	}
}

tailrec suspend fun ask(q: String, stream: DuplexStream = SystemStream): String
{
	val response: String = readLine(q, stream = stream)
	return when(response.isNotBlank())
	{
		true -> response
		false ->
		{
			println(err("This value is mandatory, please provide an input."))
			ask(q, stream)
		}
	}
}

suspend fun ask(q: String, default: String, stream: DuplexStream = SystemStream): String
{
	val response: String = readLine(q, default, stream)
	return when(response.isNotBlank())
	{
		true -> response
		false -> default
	}
}


tailrec suspend fun ask(q: String, regex: Regex, default: String? = null, stream: DuplexStream = SystemStream): String
{
	val response: String = readLine(q, default, stream)
	default?.let { if(response.isBlank()) return default }
	return when(response.matches(regex))
	{
		true -> response
		false -> {
			stream.write(err("The provided input does not match regex $regex"))
			ask(q, regex, default, stream)
		}
	}
}

tailrec suspend fun askBinary(q: String, default: Boolean? = null, stream: DuplexStream = SystemStream): Boolean
{
	val response: String = readLine(q, default?.yesOrNo(), stream).toLowerCase()
	return when(response)
	{
		"yes", "y", "true" -> true
		"no", "n", "false" -> false
		else -> when(response.isBlank())
		{
			true -> default ?: askBinary(q, stream = stream)
			false ->
			{
				stream.write(err("Please answer yes/y or no/n"))
				askBinary(q, default, stream)
			}
		}
	}
}

fun Boolean.yesOrNo(): String = if(this) "yes" else "no"