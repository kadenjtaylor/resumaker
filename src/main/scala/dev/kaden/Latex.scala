package dev.kaden

import cats.effect.IO
import dev.kaden.Entities.*
import scala.sys.process.*
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter

object Latex {

  def compile(
      resume: Resume,
      directoryName: String = "latex/",
      filename: String = "resume"
  ): IO[Unit] = for {
    texFileName <- IO.pure(s"$directoryName$filename.tex")
    _ <- IO.println("Converting to LaTeX...")
    latext <- convertToLatex(resume)
    _ <- IO.println(s"Ensuring directory $directoryName exists...")
    _ <- createDirectoryIfNeeded(directoryName)
    _ <- IO.println(s"Writing LaTeX content to `$texFileName`...")
    _ <- writeLatexFile(texFileName, latext)
    _ <- IO.println("Invoking LaTeX...")
    response <- invokeLatexOn(directoryName, texFileName).attempt
    _ <- response match {
      case Left(err) =>
        IO.println(
          s"Encountered error in compiling LaTex - see $directoryName$filename.log for details"
        )
      case Right(_) =>
        IO.println(s"Success! Your PDF should be located at: $directoryName$filename.pdf")
    }
  } yield ()

  def convertToLatex(resume: Resume): IO[String] = IO {
    // TODO: Implement actual conversion logic
    """\documentclass{article}
    |\begin{document}
    |First document. This is a simple example, with no 
    |extra parameters or packages included.
    |\end{document}
    """.stripMargin
  }

  private def createDirectoryIfNeeded(directoryName: String): IO[Unit] = IO {
    val directory = new File(directoryName);
    if (!directory.exists()) {
      println("Directory does not exist. Creating it now...")
      directory.mkdirs();
    } else {
      println("Directory already exists")
    }
  }

  private def writeLatexFile(filename: String, content: String) = IO {
    val bw = new BufferedWriter(new FileWriter(new File(filename)))
    bw.write(content)
    bw.close()
  }

  private def invokeLatexOn(directory: String, filename: String): IO[Unit] = IO {
    s"latex -halt-on-error -output-directory=$directory -output-format=pdf $filename".!!
  }
}
