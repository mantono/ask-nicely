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
	fun testAskWithCorrectInputAtFirstInput()
	{
		runBlocking {
			val input = fakeInput("42")
			val number: Int = ask<Int>("Enter a digit", null, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithCorrectInputAtSecondInput()
	{
		runBlocking {
			val input = fakeInput("Not a number", "42")
			val number: Int = ask<Int>("Enter a digit", null, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithDefaultValueAsResponse()
	{
		runBlocking {
			val input = fakeInput(" ")
			val number: Int = ask<Int>("Enter a digit", 42, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithDefaultValueAsResponseOnSecondInput()
	{
		runBlocking {
			val input = fakeInput("Not a number", " ")
			val number: Int = ask<Int>("Enter a digit", 42, input)
			assertEquals(42, number)
		}
	}
}