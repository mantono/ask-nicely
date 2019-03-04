package com.mantono.ask

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
interface Duplex: Closeable {
	fun read(): String
	fun write(data: String): Int
}

class DuplexStream(private val input: InputStream, private val output: OutputStream): Duplex {
	override fun read(): String = Scanner(input).nextLine()

	override fun write(data: String): Int {
		val bytes = data.toByteArray()
		output.write(bytes)
		return bytes.size
	}

	override fun close() {
		input.close()
		output.close()
	}
}

/**
 * A DuplexStream, communicating with [System.in] and [System.out].
 */
object SystemStream: Duplex by DuplexStream(System.`in`, System.out) {
	override fun close() { }
}