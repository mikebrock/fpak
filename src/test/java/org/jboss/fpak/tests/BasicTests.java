package org.jboss.fpak.tests;

import junit.framework.TestCase;
import org.jboss.fpak.Runner;

/**
 * @author Mike Brock .
 */
public class BasicTests extends TestCase {
    public void testSimpleGeneration() {
        Runner runner = new Runner();

        runner.setWorkingDirectory(System.getProperty("java.io.tmpdir"));
        runner.run("src/test/resources/test1", "--name", "my.fun.package.Hello");
    }
}
