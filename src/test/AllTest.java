import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import database.test.*;

@RunWith(Suite.class)
@SuiteClasses(value = {database.test.AttributeTest.class})

public class AllTests {}
