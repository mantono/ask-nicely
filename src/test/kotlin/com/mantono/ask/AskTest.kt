package com.mantono.ask

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Instant
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
		testBlocking {
			val input = fakeInput("42")
			val number: Int = ask<Int>("Enter a digit", null, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithCorrectInputAtSecondInput()
	{
		testBlocking {
			val input = fakeInput("Not a number", "42")
			val number: Int = ask<Int>("Enter a digit", null, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithDefaultValueAsResponse()
	{
		testBlocking {
			val input = fakeInput(" ")
			val number: Int = ask<Int>("Enter a digit", 42, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithDefaultValueAsResponseOnSecondInput()
	{
		testBlocking {
			val input = fakeInput("Not a number", " ")
			val number: Int = ask<Int>("Enter a digit", 42, input)
			assertEquals(42, number)
		}
	}

	@Test
	fun testAskWithParseFunction()
	{
		testBlocking {
			val input = fakeInput("1527199361")
			val timestamp: Instant = ask<Instant>("What time is it?", stream = input) { userInput ->
				Instant.ofEpochSecond(userInput.toLong())
			}
			assertEquals(1527199361L, timestamp.epochSecond)
		}
	}

	@Test
	fun testAskWithParseFunctionAndWrongInputOnFirstAttempt()
	{
		testBlocking {
			val input = fakeInput("q", "1527199361")
			val timestamp: Instant = ask<Instant>("What time is it?", stream = input) { userInput ->
				Instant.ofEpochSecond(userInput.toLong())
			}
			assertEquals(1527199361L, timestamp.epochSecond)
		}
	}
}