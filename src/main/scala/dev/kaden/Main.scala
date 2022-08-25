package dev.kaden

import cats.effect.IOApp
import cats.effect.IO
import dev.kaden.Entities.*

object Main extends IOApp.Simple {

  def run: IO[Unit] = Latex.compile(kadenResume)

  val kadenResume = Resume(
    Header(
      "Kaden Taylor",
      "I like building things, solving problems, and building things that solve problems.",
      ContactInfo(
        PhoneNumber(480, 734, 8791),
        EmailAddress("kadenjtaylor", "gmail.com")
      ),
      Location("Burien", State("Washington", "WA"))
    ),
    Experience(
      Workplace(
        "Axoni",
        "Fin-tech startup specializing in 'Distributed Ledger Technology'",
        Job("Software Engineer", "Member of the Platform Team")
      ),
      Workplace(
        "Academia.edu",
        "Experiment in short-form publishing: Academia Letters",
        Job("Software Engineer", "Did some Ruby on Rails and some JS")
      ),
      Workplace(
        "Google",
        "Member of the Android Messages Team",
        Job("Software Engineer", "Worked on an Android App")
      ),
      Workplace(
        "Usercare Inc",
        "Startup in Tucson, Arizona",
        Job("Agent.AI - Software Engineer", "Chatbot as a Service"),
        Job("Fly.me - Software Engineer", "Online Travel Agency")
      ),
      Workplace(
        "Amadeus Revenue Integrity",
        "Various Flight-Firming Products",
        Job("Software R+D Intern", "Fun research stuff"),
        Job("Software Engineer", "Domin Model Sculpting")
      )
    ),
    Education(
      EducationRecord(
        "University of Arizona",
        2016,
        ProofOfEducation.Degree("Computer Science - B.S.")
      ),
      EducationRecord(
        "Gilbert High School",
        2012,
        ProofOfEducation.Diploma
      )
    ),
    Extras(
      Element("Runner-up on The FOX network gameshow, `Superhuman`")
    )
  )

}
