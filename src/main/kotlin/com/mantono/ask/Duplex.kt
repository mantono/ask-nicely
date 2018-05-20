package com.mantono.ask

import kotlinx.coroutines.experimental.async
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.util.*

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

object SystemStream: Duplex by DuplexStream(System.`in`, System.out)
{
	override fun close() { }
}