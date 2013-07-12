package mcp.vc.git

import java.io.File
import scala.collection.JavaConversions.asScalaSet
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.internal.storage.file.FileRepository
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.SimpleFileVisitor
import java.nio.file.Path

object GitStatus extends App {

  var baseDir = "~/1/workspace/termianation-clearance/"

  var dirs = (new File(baseDir)).listFiles.filter(_.isDirectory)

  val repo = new FileRepository(dirs.head)
  val git = new Git(repo);
  val status = git.status().call();
  for (modified ← status.getModified())
    System.out.println("Modified file: " + modified);
  repo.close
}