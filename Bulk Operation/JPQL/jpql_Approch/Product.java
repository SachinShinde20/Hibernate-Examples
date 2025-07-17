package jpql_Approch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//Entity Class
@Entity
@Table(name = "Product_2")
@NamedQuery(
 name = "Product.UpdatePriceById",
 query = "UPDATE Product p SET p.price = :price WHERE p.id = :id" // No semicolon (;) in JPQL queries
)
@NamedQuery(
 name = "Product.deleteByName",
 query = "DELETE FROM Product p WHERE p.name = :name" // No semicolon (;) in JPQL queries
)
@NamedQuery(
 name = "Product.countProducts",
 query = "SELECT COUNT(p) FROM Product p" // No semicolon (;) in JPQL queries
)
public class Product {
		
	@Id
	@Column(name="Product_id") //Optional
	private int id;
	@Column(length=25)
	private String name;
	private String manufacturingDate;
	private int price;
		
	public Product() {
		super();
	}

	public Product(int id, String name, String manufacturingDate, int price) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturingDate = manufacturingDate;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(String manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", manufacturingDate=" + manufacturingDate + ", price="
				+ price + "]";
	}

}
