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

  case class Header(name: String, contactInfo: ContactInfo, location: Location)

  enum Skill:
    case Hard(name: String)
    case Soft(name: String)

  case class Job(title: String, description: String, skills: Skill*)

  case class Workplace(name: String, blurb: String, jobs: Job*)

  type Year = Int

  enum ProofOfEducation:
    case Diploma
    case Degree
    case Certification

  case class EducationRecord(instituion: String, awarded: Year, proof: ProofOfEducation)

  case class Element(content: String)

  enum Section:
    case Experience(workplaces: Workplace*)
    case Education(educations: EducationRecord*)
    case Misc(title: String, elements: Element)

  case class Resume(header: Header, sections: Section*)
}
