package dev.kaden

import cats.effect.IOApp
import cats.effect.IO
import dev.kaden.Entities.*
import java.time.LocalDate
import dev.kaden.Entities.Tenure.*

object Main extends IOApp.Simple {

  def run: IO[Unit] = Latex.compile(resume, fileName = "kaden_taylor_resume")

  val crunchbase = Workplace(
    "Crunchbase",
    "https://crunchbase.com",
    "Data and Predictions on Private Companies",
    Past(LocalDate.of(2022, 9, 26), LocalDate.of(2025, 2, 24)),
    SoftwareJob(
      "Platform Engineer",
      "Discovery Team",
      List("Kafka", "Docker", "Kubernetes", "Debezium"),
      Map("Scala" -> List("Cats-Effect", "Http4s"))
    )
  )

  val axoni = Workplace(
    "Axoni",
    "https://axoni.com",
    "Fin-tech startup specializing in 'Distributed Ledger Technology'",
    Past(LocalDate.of(2021, 6, 1), LocalDate.of(2022, 7, 1)),
    SoftwareJob(
      "Software Engineer",
      "Platform Team",
      List("Kubernetes", "Docker", "Debezium"),
      Map("Scala" -> List("Cats-Effect", "ZIO", "Http4s", "Doobie"))
    )
  )

  val academia = Workplace(
    "Academia.edu",
    "https://academia.edu",
    "Making research available",
    Past(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 6, 1)),
    SoftwareJob(
      "Full-Stack Software Engineer",
      "Short-form academic publishing: `Academia Letters`",
      List(),
      Map("Ruby" -> List("Rails"), "Javascript" -> List("React"))
    )
  )

  val google = Workplace(
    "Google",
    "https://google.com",
    "Android Messages Team",
    Past(LocalDate.of(2019, 10, 1), LocalDate.of(2020, 11, 1)),
    SoftwareJob(
      "Software Engineer",
      "Android Messages App",
      List("Blaze", "Piper"),
      Map("Java" -> List("Dagger"))
    )
  )

  val usercare = Workplace(
    "Usercare Inc",
    "https://www.crunchbase.com/organization/usercare",
    "Startup in Tucson, Arizona",
    Past(LocalDate.of(2017, 8, 1), LocalDate.of(2019, 7, 1)),
    SoftwareJob(
      "Fly.me - Software Engineer",
      "Online Travel Agency",
      List(),
      Map("Clojure" -> List())
    ),
    SoftwareJob(
      "Agent.AI - Software Engineer",
      "Chatbots as a Service",
      List("Neo4j"),
      Map("Java" -> List("Dropwizard", "Guice"))
    )
  )

  val amadeus = Workplace(
    "Amadeus Revenue Integrity",
    "https://amadeus.com/en/portfolio/airlines/revenue-integrity",
    "Various Flight-Firming Products",
    Past(LocalDate.of(2016, 3, 1), LocalDate.of(2017, 8, 1)),
    SoftwareJob(
      "Software Engineer",
      "Domain Modeling, Optimization",
      List("RabbitMQ"),
      Map("Java" -> List("Dropwizard", "Guice"))
    ),
    SoftwareJob(
      "Software R+D Intern",
      "Fun research stuff",
      List("Ansible", "Consul", "Fabio"),
      Map("APL" -> List(), "Java" -> List())
    )
  )

  val avirtek = Workplace(
    "Avirtek",
    "https://avirtek.com/",
    "Autonomic Cyber-Security",
    Past(LocalDate.of(2015, 10, 1), LocalDate.of(2016, 3, 1)),
    SoftwareJob(
      "Software Engineering Intern",
      "Information Theory, Statistics, Machine Learning",
      List("Hadoop"),
      Map("Python" -> List("pandas", "numpy", "matplotlib"))
    )
  )

  val uOfA = EducationRecord(
    "University of Arizona",
    "https://www.cs.arizona.edu/",
    2016,
    ProofOfEducation.Degree("B.S. Computer Science")
  )

  val gilbertHigh = EducationRecord(
    "Gilbert High School",
    "https://www.gilbertschools.net/gilberthigh",
    2012,
    ProofOfEducation.Diploma("General Studies")
  )

  val superhuman = Element(
    "Designed a custom mnemonic system to compete on,",
    Link("SuperHuman", "https://www.youtube.com/watch?v=t65mzlOCDF8")
  )

  val startupTucson = Element(
    "Received 'Startup Tucson Award' at UA Hackathon '15 for Pebble Smartwatch app design"
  )

  val programUnderstandability = Element(
    "Passionate about domain modeling and",
    Link(
      "improving program understandability",
      "https://www.kaden.dev/pages/musings/software_doesnt_have_clay.html"
    )
  )

  val resume = Resume(
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
      crunchbase,
      axoni,
      google,
      usercare,
      amadeus,
      avirtek
    ),
    Education(
      uOfA,
      gilbertHigh
    ),
    Extras(
      superhuman,
      startupTucson,
      programUnderstandability
    ),
    Footer("https://github.com/kadenjtaylor/resumaker", LocalDate.now())
  )

}
