import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import project.conceptualapi.PowerDigitSumController;
import project.networkapi.UserComputeAPIImplementation;
import project.processapi.DataStoreImplementation;

public class ComputeEngineIntegrationTest {

    @Test
    void testComponentInstantiation() {

        // implementation of NetworkAPI
        UserComputeAPIImplementation networkAPI = new UserComputeAPIImplementation();

        // implementation of ConceptualAPI
        PowerDigitSumController conceptualAPI = new PowerDigitSumController();

        // Test implementation of ProcessAPI (for the integration test structure check)
        DataStoreInMemory testDataStore = new DataStoreInMemory();

        // implementation of ProcessAPI (for the smoke test check)
        DataStoreImplementation realDataStore = new DataStoreImplementation();

        assertNotNull(networkAPI);
        assertNotNull(conceptualAPI);
        assertNotNull(testDataStore);
        assertNotNull(realDataStore);
    }
}

