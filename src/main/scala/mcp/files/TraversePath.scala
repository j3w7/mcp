package mcp.files

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor

class TraversePath(path: Path) {
    def foreach(f: (Path, BasicFileAttributes) => Unit) {
        Files.walkFileTree(path, new SimpleFileVisitor[Path] {
            override def visitFile(file: Path, attrs: BasicFileAttributes) = {
                f(file, attrs)
                FileVisitResult.CONTINUE
            }
        })
    }

    /**
     * foreach that takes FileVisitResult instead of Unit
     */
    def foreach2(f: (Path, BasicFileAttributes) => FileVisitResult) {
        Files.walkFileTree(path, new SimpleFileVisitor[Path] {
            override def visitFile(file: Path, attrs: BasicFileAttributes) = f(file, attrs)
        })
    }

    def foldLeft[T](t: T)(f: (T, Path) => T) = {
        var current = t
        foreach((p, _) => current = f(current, p))
        current
    }

    def forall(f: Path => Boolean) = {
        var ret = true
        foreach2((p, _) =>
            if ( !f(path) ) {
                ret = false
                FileVisitResult.TERMINATE
            }
            else
                FileVisitResult.CONTINUE
        )
        ret
    }

    def exists(f: Path => Boolean) = {
        var ret = false
        foreach2((p, _) =>
            if ( f(path) ) {
                ret = true
                FileVisitResult.TERMINATE
            }
            else
                FileVisitResult.CONTINUE
        )
    }

    /**
     * Directly modifies the underlying path.
     */
    def mapReal(f: Path => Path) = foreach((p, _) => Files.move(p, f(p)))

    /**
     * @param f map function
     * @return a left-folded list with the map function applied to each element
     */
    def map(f: Path => Path) = foldLeft(Nil: List[Path]) {
        case (xs, p) => xs ::: f(p) :: Nil
    }

    def find(f: Path => Boolean) = {
        var k = None: Option[Path]
        foreach2((p, _) =>
            if ( f(p) ) {
                k = Some(p)
                FileVisitResult.TERMINATE
            } else FileVisitResult.CONTINUE
        )
        k
    }
}

