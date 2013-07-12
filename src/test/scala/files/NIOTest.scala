package files

import java.nio.file.Path
import java.nio.file.Paths

import mcp.files.TraversePath

object NIOTest extends App {

  implicit def fromNioPath(path: Path) = new TraversePath(path)

  val path1 = Paths.get(sys.props("user.home"), "1/workspace")

//  val path2 = Paths.get(sys.props("user.home"), "workspace2")

  val list1 = path1.foldLeft(Nil: List[Path]) {
    (xs, p) ⇒ xs ::: path1.relativize(p) :: Nil
  }
  
//  list1 foreach println
 
  path1 foreach {(p,attr) => println (p); Unit}
  
//  val list2 = path2.foldLeft(Nil: List[Path]) {
//    (xs, p) ⇒ xs ::: path2.relativize(p) :: Nil
//  }
//  (list1 diff list2) foreach println
}
