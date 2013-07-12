package mcp.system.file

import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.FileVisitResult
import java.nio.file.Files.isDirectory
import java.nio.file.Files.walkFileTree
import java.nio.file.LinkOption
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.StandardWatchEventKinds
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.StandardWatchEventKinds.ENTRY_DELETE
import java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.attribute.BasicFileAttributes

import scala.collection.JavaConverters.asScalaBufferConverter
import scala.collection.mutable.HashMap
import scala.util.control.Breaks.break
import scala.util.control.Breaks.breakable

class DirectoryWatcher(
  val path: Path,
  val recursive: Boolean) extends Runnable {

  val watchService = path.getFileSystem.newWatchService
  val keys = new HashMap[WatchKey, Path]
  var trace = false

  /**
    * Print an event
    */
  def printEvent(event: WatchEvent[_]) {
    val kind = event.kind
    val event_path = event.context.asInstanceOf[Path]

    println(s"[$kind.name] $event_path")
  }

  /**
    * Register a particular file or directory to be watched
    */
  def register(dir: Path) {
    val key = dir.register(watchService, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE)

    if (trace) {
      val prev = keys.getOrElse(key, null)
      if (prev == null) {
        println("register: " + dir)
      } else if (!dir.equals(prev)) {
        println("update: " + prev + " -> " + dir)
      }
    }

    keys(key) = dir
  }

  /**
    * Makes it easier to walk a file tree
    */
  implicit def makeDirVisitor(f: (Path) ⇒ Unit) = new SimpleFileVisitor[Path] {
    override def preVisitDirectory(p: Path, attrs: BasicFileAttributes) = {
      f(p)
      FileVisitResult.CONTINUE
    }
  }

  /**
    * Recursively register directories
    */
  def registerAll(start: Path) {
    walkFileTree(start, (f: Path) ⇒ { register(f) })
  }

  /**
    * The main directory watching thread
    */
  override def run() {
    try {
      if (recursive) {
        println("Scanning " + path + "...")
        registerAll(path)
        println("Done.")
      } else
        register(path)

      trace = true

      breakable {
        while (true) {
          val key = watchService.take()
          val dir = keys.getOrElse(key, null)
          if (dir != null) {
            key.pollEvents.asScala.foreach(event ⇒ {
              val kind = event.kind

              if (kind != StandardWatchEventKinds.OVERFLOW) {
                val name = event.context().asInstanceOf[Path]
                var child = dir.resolve(name)

                printEvent(event)

                if (recursive && (kind == StandardWatchEventKinds.ENTRY_CREATE)) {
                  try {
                    if (isDirectory(child, LinkOption.NOFOLLOW_LINKS))
                      registerAll(child);
                  } catch {
                    case ioe: IOException ⇒ println("IOException: " + ioe)
                    case e: Exception ⇒
                      println("Exception: " + e)
                      break
                  }
                }
              }
            })
          } else
            println("WatchKey not recognized!!")

          if (!key.reset()) {
            keys.remove(key);
            if (keys.isEmpty)
              break
          }
        }
      }
    } catch {
      case ie: InterruptedException ⇒ println("InterruptedException: " + ie)
      case ioe: IOException         ⇒ println("IOException: " + ioe)
      case e: Exception             ⇒ println("Exception: " + e)
    }
  }
}

object WatchApp extends App {
  val path = FileSystems.getDefault().getPath("/home/jwissmann/tmp/gitsize1/.git/")
  val dir_watcher = new DirectoryWatcher(path, true)
  val watch_thread = new Thread(dir_watcher)
  watch_thread.start()
}
