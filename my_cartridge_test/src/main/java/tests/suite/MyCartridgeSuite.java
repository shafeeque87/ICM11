package tests.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ tests.embedded.com.example.DummyIntegrationTest.class})
public class MyCartridgeSuite
{
}
