package com.mantono.ask

/**
 * Ask a user for input. Unlike [readLine], this function can never return
 * null because this function will not return until it has received a non-null
 * response.
 * @param q gives the user a prompt for the input
 * @param default is a default value (of type [T]) that is returned if the user enters no
 * input or only a blank input (only spaces).
 * @param stream is a [Duplex], representing a means of input and output for
 * communicating with the user. This Duplex is mostly represented by some form
 * of [InputStream] and [OutputStream]. The default value is a [SystemStream].
 * @return a value, which is either the default value or the user's response
 */
suspend inline fun <reified T> ask(q: String, default: T? = null, stream: Duplex = SystemStream): T
{
	while(true)
	{
		readLine(q, default, stream)?.let { return it }
	}
}

suspend inline fun <reified T: Comparable<T>> ask(q: String, range: ClosedRange<T>, default: T? = null, stream: Duplex = SystemStream): T
{
	default?.let {
		require(it in range) { "Default argument $default is outside range $range" }
	}
	while(true)
	{
		readLine(q, default, stream)?.let { if(it in range) return it }
	}
}

suspend inline fun <reified T> ask(q: String, default: T? = null, stream: Duplex = SystemStream, parse: (String) -> T?): T
{
	while(true)
	{
		readLine(q, default, stream, parse)?.let { return it }
	}
}

/**
 * Ask a user for a String input. Unlike [readLine], this function can never return
 * null because this function will not return until it has received a non-null
 * response.
 * @param q gives the user a prompt for the input
 * @param default is a default value that is returned if the user enters no
 * input or only a blank input (only spaces).
 * @param stream is a [Duplex] for communicating with the user
 * @return a value, which is either the default value or the user's response
 */
tailrec suspend fun ask(q: String, default: String? = null, stream: Duplex = SystemStream): String
{
	val response: String = readLine(q, default, stream = stream) ?: ""

	return when
	{
		response.isNotBlank() -> response
		else ->
		{
			stream.write(err("This value is mandatory, please provide an input.\n"))
			ask(q, default, stream)
		}
	}
}

/**
 * Ask a user for input that matches a [Regex]. Unlike [readLine], this function can never return
 * null because this function will not return until it has received a non-null
 * response.
 * @param q gives the user a prompt for the input
 * @param regex, a regular expression which the user's input must match. If it does not match
 * @param default is a default value (of type [T]) that is returned if the user enters no
 * input or only a blank input (only spaces).
 * @param stream is a [Duplex] for communicating with the user
 * @return a value, which is either the default value or the user's response
 */
tailrec suspend fun ask(q: String, regex: Regex, default: String? = null, stream: Duplex = SystemStream): String
{
	val response: String = readLine(q, default, stream) ?: ""
	return when(response.matches(regex))
	{
		true -> response
		false -> {
			stream.write(err("The provided input does not match regex $regex\n"))
			ask(q, regex, default, stream)
		}
	}
}

/**
 * Ask a user for binary input, like yes/no, true/false. Input from the user
 * is not case sensitive, and will be converted to a [Boolean].
 * Unlike [readLine], this function can never return
 * null because this function will not return until it has received a non-null
 * response.
 * @param q gives the user a prompt for the input
 * @param default is a default value that is returned if the user enters no
 * input or only a blank input (only spaces).
 * @param stream is a [Duplex] for communicating with the user
 * @return a value, which is either the default value or the user's response
 */
tailrec suspend fun askBinary(q: String, default: Boolean? = null, stream: Duplex = SystemStream): Boolean
{
	val response: String = readLine(q, default?.yesOrNo(), stream)?.toLowerCase() ?: ""
	return when(response)
	{
		"yes", "y", "true" -> true
		"no", "n", "false" -> false
		else -> when(response.isBlank())
		{
			true -> default ?: askBinary(q, stream = stream)
			false ->
			{
				stream.write(err("Please answer yes/y or no/n\n"))
				askBinary(q, default, stream)
			}
		}
	}
}

fun Boolean.yesOrNo(): String = if(this) "yes" else "no"