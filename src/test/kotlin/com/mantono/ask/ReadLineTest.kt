package com.mantono.ask

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.math.BigInteger
import java.time.Instant

class ReadLineTest
{
	@Test
	fun readDouble()
	{
		val value: Double? = readLine<Double>("Enter your weight", stream = fakeInput("82.0"))
		assertEquals(82.0, value)
	}

	@Test
	fun readBoolean()
	{
		val value: Boolean? = readLine<Boolean>("Are you happy", stream = fakeInput("false"))
		assertFalse(value!!)
	}

	@Test
	fun readBigInteger()
	{
		val value: BigInteger? =
			readLine<BigInteger>(
					"How long is a rope",
					stream = fakeInput("1213809190381571092802108383190741028921071209389839064")
			)
		assertEquals(BigInteger("1213809190381571092802108383190741028921071209389839064"), value)
	}

	@Test
	fun readBigDecimal()
	{
		val value: BigDecimal? = readLine<BigDecimal>("", stream = fakeInput("0.1"))
		assertEquals(BigDecimal("0.1"), value)
	}

	@Test
	fun testUnsupportedClass()
	{
		assertThrows<IllegalArgumentException> {
			readLine<Instant>("Enter a date", stream = fakeInput("100"))
		}
	}

	@Test
	fun testBadNumberInputReturnsNull()
	{
		assertNull(readLine<Long>("How old is the universe", stream = fakeInput("8.9")))
	}

	@Test
	fun testReifiedReadLineLongWithTrailingL()
	{
		val input = fakeInput("10L")
		val output: Long = readLine<Long>("", stream = input)!!
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineLongWithoutTrailingL()
	{
		val input = fakeInput("10")
		val output: Long = readLine<Long>("", stream = input)!!
		assertEquals(10L, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndNoDecimalPoint()
	{
		val input = fakeInput("10f")
		val output: Float = readLine<Float>("", stream = input)!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithTrailingFAndDecimalPoint()
	{
		val input = fakeInput("10.0f")
		val output: Float = readLine<Float>("", stream = input)!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndNoDecimalPoint()
	{
		val input = fakeInput("10")
		val output: Float = readLine<Float>("", stream = input)!!
		assertEquals(10f, output)
	}

	@Test
	fun testReifiedReadLineFloatWithoutTrailingFAndDecimalPoint()
	{
		val input = fakeInput("10.0")
		val output: Float = readLine<Float>("", stream = input)!!
		assertEquals(10f, output)
	}
}