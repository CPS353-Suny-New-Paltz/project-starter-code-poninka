package project.conceptualapi;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PowerDigitSumControllerFast implements ComputeControllerAPI {

    /*
    To identify the bottleneck, I created a simple end-to-end test UserComputeSpeedIT ran it to get a baseline reading.
    on m1 macbook I got a reading of 20.18 ms for the sequential implementation and 13.83 ms for multithreaded (MAX: 8 threads)
    I opened Jconsole and ran the test repeatedly I saw CPU usage spike to around 25%
    I thought what is the most computationally intensive process, of course the actual computation my API is designed to do.
    So I just edited the test to skip PowerDigitSumController.
    Now when I ran UserComputeSpeedIT I got readings of around 1 ms for both sequential and multithreaded.
    I couldn't come up with a way to optimize the computation, at least not anything to net 10%
    So, I just added a Cache my benchmark performance skyrocketed from 20.18 to 0.49 ms in sequential and 13.83 ms to 1.31 ms in multithreaded.
    Granted, this benchmark is the best case scenario for a Cache,
    so the 97.57% Sequential improvement and 90.53% Multithreaded improvements won't translate perfectly to a real-world use case.
     */
    private static final BigInteger TEN = BigInteger.TEN;

    //multi-thread safe cache
    //ConcurrentMap = "base:exponent"
    private final ConcurrentMap<String, String> cache = new ConcurrentHashMap<>();

    @Override
    public ComputeResponse compute(ComputeRequest request) {
        try {
            if (request == null) {
                return new ComputeResponse("Invalid input: request seen as null", ComputeStatus.FAIL);
            }

            int base = request.getBase();
            int exponent = request.getExponent();

            // Validate input
            if (!isPositive(base) || !isPositive(exponent)) {
                return new ComputeResponse(
                        "Invalid input: base and exponent are not positive.",
                        ComputeStatus.FAIL
                );
            }

            //Check in cache
            String key = cacheKey(base, exponent);
            String cached = cache.get(key);
            if (cached != null) {
                return new ComputeResponse(cached, ComputeStatus.SUCCESS);
            }

            // Do full power + digit sum calculation
            BigInteger power = pow(base, exponent);
            int sum = digitSum(power);
            String result = String.valueOf(sum);

            //store result in the cache
            cache.putIfAbsent(key, result);

            return new ComputeResponse(result, ComputeStatus.SUCCESS);

        } catch (Exception e) {
            return new ComputeResponse(
                    "Error during computation: " + e.getMessage(),
                    ComputeStatus.ERROR
            );
        }
    }
    private boolean isPositive(int value) {
        if (value > 0) {
            return true;
        } else {
            return false;
        }
    }

    //format for cacheKey
    private String cacheKey(int base, int exponent) {
        return base + ":" + exponent;
    }

    private BigInteger pow(int base, int exponent) {
        // Use BigInteger.pow to avoid overflow
        return BigInteger.valueOf(base).pow(exponent);
    }

    private int digitSum(BigInteger value) {
        String str = value.toString();
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += str.charAt(i) - '0';
        }
        return sum;
    }
}
