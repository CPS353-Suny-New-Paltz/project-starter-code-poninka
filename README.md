# Power Digit Sum Computation

API Diagram:

![System Diagram](assets/GithubAPI.drawio.svg)

The api will use the sum of the digits of a number raised to a power.
For example, for an input of 2^15 = 32768, the output would be 3 + 2 + 7 + 6 + 8 = 26.

## Multi-threaded Network API

There is a multi-thread implementation of the Network API. Upper bound is set to 8. Uses "Executors.newFixedThreadPool(MAX_THREADS)" to limit threads.
## Performance Tuning

**Bottleneck**: using Jconsole while running UserComputeSpeedIT showed high CPU usage this is due to PowerDigitSumController recomputed the same BigInteger power/digit sum every run.

**Fix**: Added PowerDigitSumControllerFast, which Cached successful results and looked them up in HashMap instead of recomputing.

### Benchmark Results

| Coordinator and Engine | Time (ms) |
|------------------------|-----------|
| Sequential (old)       | 19.8      |
| Sequential + fast      | 0.4       |
| Multi-threaded (old)   | 11.4      |
| Multi-threaded + fast  | 1.2       |

Sequential improvement: 97.57%. 
Multithreaded improvement: 90.53%.

Used [test/project/networkapi/UserComputeSpeedIT.java](test/project/networkapi/UserComputeSpeedIT.java) to determine results.
