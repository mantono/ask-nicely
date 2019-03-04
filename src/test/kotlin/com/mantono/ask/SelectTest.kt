package com.mantono.ask

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private enum class Color { RED, GREEN, BLUE }

class SelectTest
{
	@Test
	fun testSelectWithValidInputAndNoDefault()
	{
		val color: Color = select("Select your favorite color", stream = fakeInput("0"))
		assertEquals(Color.RED, color)
	}

	@Test
	fun testSelectWithNoValidInputAndDefaultValue()
	{
		val color: Color = select("Select your favorite color", default = Color.RED, stream = fakeInput(" "))
		assertEquals(Color.RED, color)
	}

	@Test
	fun testSelectWithNoValidInputOnFirstAttemptAndNoDefaultValue()
	{
		val input = fakeInput("xxx", "0")
		val color: Color = select("Select your favorite color", default = Color.RED, stream = input)
		assertEquals(Color.RED, color)
	}

	@Test
	fun testSelectFromListOfStringsWithNoDefaultValue()
	{
			val input = fakeInput("b")
			val options = listOf("a", "b", "c")
			val selected: String = select("Select a letter", options, stream = input)
			assertEquals("b", selected)
	}

	@Test
	fun testSelectFromListOfStringsWithADefaultValue()
	{
			val input = fakeInput(" ")
			val options = listOf("a", "b", "c")
			val selected: String = select("Select a letter", options, "b", stream = input)
			assertEquals("b", selected)
	}

	@Test
	fun testSelectFromListOfGenericsWithNoDefaultValue()
	{
			val input = fakeInput("-1", "0")
			val options = listOf(Math.E, Math.PI)
			val selected: Double = select("Select your favorite irrational number", options, stream = input)
			assertEquals(Math.E, selected)
	}

	@Test
	fun testSelectFromListOfGenericsWithADefaultValue()
	{
			val input = fakeInput(" ")
			val options = listOf(Math.E, Math.PI)
			val selected: Double = select("Select your favorite irrational number", options, default = Math.PI, stream = input)
			assertEquals(Math.PI, selected)
	}
}