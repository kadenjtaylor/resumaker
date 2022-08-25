package dev.kaden

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

  case class Job(title: String, description: String, skills: String*)

  case class Workplace(name: String, blurb: String, jobs: Job*)

  type Year = Int

  enum ProofOfEducation:
    case Diploma
    case Degree(areaOfStudy: String)
    case Certification

  case class EducationRecord(instituion: String, awarded: Year, proof: ProofOfEducation)

  case class Element(content: String)

  case class Experience(workplaces: Workplace*)
  case class Education(certifcations: EducationRecord*)
  case class Extras(elements: Element*)
  case class Metadata(attribution: String)

  case class Resume(
      header: Header,
      experience: Experience,
      education: Education,
      extras: Extras,
      metadata: Metadata
  )
}
