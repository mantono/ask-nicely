package com.mantono.ask

import kotlinx.coroutines.experimental.async
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * Interface Duplex is an abstraction for reading user input and output,
 * mostly represented by some form of [java.io.InputStream] and [java.io.OutputStream]
 * like [System.in] and [System.out]. However, any means
 * of reading and writing strings can be used, so writing test cases can
 * be done without relying on the [java.io.PrintStream] in [System] when executing
 * test cases.
 */
interface Duplex: Closeable
{
	suspend fun read(): String
	suspend fun write(data: String): Int
}

class DuplexStream(private val input: InputStream, private val output: OutputStream): Duplex
{
	override suspend fun read(): String = async { Scanner(input).nextLine() }.await()

	override suspend fun write(data: String): Int
	{
		return async {
			val bytes = data.toByteArray()
			output.write(bytes)
			bytes.size
		}.await()
	}

	override fun close()
	{
		input.close()
		output.close()
	}
}

/**
 * A DuplexStream, communicating with [System.in] and [System.out].
 */
object SystemStream: Duplex by DuplexStream(System.`in`, System.out)
{
	override fun close() { }
}