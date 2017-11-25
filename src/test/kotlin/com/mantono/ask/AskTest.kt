package com.mantono.ask

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.InputStream

fun fakeInput(input: String) = ByteArrayInputStream(input.toByteArray())

class AskTest
{
	@Test
	fun testReifiedReadLineLongWithTrailingL()
	{
		val input = fakeInput("10L")
		val output: Long = readLine<Long>("", input = input)!!
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineLongWithoutTrailingL()
	{
		val input = fakeInput("10")
		val output: Long = readLine<Long>("", input = input)!!
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndNoDecimalPoint()
	{
		val input = fakeInput("10f")
		val output: Float = readLine<Float>("", input = input)!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndDecimalPoint()
	{
		val input = fakeInput("10.0f")
		val output: Float = readLine<Float>("", input = input)!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndNoDecimalPoint()
	{
		val input = fakeInput("10")
		val output: Float = readLine<Float>("", input = input)!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndDecimalPoint()
	{
		val input = fakeInput("10.0")
		val output: Float = readLine<Float>("", input = input)!!
		assertEquals(10f, output)
	}
}