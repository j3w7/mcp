import org.junit.Assert.fail
import org.junit.Test
import org.junit.runners.Suite.SuiteClasses
import org.junit.runner.RunWith

package object cli {


  class TestMCP {
    @Test
    def testOverview() = {
      // mcp status
      // TODO Display an executive summary. Status mailbox, next and high priority tasks...
      fail("Not yet implemented");
    }
  }

  class TestTasks {

    @Test
    def testNextTask() = {
      // mcp next-task
      fail("Not yet implemented");
    }

    @Test
    def testUnblockTask() = {
      // schlaue Strategien Anwenden: z.B. Task in kleinere Tasks zerteilen.
      fail("Not yet implemented");
    }

    @Test
    def testAddTask() = {
      // mcp -add "TASK"
      fail("Not yet implemented");
    }
  }

  class TestMail {
    @Test
    def testZeroInbox() = {
      fail("Not yet implemented");
    }
  }

  class TestVocab {
    @Test
    def testAddVocab() = {
      // mcp learn -add "OCIS"
      fail("Not yet implemented");
    }
  }

}