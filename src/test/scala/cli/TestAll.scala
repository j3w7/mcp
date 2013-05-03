package cli

import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@RunWith(value = classOf[org.junit.runners.Suite])
@SuiteClasses(value = Array(
  classOf[TestMail],
  classOf[TestMCP],
  classOf[TestTasks],
  classOf[TestVocab]
))
class TestAll {}