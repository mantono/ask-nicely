# ask-nicely
_A small library for easier creation of user interactive terminal input_

## What
"Ask nicely" is a library for the JVM, written in Kotlin, that simplifies the
process of user input/output via the command line. This is done by inferred typing and
automatic parsing of inputs (as much that is possible, and more importantly, that
can be done safely in a statically typed language) so that writing applications
that depends on user input via a terminal can be done as swiftly as possible.

## Why
Without any help from a third party library, reading and processing user input
through the command line can be quite cumbersome in Java or Kotlin.
This is an example of what is required to print some output, read the user input,
validate it and parse the input as a `Double` in Kotlin.

```
fun readWeight(): Double?
{
    System.out.println("What is your weight: ")
    val input: String = Scanner(System.`in`).nextLine()
    return try
    {
        input.toDouble()
    }
    catch(e: NumberFormatException)
    {
        // Handle exception
        null
    }
}
```

And this is a quite primitive example, without any logic for retries when we
encounter bad input from the user (such as an empty String, not a valid Double, and etc).
Having just one occasion of this could be acceptable, but if you are writing an application
that depends heavily on user input and output, then you would probably want
something less noisy. What if it just could be as simple as
`val weight: Double = ask("What is your weight")`

## How

Ask nicely contains primarily three functions (and variants of these three) that
make handling user to terminal input/output easier.
1. ask - Ask for an input from the user, accept any input as long as it conforms to
the given type
2. select - Ask for an input, and let the user select an alternative from a limited
set of options
3. readLine - A convenience function used by _ask_ and _select_, without the 
error handling that these two have, for reading user input
