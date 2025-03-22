package dev.kaden

import cats.effect.IOApp
import cats.effect.IO
import dev.kaden.Entities.*
import java.time.LocalDate
import dev.kaden.Entities.Tenure.*

object Main extends IOApp.Simple {

  def run: IO[Unit] = Latex.compile(kadenResume, fileName = "kaden_taylor_resume")

  val kadenResume = Resume(
    Header(
      "Kaden Taylor",
      "I like building things, solving problems, and building things that solve problems.",
      ContactInfo(
        PhoneNumber(480, 734, 8791),
        EmailAddress("kadenjtaylor", "gmail.com")
      ),
      Location("Tucson", State("Arizona", "AZ"))
    ),
    Experience(
      Workplace(
        "Crunchbase",
        "https://crunchbase.com",
        "Discovery Team",
        Past(LocalDate.of(2022, 9, 26), LocalDate.of(2025, 2, 24)),
        Job("Platform Engineer", "Backend Functional scala", "Scala", "http4s")
      ),
      Workplace(
        "Axoni",
        "https://axoni.com",
        "Fin-tech startup specializing in 'Distributed Ledger Technology'",
        Past(LocalDate.of(2021, 6, 1), LocalDate.of(2022, 7, 1)),
        Job(
          "Software Engineer",
          "Member of the Platform Team",
          "Scala (Cats-Effect, ZIO, HTTP4s, Doobie)",
          "Kubernetes",
          "Docker"
        )
      ),
      Workplace(
        "Academia.edu",
        "https://academia.edu",
        "Short-form academic publishing: `Academia Letters`",
        Past(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 6, 1)),
        Job("Software Engineer", "Full-Stack Development", "Ruby on Rails", "Javascript")
      ),
      Workplace(
        "Google",
        "https://google.com",
        "Android Messages Team",
        Past(LocalDate.of(2019, 10, 1), LocalDate.of(2020, 11, 1)),
        Job("Software Engineer", "Client-Side - Android Messages App", "Java", "Blaze")
      ),
      Workplace(
        "Usercare Inc",
        "https://www.crunchbase.com/organization/usercare",
        "Startup in Tucson, Arizona",
        Past(LocalDate.of(2017, 8, 1), LocalDate.of(2019, 7, 1)),
        Job("Fly.me - Software Engineer", "Online Travel Agency", "Clojure"),
        Job("Agent.AI - Software Engineer", "Chatbots as a Service", "Java", "Neo4j")
      ),
      Workplace(
        "Amadeus Revenue Integrity",
        "https://amadeus.com/en/portfolio/airlines/revenue-integrity",
        "Various Flight-Firming Products",
        Past(LocalDate.of(2016, 3, 1), LocalDate.of(2017, 8, 1)),
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
        "https://www.cs.arizona.edu/",
        2016,
        ProofOfEducation.Degree("B.S. Computer Science")
      ),
      EducationRecord(
        "Gilbert High School",
        "https://www.gilbertschools.net/gilberthigh",
        2012,
        ProofOfEducation.Diploma("General Studies")
      )
    ),
    Extras(
      Element(
        "Runner-up on The FOX network gameshow,",
        Link("SuperHuman", "https://www.youtube.com/watch?v=t65mzlOCDF8")
      ),
      Element(
        "Received the Startup Tucson Award at UA Hackathon 2015 for designing an app for the Pebble Smartwatch"
      ),
      Element(
        "Got fed up with using Indeed's autoformatted resume so I wrote some Scala code to generate the required LaTeX instead"
      )
    ),
    Footer("https://github.com/kadenjtaylor/resumaker", LocalDate.now())
  )

}
