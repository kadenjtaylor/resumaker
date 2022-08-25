package dev.kaden

import cats.effect.IO
import dev.kaden.Entities.*
import scala.sys.process.*
import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object Latex {

  def compile(resume: Resume, fileName: String = "resume", dirName: String = "latex/"): IO[Unit] =
    for {
      texFileName <- IO.pure(s"$dirName$fileName.tex")
      _           <- log("Converting to LaTeX...")
      latext      <- LatexUtils.convertToLatex(resume)
      _           <- log(s"Ensuring directory $dirName exists...")
      _           <- createDirectoryIfNeeded(dirName)
      _           <- log(s"Writing LaTeX content to `$texFileName`...")
      _           <- writeLatexFile(texFileName, latext)
      _           <- log("Invoking LaTeX...")
      response    <- invokeLatexOn(dirName, texFileName).attempt
      _ <- response match {
        case Left(err) => reportError(dirName, fileName)
        case Right(_)  => reportSuccess(dirName, fileName)
      }
    } yield ()

  private def reportError(directoryName: String, filename: String): IO[Unit] =
    log(s"Encountered error in compiling LaTex - see $directoryName$filename.log for details")

  private def reportSuccess(directoryName: String, filename: String): IO[Unit] =
    log(s"Success! Your PDF should be located at: $directoryName$filename.pdf")

  private def log(s: String): IO[Unit] = IO.println(s)

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

  object LatexUtils {
    def convertToLatex(resume: Resume): IO[String] = IO {
      s"""|$documentClass
        |$plainPage
        |$importGeo
        |$configureGeometry
        |$importHyper
        |$configureHyper
        |$documentBegin
        |${formatHeader(resume.header)}
        |$horizLine
        |${section("Experience")}
        |${listOf(resume.experience.workplaces, formatWorkplace)}
        |$horizLine
        |${section("Education")}
        |${listOf(resume.education.certifcations, formatCert)}
        |$horizLine
        |${section("Extras")}
        |${listOf(resume.extras.elements, formatExtraElement)}
        |$vPad
        |${formatCreationDate(resume.metadata)}
        |
        |${formatCreationLink(resume.metadata)}
        |$documentEnd
    """.stripMargin
    }

    private val plainPage     = raw"\pagestyle{empty}"
    private val setIndent     = raw"\setlength{\parindent}{0pt}"
    private val setParSkip    = raw"\setlength{\parskip}{0pt}"
    private val documentClass = raw"\documentclass{article}"
    private val documentBegin = raw"\begin{document}"
    private val documentEnd   = raw"\end{document}"
    private val horizLine     = raw"\noindent\rule{\linewidth}{1pt}" + "\n"
    private val newline       = raw"\newline"
    private val vPad          = raw"\vspace*{\fill}"
    private val importGeo     = raw"\usepackage{geometry}"
    private val configureGeometry = raw"""\geometry{
      |   a4paper,
      |   total={160mm,267mm},
      |   left=25mm,
      |   top=20mm,
      |}""".stripMargin
    private val importHyper = raw"\usepackage{hyperref}"
    private val configureHyper = raw"""\hypersetup{
      |   colorlinks=true,
      |   linkcolor=blue,   
      |   urlcolor=blue,
      |   pdfpagemode=FullScreen,
      |   pdfauthor = Kaden Taylor
      |}"""

    private def beginList              = raw"\begin{itemize}"
    private def itemOf[S](s: S)        = raw"\item $s"
    private def endList                = raw"\end{itemize}"
    private def centered(text: String) = raw"\centering{$text}"
    private def bold(text: String)     = raw"\textbf{$text}"
    private def italics(text: String)  = raw"\textit{$text}"
    private def section(name: String)  = raw"\raggedright{\section*{$name}}"
    private def link(text: String, url: String) =
      raw"\href{$url}{$text}"
    private def url(url: String) = raw"\url{$url}"

    private def formatJob(j: Job): String =
      s"${bold(j.title)} - ${j.description}" +
        s"$newline ${italics(j.skills.mkString(", "))}"

    private def formatWorkplace(w: Workplace): String =
      s"""${link(bold(w.name), w.link)} {${w.tenure}}: ${w.blurb} ${listOf(w.jobs, formatJob)}"""

    private def formatCert(rec: EducationRecord): String =
      s"${link(bold(rec.instituion), rec.link)} - ${rec.awarded}: ${rec.proof}"

    private def formatExtraElement(element: Element) =
      element.contentChunks
        .map {
          case Link(text, url) => link(text, url)
          case a               => a
        }
        .mkString(" ")

    private def locationContactBanner(h: Header) =
      raw"""${bold("Location:")} ${h.location} /
      |${bold("Email:")} ${h.contactInfo.email} /
      |${bold("Phone:")} ${h.contactInfo.phoneNumber}"""

    private def formatHeader(h: Header) = {
      raw"""${section(h.name)}
        |${italics(h.tagline)}
        |$newline
        |\begin{center}
        |${locationContactBanner(h)}
        |\end{center}"""
    }

    private def formatCreationDate(f: Footer): String = {
      val formattedDate = f.creationDate.format(
        DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
      )
      centered(raw"Generated on ${italics(formattedDate)} via:")
    }

    private def formatCreationLink(f: Footer): String = {
      raw"${centered(url(f.createdByLink))}"
    }

    private def listOf[X](xs: Seq[X], formatter: X => String) = {
      "\n" + beginList + "\n" + xs
        .map(formatter)
        .map(itemOf)
        .mkString("\n") + "\n" + endList
    }
  }
}
