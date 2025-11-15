package project.conceptualapi;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PowerDigitSumControllerFast implements ComputeControllerAPI {

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
