package com.mantono.ask

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

fun fakeInput(vararg input: String): Duplex
{
	return object: Duplex
	{
		private val queue: Queue<String> = LinkedList(input.toList())
		override suspend fun write(data: String): Int = data.toByteArray().size
		override suspend fun read(): String = queue.poll()
		override fun close() { }
	}
}

class AskTest
{
	@Test
	fun testReifiedReadLineLongWithTrailingL()
	{
		val input = fakeInput("10L")
		val output: Long = runBlocking { readLine<Long>("", stream = input)!! }
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineLongWithoutTrailingL()
	{
		val input = fakeInput("10")
		val output: Long = runBlocking { readLine<Long>("", stream = input)!! }
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndNoDecimalPoint()
	{
		val input = fakeInput("10f")
		val output: Float = runBlocking { readLine<Float>("", stream = input)!! }
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndDecimalPoint()
	{
		val input = fakeInput("10.0f")
		val output: Float = runBlocking { readLine<Float>("", stream = input)!! }
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndNoDecimalPoint()
	{
		val input = fakeInput("10")
		val output: Float = runBlocking { readLine<Float>("", stream = input)!! }
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndDecimalPoint()
	{
		val input = fakeInput("10.0")
		val output: Float = runBlocking { readLine<Float>("", stream = input)!! }
		assertEquals(10f, output)
	}
}