package project.conceptualapi;

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

            long power = pow(base, exponent);
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

    private long pow(int base, int exponent) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }

    private int digitSum(long value) {
        int sum = 0;
        String str = Long.toString(value);
        for (char c : str.toCharArray()) {
            sum += (c - '0');
        }
        return sum;
    }
}
