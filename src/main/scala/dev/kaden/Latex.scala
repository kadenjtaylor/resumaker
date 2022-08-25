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
      filename: String = "resume",
      directoryName: String = "latex/"
  ): IO[Unit] = for {
    texFileName <- IO.pure(s"$directoryName$filename.tex")
    _           <- log("Converting to LaTeX...")
    latext      <- convertToLatex(resume)
    _           <- log(s"Ensuring directory $directoryName exists...")
    _           <- createDirectoryIfNeeded(directoryName)
    _           <- log(s"Writing LaTeX content to `$texFileName`...")
    _           <- writeLatexFile(texFileName, latext)
    _           <- log("Invoking LaTeX...")
    response    <- invokeLatexOn(directoryName, texFileName).attempt
    _ <- response match {
      case Left(err) => reportError(directoryName, filename)
      case Right(_)  => reportSuccess(directoryName, filename)
    }
  } yield ()

  private def reportError(directoryName: String, filename: String): IO[Unit] =
    log(s"Encountered error in compiling LaTex - see $directoryName$filename.log for details")

  private def reportSuccess(directoryName: String, filename: String): IO[Unit] =
    log(s"Success! Your PDF should be located at: $directoryName$filename.pdf")

  private def log(s: String): IO[Unit] = IO.println(s)

  private val plainPage     = raw"\pagestyle{empty}"
  private val documentClass = raw"\documentclass{article}"
  private val documentBegin = raw"\begin{document}"
  private val documentEnd   = raw"\end{document}"
  private val horizLine     = raw"\noindent\rule{\linewidth}{1pt}" + "\n"
  private val newline       = raw"\newline"
  private val vPad          = raw"\vspace*{\fill}"
  private val importGeo     = raw"\usepackage{geometry}"
  private val setGeometry = raw"""\geometry{
 |  a4paper,
 |  total={160mm,267mm},
 |  left=25mm,
 |  top=20mm,
 |}""".stripMargin

  private def beginList       = raw"\begin{itemize}"
  private def itemOf[S](s: S) = raw"\item $s"
  private def endList         = raw"\end{itemize}"

  private def centered(text: String) = raw"\centering{$text}"
  private def bold(text: String)     = raw"\textbf{$text}"
  private def italics(text: String)  = raw"\textit{$text}"
  private def section(name: String)  = raw"\section*{$name}"

  private def formatJob(j: Job): String =
    s"${bold(j.title)} - ${j.description}" +
      s"$newline ${italics(j.skills.mkString(", "))}"

  private def formatWorkplace(w: Workplace): String =
    s"""${bold(w.name)} {${w.tenure}}: ${w.blurb} ${format(w.jobs, formatJob)}"""

  private def formatCert(rec: EducationRecord): String =
    s"${bold(rec.instituion)} - ${rec.awarded}: ${rec.proof}"

  private def formatHeader(h: Header) = {
    raw"""${section(h.name)}
        |${h.tagline}
        |$newline
        |
        |${bold("Location:")} ${h.location} /  ${bold("Email:")} ${h.contactInfo.email} / ${bold(
        "Phone:"
      )} ${h.contactInfo.phoneNumber}
      |$newline"""
  }

  private def format[X](xs: Seq[X], formatter: X => String) = {
    "\n" + beginList + "\n" + xs
      .map(formatter)
      .map(itemOf)
      .mkString("\n") + "\n" + endList + "\n"
  }

  private def convertToLatex(resume: Resume): IO[String] = IO {
    s"""|$documentClass
        |$plainPage
        |$importGeo
        |$setGeometry
        |$documentBegin
        |${formatHeader(resume.header)}
        |$horizLine
        |${section("Experience")}
        |${format(resume.experience.workplaces, formatWorkplace)}
        |$horizLine
        |${section("Education")}
        |${format(resume.education.certifcations, formatCert)}
        |$horizLine
        |${section("Extras")}
        |${format(resume.extras.elements, _.content)}
        |$vPad
        |${centered(italics(resume.metadata.attribution))}
        |$documentEnd
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

  private def invokeLatexOn(directory: String, filename: String): IO[String] = IO {
    s"latex -halt-on-error -output-directory=$directory -output-format=pdf $filename".!!
  }
}
