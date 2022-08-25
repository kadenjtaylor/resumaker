package dev.kaden

import cats.effect.IOApp
import cats.effect.IO
import dev.kaden.Entities.*
import java.time.LocalDate

object Main extends IOApp.Simple {

  def run: IO[Unit] = Latex.compile(kadenResume, filename = "kaden_taylor_resume")

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
        Tenure(LocalDate.of(2021, 6, 1), LocalDate.of(2022, 7, 1)),
        Job(
          "Software Engineer",
          "Member of the Platform Team",
          "Scala (Cats-Effect, ZIO, HTTP4s, doobie)",
          "Kubernetes",
          "Docker"
        )
      ),
      Workplace(
        "Academia.edu",
        "Short-form academic publishing: `Academia Letters`",
        Tenure(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 6, 1)),
        Job("Software Engineer", "Full-Stack Development", "Ruby on Rails", "Javascript")
      ),
      Workplace(
        "Google",
        "Android Messages Team",
        Tenure(LocalDate.of(2019, 10, 1), LocalDate.of(2020, 11, 1)),
        Job("Software Engineer", "Client-Side - Android Messages App", "Java", "Blaze")
      ),
      Workplace(
        "Usercare Inc",
        "Startup in Tucson, Arizona",
        Tenure(LocalDate.of(2017, 8, 1), LocalDate.of(2019, 7, 1)),
        Job("Fly.me - Software Engineer", "Online Travel Agency", "Clojure"),
        Job("Agent.AI - Software Engineer", "Chatbots as a Service", "Java", "Neo4j")
      ),
      Workplace(
        "Amadeus Revenue Integrity",
        "Various Flight-Firming Products",
        Tenure(LocalDate.of(2016, 3, 1), LocalDate.of(2017, 8, 1)),
        Job(
          "Software Engineer",
          "Domin Model Sculpting, Various Product Work",
          "Java",
          "RabbitMQ",
          "Dropwizard"
        ),
        Job("Software R+D Intern", "Fun research stuff", "APL", "Ansible", "Consul", "Fabio")
      )
    ),
    Education(
      EducationRecord(
        "University of Arizona",
        2016,
        ProofOfEducation.Degree("B.S. Computer Science")
      ),
      EducationRecord(
        "Gilbert High School",
        2012,
        ProofOfEducation.Diploma("General Studies")
      )
    ),
    Extras(
      Element("Runner-up on The FOX network gameshow, `Superhuman`"),
      Element(
        "Received the Startup Tucson Award at UA Hackathon 2015 for designing an app for the Pebble Smartwatch"
      ),
      Element(
        "Got fed up with using Indeed's autoformatted resume so I wrote some Scala code to generate the required LaTeX instead"
      )
    ),
    Metadata("Created via: https://github.com/kadenjtaylor/resumaker")
  )

}
