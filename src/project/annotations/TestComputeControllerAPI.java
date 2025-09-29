import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import project.annotations.*;

public class TestComputeControllerAPI {
    @Test
    public void testPowerDigitSum() {
        ComputeControllerAPI mockcomp = Mockito.mock(ComputeControllerAPI.class);
        ComputeRequest req = new ComputeRequest(2, 15); // 2^15 = 32768 sum = 26

        Mockito.when(mockcomp.compute(req))
                .thenReturn(new ComputeResponse("26", ComputeStatus.SUCCESS));

        ComputeResponse resp = mockcomp.compute(req);
        assertEquals("26", resp.getResult());
    }
}
