package com.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto implements Comparable<SearchDto>{
	private String keyword;
	private int count;

	@Override
	public int compareTo(SearchDto o) {
		return  - this.count + o.count;
	}

	
}
