package com.mantono.ask

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream

fun fakeInput(input: String)
{
	System.setIn(ByteArrayInputStream(input.toByteArray()))
}

class AskTest
{
	@Test
	fun testReifiedReadLineLongWithTrailingL()
	{
		fakeInput("10L")
		val output: Long = readLine<Long>("")!!
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineLongWithoutTrailingL()
	{
		fakeInput("10")
		val output: Long = readLine<Long>("")!!
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndNoDecimalPoint()
	{
		fakeInput("10f")
		val output: Float = readLine<Float>("")!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndDecimalPoint()
	{
		fakeInput("10.0f")
		val output: Float = readLine<Float>("")!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndNoDecimalPoint()
	{
		fakeInput("10")
		val output: Float = readLine<Float>("")!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndDecimalPoint()
	{
		fakeInput("10.0")
		val output: Float = readLine<Float>("")!!
		assertEquals(10f, output)
	}
}