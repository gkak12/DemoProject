package com.demo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema="public", name="demo_table")
@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class DemoRelation {

	@Id
	@Column(name="id", nullable=false)
	private String id;
	
	@Column(name="col1")
	private String col1;
	
	@Column(name="col2")
	private String col2;
	
	@Builder
	public DemoRelation(String id, String col1, String col2) {
		this.id = id;
		this.col1 = col1;
		this.col2 = col2;
	}
}
