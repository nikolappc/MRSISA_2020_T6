package isamrs.registracija;

import isamrs.domain.Pacijent;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;

@Entity
@Table(name = "verification_token")
public class VerificationToken {
	
	private static final int EXPIRATION = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="token")
	private String token;
	
	@OneToOne(targetEntity = Pacijent.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private Pacijent user;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="expiry_date")
	private Date expiryDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Pacijent getUser() {
		return user;
	}

	public void setUser(Pacijent user) {
		this.user = user;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

	public VerificationToken(int id, String token, Pacijent user, Date createdDate, Date expiryDate) {
		super();
		this.id = id;
		this.token = token;
		this.user = user;
		this.createdDate = createdDate;
		this.expiryDate = expiryDate;
	}
	public VerificationToken(String token, Pacijent user) {
		super();
		this.token = token;
		this.user = user;
		this.createdDate = new Date();
		this.expiryDate = this.calculateExpiryDate(EXPIRATION);
	}
	
	public VerificationToken() {}
	
	
	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(calendar.getTime().getTime());
	}
	
}