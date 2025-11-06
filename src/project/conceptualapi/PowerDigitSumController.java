package project.conceptualapi;

import java.math.BigInteger;

public class PowerDigitSumController implements ComputeControllerAPI {

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

            BigInteger power = pow(base, exponent);
            int sum = digitSum(power);

            // Return + successful response
            return new ComputeResponse(String.valueOf(sum), ComputeStatus.SUCCESS);

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

    private BigInteger pow(int base, int exponent) {
        // Use BigInteger.pow to avoid overflow
        return BigInteger.valueOf(base).pow(exponent);
    }

    private int digitSum(BigInteger value) {
        int sum = 0;
        String str = value.toString();
        for (char c : str.toCharArray()) {
            sum += (c - '0');
        }
        return sum;
    }
}
