package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	CacheTests.class,
	ControllerTests.class,
	DictionaryTests.class,
	ResultsTests.class,
	SolverTests.class,
	ProblemTests.class
})
public class RunAllTests {
}
