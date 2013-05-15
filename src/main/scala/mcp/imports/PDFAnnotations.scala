package mcp.imports

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdfparser.PDFParser
import java.io.FileInputStream
import java.io.File
import scala.collection.JavaConversions._
import java.util.ArrayList
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation
import org.apache.pdfbox.pdmodel.common.COSArrayList
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationText
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationPopup
import mcp.scala.implicits.FileImplicits._
import org.apache.pdfbox.util.PDFTextStripperByArea
import org.apache.pdfbox.pdmodel.common.PDRectangle
import java.awt.geom.Rectangle2D
import java.util.Random

object PDFAnnotations {

  def main(args: Array[String]) {
    val file = """C:\Users\wissmann.FZI\My Dropbox\bib\Goguen 1999 Algebraic Semiotics - annotated.pdf"""
    //val file = """C:\Users\wissmann.FZI\My Dropbox\bib\Mossakowski Kutz 2012 Institutes - annotated.pdf"""

    val document = PDDocument.load(file)
    val pages = document.getDocumentCatalog.getAllPages.asInstanceOf[ArrayList[PDPage]]
    for (page ← pages) {
      val annotations = page.getAnnotations.asInstanceOf[java.util.List[PDAnnotation]]
      for (annotation ← annotations)
        annotation match {
          case a: PDAnnotationTextMarkup ⇒ println(extractText(a.getPage, a.getRectangle))
          //          case a: PDAnnotationTextMarkup ⇒ println(extractText(document, a.getAnnotationName))
          case a: PDAnnotationText       ⇒ println(a.getContents())
          case a: PDAnnotationPopup      ⇒ println(a.getContents())
          case _                         ⇒ println(annotation)
        }
      //          val p = page.asInstanceOf[PDPage]
      //.getAnnotations()
    }

  }

  def extractText(page: PDPage, rect: PDRectangle): String = {
    val stripper = new PDFTextStripperByArea()
    stripper.setSortByPosition(true)

    // convert annotation rectangle to region rectangle
    val x = rect.getLowerLeftX() - 1;
    var y = rect.getUpperRightY() - 1
    if (page.findRotation() == 0)
      y = page.findMediaBox.getHeight() - y
    val width = rect.getWidth() + 2;
    val height = rect.getHeight() + rect.getHeight() / 4;

    val awtRect = new Rectangle2D.Float(x, y, width, height);

    // create a region and extract text from it
    val id = "region_id_" + new Random().nextLong()
    stripper.addRegion(id, awtRect)
    stripper.extractRegions(page)
    stripper.getTextForRegion(id)
  }
}