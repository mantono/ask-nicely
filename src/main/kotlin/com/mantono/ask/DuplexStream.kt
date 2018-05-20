package com.mantono.ask

import kotlinx.coroutines.experimental.async
import java.io.Closeable
import java.io.InputStream
import java.io.OutputStream
import java.util.*

interface DuplexStream: Closeable
{
	val input: InputStream
	val output: OutputStream

	override fun close()
	{
		input.close()
		output.close()
	}

	suspend fun read(): String = async { Scanner(input).nextLine() }.await()

	suspend fun write(data: String): Int
	{
		return async {
			val bytes = data.toByteArray()
			output.write(bytes)
			bytes.size
		}.await()
	}
}

object SystemStream: DuplexStream
{
	override val input: InputStream = System.`in`
	override val output: OutputStream = System.out

	override fun close() { }
}