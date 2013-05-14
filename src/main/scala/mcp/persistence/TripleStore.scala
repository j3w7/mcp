package mcp.persistence

import org.openrdf.repository.Repository
import org.openrdf.repository.sail.SailRepository
import org.openrdf.sail.memory.MemoryStore
import java.io.File
import org.openrdf.sail.nativerdf.NativeStore
import org.openrdf.OpenRDFException
import org.openrdf.rio.RDFFormat
import java.net.URL
import java.io.IOException

/**
 * http://www.openrdf.org/doc/sesame2/users/ch08.html#d0e686
 */
object TripleStore {

  /*
   * local repository with in-file persistence
   */
  val dataDir = new File("c:\\tmp\\repo\\")
  val myRepository = new SailRepository(new NativeStore(dataDir))
  myRepository.initialize

  val file = new File("/path/to/example.rdf")
  val baseURI = "http://example.org/example/local"

  try {
    val con = myRepository.getConnection
    try {
      con.add(file, baseURI, RDFFormat.RDFXML)

      val url = new URL("http://example.org/example/remote")
      con.add(url, url.toString(), RDFFormat.RDFXML)
    } finally {
      con.close
    }
  } catch {
    case e: Exception ⇒ e.printStackTrace
  }

}