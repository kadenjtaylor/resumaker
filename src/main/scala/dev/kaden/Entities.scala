package dev.kaden

import java.time.LocalDate

object Entities {

  case class PhoneNumber(areaCode: Int, prefix: Int, suffix: Int) {
    override def toString(): String = s"($areaCode) $prefix-$suffix"
  }

  case class EmailAddress(host: String, domain: String) {
    override def toString(): String = s"$host@$domain"
  }

  case class ContactInfo(phoneNumber: PhoneNumber, email: EmailAddress)

  case class State(name: String, abbrev: String)

  case class Location(city: String, state: State) {
    override def toString(): String = s"$city, ${state.name}"
  }

  case class Header(name: String, tagline: String, contactInfo: ContactInfo, location: Location)

  case class SoftwareJob(
      title: String,
      description: String,
      skillsAndTools: List[String],
      langsAndLibs: Map[String, List[String]]
  )

  enum Tenure {
    case Past(start: LocalDate, end: LocalDate)
    case Present(start: LocalDate)

    private def niceFormat(ld: LocalDate) = {
      s"${ld.getMonth().toString().toLowerCase().capitalize} '${ld.getYear() % 100}"
    }

    override def toString(): String = this match
      case Tenure.Past(start, end) => s"(${niceFormat(start)} - ${niceFormat(end)})"
      case Tenure.Present(start)   => s"(${niceFormat(start)} - Present)"
  }

  case class Workplace(
      name: String,
      link: String,
      blurb: String,
      tenure: Tenure,
      jobs: SoftwareJob*
  )

  type Year = Int

  enum ProofOfEducation:
    case Diploma(areaOfStudy: String)
    case Degree(areaOfStudy: String)
    case Certification(topic: String)

    override def toString(): String = this match
      case Diploma(areaOfStudy) => areaOfStudy
      case Degree(areaOfStudy)  => areaOfStudy
      case Certification(topic) => topic

  case class EducationRecord(
      instituion: String,
      link: String,
      awarded: Year,
      proof: ProofOfEducation
  )

  case class Link(text: String, link: String)
  type ContentChunk = String | Link
  case class Element(contentChunks: ContentChunk*)

  case class Experience(workplaces: Workplace*)
  case class Education(certifcations: EducationRecord*)
  case class Extras(elements: Element*)
  case class Footer(createdByLink: String, creationDate: LocalDate)

  case class Resume(
      header: Header,
      experience: Experience,
      education: Education,
      extras: Extras,
      metadata: Footer
  )
}
