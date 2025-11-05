# Power Digit Sum Computation

API Diagram:

![System Diagram](assets/GithubAPI.drawio.svg)

The api will use the sum of the digits of a number raised to a power.
For example, for an input of 2^15 = 32768, the output would be 3 + 2 + 7 + 6 + 8 = 26.

## Multi-threaded Network API

There is a multi-thread implementation of the Network API. Upper bound is set to 8. Uses "Executors.newFixedThreadPool(MAX_THREADS)" to limit threads.
