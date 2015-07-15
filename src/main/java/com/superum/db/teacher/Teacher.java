package com.superum.db.teacher;

import static com.superum.db.generated.timestar.Tables.TEACHER;

import java.util.Objects;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.jooq.Record;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.superum.utils.StringUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Teacher {

	// PUBLIC API

	@JsonProperty("id")
	public int getId() {
		return id;
	}
	@JsonIgnore
	public boolean hasId() {
		return id > 0;
	}
	
	@JsonProperty("paymentDay")
	public byte getPaymentDay() {
		return paymentDay;
	}
	
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	
	@JsonProperty("surname")
	public String getSurname() {
		return surname;
	}
	
	@JsonProperty("phone")
	public String getPhone() {
		return phone;
	}
	
	@JsonProperty("city")
	public String getCity() {
		return city;
	}
	
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	
	@JsonProperty("pictureName")
	public String getPictureName() {
		return pictureName;
	}
	
	@JsonProperty("documentName")
	public String getDocumentName() {
		return documentName;
	}
	
	@JsonProperty("comment")
	public String getComment() {
		return comment;
	}
	
	// OBJECT OVERRIDES

	@Override
	public String toString() {
		return StringUtils.toString(
				"Teacher ID: " + id,
				"Payment day: " + paymentDay, 
				"Name: " + name,
				"Surname: " + surname,
				"Phone: " + phone,
				"City: " + city,
				"Email: " + email, 
				"Picture: " + pictureName, 
				"Document: " + documentName, 
				"Comment: " + comment);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Teacher))
			return false;

		Teacher other = (Teacher) o;

		return this.id == other.id
				&& this.paymentDay == other.paymentDay
				&& Objects.equals(this.name, other.name)
				&& Objects.equals(this.surname, other.surname)
				&& Objects.equals(this.phone, other.phone)
				&& Objects.equals(this.city, other.city)
				&& Objects.equals(this.email, other.email)
				&& Objects.equals(this.pictureName, other.pictureName)
				&& Objects.equals(this.documentName, other.documentName)
				&& Objects.equals(this.comment, other.comment);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = (result << 5) - result + id;
		result = (result << 5) - result + paymentDay;
		result = (result << 5) - result + (name == null ? 0 : name.hashCode());
		result = (result << 5) - result + (surname == null ? 0 : surname.hashCode());
		result = (result << 5) - result + (phone == null ? 0 : phone.hashCode());
		result = (result << 5) - result + (city == null ? 0 : city.hashCode());
		result = (result << 5) - result + (email == null ? 0 : email.hashCode());
		result = (result << 5) - result + pictureName.hashCode();
		result = (result << 5) - result + documentName.hashCode();
		result = (result << 5) - result + comment.hashCode();
		return result;
	}

	// CONSTRUCTORS

	@JsonCreator
	public Teacher(@JsonProperty("id") int id, 
					@JsonProperty("paymentDay") byte paymentDay, 
					@JsonProperty("name") String name, 
					@JsonProperty("surname") String surname, 
					@JsonProperty("phone") String phone, 
					@JsonProperty("city") String city,
					@JsonProperty("email") String email,
					@JsonProperty("pictureName") String pictureName,
					@JsonProperty("documentName") String documentName,
					@JsonProperty("comment") String comment) {
		this.id = id;
		this.paymentDay = paymentDay;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.city = city;
		this.email = email;
		this.pictureName = pictureName != null ? pictureName : "";
		this.documentName = documentName != null ? documentName : "";
		this.comment = comment != null ? comment : "";
	}
	
	public static Teacher valueOf(Record teacherRecord) {
		if (teacherRecord == null)
			return null;
		
		int id = teacherRecord.getValue(TEACHER.ID);
		byte paymentDay = teacherRecord.getValue(TEACHER.PAYMENT_DAY);
		String name = teacherRecord.getValue(TEACHER.NAME);
		String surname = teacherRecord.getValue(TEACHER.SURNAME);
		String phone = teacherRecord.getValue(TEACHER.PHONE);
		String city = teacherRecord.getValue(TEACHER.CITY);
		String email = teacherRecord.getValue(TEACHER.EMAIL);
		String pictureName = teacherRecord.getValue(TEACHER.PICTURE_NAME);
		String documentName = teacherRecord.getValue(TEACHER.DOCUMENT_NAME);
		String comment = teacherRecord.getValue(TEACHER.COMMENT_ABOUT);
		return new Teacher(id, paymentDay, name, surname, phone, city, email, pictureName, documentName, comment);
	}

	// PRIVATE
	
	@Min(value = 0, message = "Negative teacher ids not allowed")
	private final int id;
	
	@Min(value = 1, message = "The payment day must be at least the first day of the month")
	@Max(value = 31, message = "The payment day must be at most the last day of the month")
	private final byte paymentDay;
	
	@NotNull(message = "The teacher must have a name")
	@Size(max = 30, message = "Name size must not exceed 30 characters")
	private final String name;
	
	@NotNull(message = "The teacher must have a surname")
	@Size(max = 30, message = "Surname size must not exceed 30 characters")
	private final String surname;
	
	@NotNull(message = "The teacher must have a phone")
	@Size(max = 30, message = "Phone size must not exceed 30 characters")
	private final String phone;
	
	@NotNull(message = "The teacher must have a city")
	@Size(max = 30, message = "City size must not exceed 30 characters")
	private final String city;
	
	@NotNull(message = "The teacher must have an email")
	@Size(max = 60, message = "Email size must not exceed 60 characters")
	@Email
	private final String email;
	
	@NotNull(message = "The teacher must have a picture name")
	@Size(max = 100, message = "Picture name size must not exceed 100 characters")
	private final String pictureName;
	
	@NotNull(message = "The teacher must have a document name")
	@Size(max = 100, message = "Document name size must not exceed 100 characters")
	private final String documentName;
	
	@NotNull(message = "The teacher must have a comment, even if empty")
	@Size(max = 500, message = "The comment must not exceed 500 characters")
	private final String comment;

}
