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
        "Amadeus Revenue Integrity",
        "Flight firming stuff",
        Job("Software R+D Intern", "Fun research stuff"),
        Job("Software Engineer", "Domin Model Sculpting")
      ),
      Workplace(
        "Usercare Inc.",
        "Startup in Tucson, Arizona",
        Job("Agent.AI - Software Engineer", "Chatbot as a Service"),
        Job("Fly.me - Software Engineer", "Online Travel Agency")
      ),
      Workplace(
        "Google",
        "Member of the Android Messages Team",
        Job("Software Engineer", "Worked on an Android App")
      ),
      Workplace(
        "Academia.edu",
        "Experiment in short-form publishing: Academia Letters",
        Job("Software Engineer", "Did some Ruby on Rails and some JS")
      ),
      Workplace(
        "Axoni",
        "Fin-tech startup specializing in 'Distributed Ledger Technology'",
        Job("Software Engineer", "Member of the Platform Team")
      )
    ),
    Education(
      EducationRecord("University of Arizona", 2016, ProofOfEducation.Degree),
      EducationRecord("Gilbert High School", 2012, ProofOfEducation.Diploma)
    )
  )

}
