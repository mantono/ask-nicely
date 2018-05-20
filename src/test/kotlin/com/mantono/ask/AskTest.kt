package com.mantono.ask

import kotlinx.coroutines.experimental.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream

fun fakeInput(input: String): DuplexStream
{
	return object: DuplexStream
	{
		override val output: OutputStream = ByteArrayOutputStream()
		override val input =  ByteArrayInputStream(input.toByteArray())
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