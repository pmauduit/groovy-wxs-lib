package fr.beneth.wxslib.georchestra

import fr.beneth.wxslib.georchestra.Instance
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by pmauduit on 10/27/15.
 */
public class InstanceTest {

    @Test
    void testGetInstances() {
        def instances = Instance.loadGeorchestraInstances()
        assertTrue(instances.size() > 0)
    }
}
