package com.zzu.dataobject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

//类目表，对应数据库中的表

/*note:
 * 
 * 1.这里的类名是要求和数据库中一样的，但是这里是驼峰命名，对应到类目是下划线
 *2. 如果想不使用一样的名字，可以使用注解@Table(name="product_cotegory_diy")
   3.如果要把数据库映射成对象，需要加注解 @Entity 注解
*/
@Data
@Entity
//这个注解是动态更新，数据库中更新后时间也更新
@DynamicUpdate
public class ProductCategory {
    //属性和数据库中的属性一一对应，也要把下划线变成驼峰
	
	
	
	
	/**类目id 主键 自增 */
	@Id
	@GeneratedValue
	 private Integer categoryId;
	
	/** 类目名字. */
	 private String categoryName;

	    /** 类目编号. */
	 private Integer categoryType;
	 
	 private Date updateTime;
     private Date createTime;	 
	 public Date getUpdateTime() {
		return updateTime;
	}
    public ProductCategory(){
    	
    }
	public ProductCategory(String categoryName, Integer categoryType) {
		super();
		this.categoryName = categoryName;
		this.categoryType = categoryType;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}

	@Override
	public String toString() {
		return "ProductCategory [categoryId=" + categoryId + ", categoryName=" + categoryName + ", categoryType="
				+ categoryType + "]";
	}
   
	
}
