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

  private val plainPage = raw"\pagestyle{empty}"
  private val documentClass = raw"\documentclass{article}"
  private val documentBegin = raw"\begin{document}"
  private val documentEnd = raw"\end{document}"
  private val horizLine = raw"\noindent\rule{\linewidth}{1pt}"
  private val newline = raw"\newline"
  private val importGeo = raw"\usepackage{geometry}"
  private val setGeometry = raw"""\geometry{
 |  a4paper,
 |  total={160mm,267mm},
 |  left=25mm,
 |  top=20mm,
 |}""".stripMargin
  private val vPad = raw"\vspace*{\fill}"

  private def itemOf[S](s: S) = raw"\item $s"
  private def beginList = raw"\begin{itemize}"
  private def endList = raw"\end{itemize}"

  private def centered(text: String) = raw"\centering{$text}"
  private def bold(text: String) = raw"\textbf{$text}"
  private def italics(text: String) = raw"\textit{$text}"
  private def section(name: String) = raw"""\section*{$name}"""

  private def formatJob(j: Job): String =
    s"${bold(j.title)} - ${j.description}" +
      s"$newline ${italics(j.skills.mkString(", "))}"

  private def formatJobs(jobs: Seq[Job]): String =
    beginList + jobs
      .map(formatJob)
      .map(itemOf)
      .mkString("\n") + endList

  private def formatWorkplace(w: Workplace): String =
    s"""${bold(w.name)}: ${w.blurb} ${formatJobs(w.jobs)}"""

  private def formatExperience(workplaces: Seq[Workplace]): String =
    beginList +
      workplaces
        .map(formatWorkplace)
        .map(itemOf)
        .mkString("\n") + endList

  private def formatCert(rec: EducationRecord): String =
    s"${bold(rec.instituion)} - ${rec.awarded}: ${rec.proof}"

  private def formatEducation(certifcations: Seq[EducationRecord]): String =
    beginList + certifcations
      .map(formatCert)
      .map(itemOf)
      .mkString("\n") + endList

  private def formatExtras(extras: Seq[Element]): String =
    beginList + extras
      .map(_.content)
      .map(itemOf)
      .mkString("\n") + endList

  def convertToLatex(resume: Resume): IO[String] = IO {
    s"""|$documentClass
        |$plainPage
        |$importGeo
        |$setGeometry
        |$documentBegin
        |${section(resume.header.name)}
        |${resume.header.tagline}
        |$newline
        |
        |${bold("Location:")} ${resume.header.location} /  ${bold(
         "Email:"
       )} ${resume.header.contactInfo.email} / ${bold(
         "Phone:"
       )} ${resume.header.contactInfo.phoneNumber}
        |
        |$horizLine
        |
        |${section("Experience")}
        |
        |${formatExperience(resume.experience.workplaces)}
        |
        |$horizLine
        |
        |${section("Education")}
        |
        |${formatEducation(resume.education.certifcations)}
        |
        |$horizLine
        |
        |${section("Extras")}
        |
        |${formatExtras(resume.extras.elements)}
        |
        |$vPad
        |
        |${centered(italics(resume.metadata.attribution))}
        |
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

  private def invokeLatexOn(directory: String, filename: String): IO[Unit] = IO {
    s"latex -halt-on-error -output-directory=$directory -output-format=pdf $filename".!!
  }
}
