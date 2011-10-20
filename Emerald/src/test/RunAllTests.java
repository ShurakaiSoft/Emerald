package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	CacheTests.class,
	ControllerTests.class,
	DictionaryTests.class, 
	MutationTests.class,
	SolverTests.class,
	UnsolvedTests.class
})
public class RunAllTests {
}
