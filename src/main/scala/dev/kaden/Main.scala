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
        Job(
          "Software Engineer",
          "Member of the Platform Team",
          "Scala (cats/zio)",
          "Kubernetes",
          "Docker"
        )
      ),
      Workplace(
        "Academia.edu",
        "Experiment in short-form academic publishing: `Academia Letters`",
        Job("Software Engineer", "Full-Stack Development", "Ruby on Rails", "Javascript")
      ),
      Workplace(
        "Google",
        "Member of the Android Messages Team",
        Job("Software Engineer", "Client-Side of Android Messages App", "Java", "Blaze")
      ),
      Workplace(
        "Usercare Inc",
        "Startup in Tucson, Arizona",
        Job("Fly.me - Software Engineer", "Online Travel Agency", "Clojure"),
        Job("Agent.AI - Software Engineer", "Chatbot as a Service", "Java", "Neo4j")
      ),
      Workplace(
        "Amadeus Revenue Integrity",
        "Various Flight-Firming Products",
        Job("Software Engineer", "Domin Model Sculpting", "Java", "RabbitMQ", "Dropwizard"),
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
        ProofOfEducation.Diploma
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
